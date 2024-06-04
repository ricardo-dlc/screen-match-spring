package com.ricardodev.screenmatch.service;

import java.util.List;
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

    private List<SeriesDTO> convertSeriesData(List<Series> series) {
        return series.stream()
                .map(s -> new SeriesDTO(
                        s.getId(),
                        s.getTitle(),
                        s.getTotalSeasons(),
                        s.getRating(),
                        s.getGenre(),
                        s.getActors(),
                        s.getPlot(),
                        s.getPoster()))
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
}
