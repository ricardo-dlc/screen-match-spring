package com.ricardodev.screenmatch.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(
        @JsonAlias("Season") int seasonNumber,
        @JsonAlias("Episodes") List<EpisodeData> episodes) {
}
