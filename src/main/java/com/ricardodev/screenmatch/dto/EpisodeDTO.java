package com.ricardodev.screenmatch.dto;

public record EpisodeDTO(
        Long id,
        Integer seasonNumber,
        String title,
        Integer episodeNumber) {

}
