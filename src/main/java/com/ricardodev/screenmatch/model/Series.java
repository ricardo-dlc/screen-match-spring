package com.ricardodev.screenmatch.model;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

public class Series {
    private String title;
    private int totalSeasons;
    private Double rating;
    // private List<Genre> genres;
    private Genre genre;
    private String actors;
    private String plot;
    private String poster;

    public Series(SeriesData seriesData) {
        this.title = seriesData.title();
        this.totalSeasons = seriesData.totalSeasons();
        this.rating = OptionalDouble.of(Double.valueOf(seriesData.rating())).orElse(0);
        // this.genres = Arrays.stream(seriesData.genres().split(",")).map(g ->
        // Genre.fromString(g.trim())).toList();
        this.genre = Genre.fromString(seriesData.genres().split(",")[0].trim());
        this.actors = seriesData.actors();
        this.plot = seriesData.plot();
        this.poster = seriesData.poster();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(int totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genres) {
        this.genre = genres;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "genre=" + genre + ", title=" + title + ", totalSeasons=" + totalSeasons + ", rating=" + rating
                + ", actors=" + actors + ", plot=" + plot + ", poster=" + poster;
    }
}
