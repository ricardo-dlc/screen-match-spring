package com.ricardodev.screenmatch.dto;

import com.ricardodev.screenmatch.model.Genre;

public record SeriesDTO(
                Long id,
                String title,
                Integer totalSeasons,
                Double rating,
                Genre genre,
                String actors,
                String plot,
                String poster) {

}
