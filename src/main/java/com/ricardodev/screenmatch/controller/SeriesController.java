package com.ricardodev.screenmatch.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricardodev.screenmatch.dto.SeriesDTO;
import com.ricardodev.screenmatch.repository.SeriesRepository;

@RestController
public class SeriesController {
    @Autowired
    private SeriesRepository repository;

    @GetMapping("/series")
    public List<SeriesDTO> getSeries() {
        return repository.findAll().stream()
                .map(s -> new SeriesDTO(
                        s.getTitle(),
                        s.getTotalSeasons(),
                        s.getRating(),
                        s.getGenre(),
                        s.getActors(),
                        s.getPlot(),
                        s.getPoster()))
                .collect(Collectors.toList());
    }
}
