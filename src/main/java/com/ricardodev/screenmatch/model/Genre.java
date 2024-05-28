package com.ricardodev.screenmatch.model;

public enum Genre {
    ACTION("Action"),
    COMEDY("Comedy"),
    ANIMATION("Animation"),
    DRAMA("Drama"),
    CRIME("Crime");

    private String genre;

    Genre(String genre) {
        this.genre = genre;
    }

    public static Genre fromString(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.genre.equalsIgnoreCase(text)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("No genre was found: " + text);
    }

}
