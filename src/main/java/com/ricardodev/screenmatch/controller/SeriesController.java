package com.ricardodev.screenmatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricardodev.screenmatch.dto.SeriesDTO;
import com.ricardodev.screenmatch.service.SeriesService;

@RestController
public class SeriesController {
    @Autowired
    private SeriesService service;

    @GetMapping("/series")
    public List<SeriesDTO> getSeries() {
        return service.getAllSeries();
    }

    @GetMapping("/series/top5")
    public List<SeriesDTO> getTop5Series() {
        return service.getTop5Series();
    }
}
