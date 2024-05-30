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
import com.ricardodev.screenmatch.model.Genre;
import com.ricardodev.screenmatch.model.SeasonData;
import com.ricardodev.screenmatch.model.Series;
import com.ricardodev.screenmatch.model.SeriesData;
import com.ricardodev.screenmatch.repository.SeriesRepository;
import com.ricardodev.screenmatch.service.ApiConsuming;
import com.ricardodev.screenmatch.service.DataConverter;
import com.ricardodev.screenmatch.service.SecretsService;

public class Main {
    private final SecretsService secretsService;
    private final String BASE_URL;
    private Scanner scanner = new Scanner(System.in);
    private ApiConsuming apiConsuming = new ApiConsuming();
    private DataConverter converter = new DataConverter();
    private List<Series> seriesList;
    private SeriesRepository seriesRepository;

    public Main(SeriesRepository seriesRepository, SecretsService secretsService) {
        this.seriesRepository = seriesRepository;
        this.secretsService = secretsService;
        this.BASE_URL = "http://www.omdbapi.com/?apikey=" + this.secretsService.getOmdbApiKey();
    }

    public void showMenu() throws UnsupportedEncodingException {
        int option = -1;
        while (option != 0) {
            String menu = """
                    1. Search series
                    2. Search episodes
                    3. Show search history
                    4. Find series by title
                    5. Display top 5 series
                    6. Find series by genre
                    7. Filter series by total seasons and rating

                    0. Exit
                    """;
            System.out.println(menu);
            try {
                System.out.print("Your option: ");
                option = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid option!");
                continue;
            } finally {
                scanner.nextLine();
            }

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
                case 4:
                    findSeriesByTitle();
                    break;
                case 5:
                    findTop5Series();
                    break;
                case 6:
                    findSeriesByGenre();
                    break;
                case 7:
                    findByTotalSeasonsAndRating();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Unknown option.");
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

    private void findSeriesByTitle() {
        System.out.print("Type the name of the series which you want to search: ");
        String seriesName = scanner.nextLine();

        Optional<Series> result = seriesRepository.findByTitleContainsIgnoreCase(seriesName);

        if (result.isPresent()) {
            System.out.println("The series is: " + result.get());
        } else {
            System.out.println("Series not found.");
        }
    }

    private void findTop5Series() {
        List<Series> topSeries = seriesRepository.findTop5ByOrderByRatingDesc();
        System.out.println("Top 5 series are:");
        topSeries.forEach(s -> System.out.printf("%-20s %.1f%n", s.getTitle(), s.getRating()));
    }

    private void findSeriesByGenre() {
        System.out.print("Type the genre/category name which you want to search: ");
        String genre = scanner.nextLine();

        try {
            List<Series> seriesByGenre = seriesRepository.findByGenre(Genre.fromString(genre));
            if (seriesByGenre.size() > 0) {
                System.out.println("Series of genere " + genre + " are:");
                seriesByGenre.forEach(s -> System.out.printf("%-20s %.1f%n", s.getTitle(), s.getRating()));
            } else {
                System.out.println("No series found.");
            }

        } catch (Exception e) {
            System.out.println("An error ocurred. Please try again.");
        }
    }

    private void findByTotalSeasonsAndRating() {
        System.out.println("Provide the following data (both inclusive)");
        System.out.print("Enter the max seasons: ");
        Integer totalSeasons = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the minimum rating: ");
        Double rating = scanner.nextDouble();
        scanner.nextLine();

        List<Series> seriesByGenre = seriesRepository
                .findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(totalSeasons, rating);
        if (seriesByGenre.size() > 0) {
            System.out.printf("Series with max %d seasons and a min rating of %.2f are:%n", totalSeasons, rating);
            seriesByGenre.forEach(
                    s -> System.out.printf("%-20s %-5d %.1f%n", s.getTitle(), s.getTotalSeasons(), s.getRating()));
        } else {
            System.out.println("No series found.");
        }
    }
}
