package com.ricardodev.screenmatch.main;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.ricardodev.screenmatch.model.Episode;
import com.ricardodev.screenmatch.model.SeasonData;
import com.ricardodev.screenmatch.model.Series;
import com.ricardodev.screenmatch.model.SeriesData;
import com.ricardodev.screenmatch.repository.SeriesRepository;
import com.ricardodev.screenmatch.service.ApiConsuming;
import com.ricardodev.screenmatch.service.DataConverter;

import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private ApiConsuming apiConsuming = new ApiConsuming();
    private final Dotenv env = Dotenv.load();
    private final String API_KEY = env.get("OMDB_API_KEY");
    private final String BASE_URL = "http://www.omdbapi.com/?apikey=%s".formatted(this.API_KEY);
    private DataConverter converter = new DataConverter();
    private List<Series> seriesList;
    private SeriesRepository seriesRepository;

    public Main(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public void showMenu() throws UnsupportedEncodingException {
        int option = -1;
        while (option != 0) {
            String menu = """
                    1. Search series
                    2. Search episodes
                    3. Show search history

                    0. Exit
                    """;
            System.out.println(menu);
            System.out.print("Your option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    searchSeriesWeb();
                    break;
                case 2:
                    searchEpisodeInSeries();
                    break;
                case 3:
                    showSearchHistory();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private SeriesData getSeriesData() throws UnsupportedEncodingException {
        System.out.print("Enter the series name you want to seach: ");
        String seriesName = scanner.nextLine();
        String apiEndpoint = BASE_URL + "&t=" + URLEncoder.encode(seriesName, "UTF-8");
        String json = apiConsuming.getData(apiEndpoint);
        return converter.getData(json, SeriesData.class);
    }

    private void searchEpisodeInSeries() throws UnsupportedEncodingException {
        showSearchHistory();
        System.out.println("Type the name of the series for which you want to search for episodes: ");
        String seriesName = scanner.nextLine();

        Optional<Series> series = seriesList.stream()
                .filter(s -> s.getTitle().toLowerCase().contains(seriesName.toLowerCase()))
                .findFirst();

        if (series.isPresent()) {
            Series resultSeries = series.get();

            List<SeasonData> seasons = new ArrayList<>();
            String apiEndpoint = BASE_URL + "&t=" + URLEncoder.encode(resultSeries.getTitle(), "UTF-8");
            for (int i = 1; i <= resultSeries.getTotalSeasons(); i++) {
                String json = apiConsuming.getData(apiEndpoint + "&season=" + i);
                SeasonData seasonData = converter.getData(json, SeasonData.class);
                seasons.add(seasonData);
            }
            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(s -> s.episodes().stream()
                            .map(e -> new Episode(s.seasonNumber(), e)))
                    .collect(Collectors.toList());

            resultSeries.setEpisodes(episodes);
            seriesRepository.save(resultSeries);
        }

    }

    private void searchSeriesWeb() throws UnsupportedEncodingException {
        SeriesData data = getSeriesData();
        Series series = new Series(data);
        seriesRepository.save(series);
        // seriesHistory.add(data);
        System.out.println(data);
    }

    private void showSearchHistory() {
        seriesList = seriesRepository.findAll();

        seriesList.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
        ;
    }
}
