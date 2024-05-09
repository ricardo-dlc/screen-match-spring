package com.ricardodev.screenmatch.main;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.ricardodev.screenmatch.model.Episode;
import com.ricardodev.screenmatch.model.EpisodeData;
import com.ricardodev.screenmatch.model.SeasonData;
import com.ricardodev.screenmatch.model.SeriesData;
import com.ricardodev.screenmatch.service.ApiConsuming;
import com.ricardodev.screenmatch.service.DataConverter;

import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private ApiConsuming apiConsuming = new ApiConsuming();
    private final Dotenv env = Dotenv.load();
    private final String API_KEY = env.get("API_KEY");
    private final String BASE_URL = "http://www.omdbapi.com/?apikey=%s".formatted(this.API_KEY);
    private DataConverter converter = new DataConverter();

    public void showMenu() {
        try {
            System.out.print("Enter the series name you want to seach: ");
            String input = scanner.nextLine();
            String seriesName = URLEncoder.encode(input, "UTF-8");
            String apiEndpoint = BASE_URL + "&t=%s".formatted(seriesName);

            String json;
            json = apiConsuming.getData(apiEndpoint);
            SeriesData seriesData = converter.getData(json, SeriesData.class);
            System.out.println(seriesData);

            List<SeasonData> seasons = new ArrayList<>();
            for (int i = 1; i <= seriesData.totalSeasons(); i++) {
                json = this.apiConsuming.getData(apiEndpoint + "&season=" + i);
                SeasonData seasonData = this.converter.getData(json, SeasonData.class);
                seasons.add(seasonData);
            }

            // seasons.forEach(System.out::println);

            // seasons.forEach(seasonData -> {
            // seasonData.episodes().forEach(episode ->
            // System.out.println(episode.title()));
            // });

            List<EpisodeData> episodesData = seasons.stream()
                    .flatMap(t -> t.episodes().stream())
                    .collect(Collectors.toList());

            // Filter top 5 rated episodes
            System.out.println("Top 5 episodes");
            episodesData.stream()
                    .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                    .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                    .limit(5)
                    .forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(s -> s.episodes().stream()
                            .map(e -> new Episode(s.seasonNumber(), e)))
                    .collect(Collectors.toList());

            episodes.forEach(System.out::println);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
