package com.ricardodev.screenmatch.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardodev.screenmatch.dto.EpisodeDTO;
import com.ricardodev.screenmatch.dto.SeriesDTO;
import com.ricardodev.screenmatch.model.Episode;
import com.ricardodev.screenmatch.model.Genre;
import com.ricardodev.screenmatch.model.Series;
import com.ricardodev.screenmatch.repository.SeriesRepository;

@Service
public class SeriesService {
    @Autowired
    private SeriesRepository repository;

    private SeriesDTO toSeriesDto(Series series) {
        return new SeriesDTO(
                series.getId(),
                series.getTitle(),
                series.getTotalSeasons(),
                series.getRating(),
                series.getGenre(),
                series.getActors(),
                series.getPlot(),
                series.getPoster());
    }

    private List<SeriesDTO> toSeriesDtoList(List<Series> series) {
        return series.stream()
                .map(s -> toSeriesDto(s))
                .collect(Collectors.toList());
    }

    private List<EpisodeDTO> toEpisodeDtoList(List<Episode> episodes) {
        return episodes.stream()
                .map(e -> toEpisodeDto(e))
                .collect(Collectors.toList());
    }

    private EpisodeDTO toEpisodeDto(Episode e) {
        return new EpisodeDTO(e.getId(), e.getSeasonNumber(), e.getTitle(), e.getEpisodeNumber());
    }

    public List<SeriesDTO> getAllSeries() {
        return toSeriesDtoList(repository.findAll());
    }

    public List<SeriesDTO> getTop5Series() {
        return toSeriesDtoList(repository.findTop5ByOrderByRatingDesc());
    }

    public List<SeriesDTO> getRecentReleases() {
        return toSeriesDtoList(repository.findRecentSeries());
    }

    public SeriesDTO getSeriesById(Long id) {
        Optional<Series> series = repository.findById(id);

        return series.map(this::toSeriesDto).orElse(null);
    }

    public List<EpisodeDTO> getAllSeasons(Long id) {
        Optional<Series> series = repository.findById(id);

        return series.map(s -> toEpisodeDtoList(s.getEpisodes())).orElse(Collections.emptyList());
    }

    public List<EpisodeDTO> getSeasonById(Long id, Long seasonId) {
        Optional<Series> series = repository.findById(id);

        return series
                .map(s -> toEpisodeDtoList(s.getEpisodes().stream().filter(e -> e.getSeasonNumber() == seasonId)
                        .collect(Collectors.toList())))
                .orElse(Collections.emptyList());
    }

    public List<SeriesDTO> getSeriesByGenre(String genre) {
        try {
            Genre searchGenre = Genre.fromString(genre);
            return toSeriesDtoList(repository.findByGenre(searchGenre));
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<EpisodeDTO> getTop5EpisodesBySeriesId(Long id) {
        Optional<Series> series = repository.findById(id);
        return series
                .map(s -> toEpisodeDtoList(repository.findTop5Episodes(s)))
                .orElse(Collections.emptyList());
    }
}
