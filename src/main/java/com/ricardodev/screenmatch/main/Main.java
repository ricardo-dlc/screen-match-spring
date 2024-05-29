package com.ricardodev.screenmatch.main;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.ricardodev.screenmatch.model.SeasonData;
import com.ricardodev.screenmatch.model.Series;
import com.ricardodev.screenmatch.model.SeriesData;
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
    private List<SeriesData> seriesHistory = new ArrayList<>();

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
        SeriesData seriesData = getSeriesData();
        List<SeasonData> seasons = new ArrayList<>();
        for (int i = 1; i <= seriesData.totalSeasons(); i++) {
            String apiEndpoint = BASE_URL + "&t=" + URLEncoder.encode(seriesData.title(), "UTF-8");
            String json = apiConsuming.getData(apiEndpoint + "&season=" + i);
            SeasonData seasonData = converter.getData(json, SeasonData.class);
            seasons.add(seasonData);
        }
        seasons.forEach(System.out::println);
    }

    private void searchSeriesWeb() throws UnsupportedEncodingException {
        SeriesData data = getSeriesData();
        seriesHistory.add(data);
        System.out.println(data);
    }

    private void showSearchHistory() {
        List<Series> seriesList = new ArrayList<>();
        seriesList = seriesHistory.stream()
                .map(s -> new Series(s))
                .collect(Collectors.toList());

        seriesList.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
        ;
    }
}
