package com.ricardodev.screenmatch.dto;

import com.ricardodev.screenmatch.model.Genre;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SeriesDTO(
        String title,
        Integer totalSeasons,
        Double rating,
        Genre genre,
        String actors,
        String plot,
        String poster) {

}
