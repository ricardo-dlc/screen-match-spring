package com.ricardodev.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private int seasonNumber;
    private String title;
    private int episodeNumber;
    private Double rating;
    private LocalDate releaseDate;

    public Episode(int seasonNumber, EpisodeData e) {
        this.seasonNumber = seasonNumber;
        this.title = e.title();
        this.episodeNumber = e.episodeNumber();
        try {
            this.rating = Double.valueOf(e.rating());
        } catch (NumberFormatException ex) {
            this.rating = 0.0;
        }

        try {
            this.releaseDate = LocalDate.parse(e.releaseDate());
        } catch (DateTimeParseException ex) {
            this.releaseDate = null;
        }
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int season) {
        this.seasonNumber = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "seasonNumber=" + seasonNumber + ", title=" + title + ", episodeNumber=" + episodeNumber
                + ", rating=" + rating + ", releaseDate=" + releaseDate;
    }

}
