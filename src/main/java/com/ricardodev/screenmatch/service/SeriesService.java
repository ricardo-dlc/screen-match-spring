package com.ricardodev.screenmatch.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardodev.screenmatch.dto.SeriesDTO;
import com.ricardodev.screenmatch.model.Series;
import com.ricardodev.screenmatch.repository.SeriesRepository;

@Service
public class SeriesService {
    @Autowired
    private SeriesRepository repository;

    private SeriesDTO convertToDto(Series series) {
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

    private List<SeriesDTO> convertSeriesData(List<Series> series) {
        return series.stream()
                .map(s -> convertToDto(s))
                .collect(Collectors.toList());
    }

    public List<SeriesDTO> getAllSeries() {
        return convertSeriesData(repository.findAll());
    }

    public List<SeriesDTO> getTop5Series() {
        return convertSeriesData(repository.findTop5ByOrderByRatingDesc());
    }

    public List<SeriesDTO> getRecentReleases() {
        return convertSeriesData(repository.findRecentSeries());
    }

    public SeriesDTO getSeriesById(Long id) {
        Optional<Series> series = repository.findById(id);

        return series.map(this::convertToDto).orElse(null);
    }
}
