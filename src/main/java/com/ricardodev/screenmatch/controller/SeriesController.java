package com.ricardodev.screenmatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricardodev.screenmatch.dto.EpisodeDTO;
import com.ricardodev.screenmatch.dto.SeriesDTO;
import com.ricardodev.screenmatch.model.MovieQuote;
import com.ricardodev.screenmatch.service.MovieQuoteService;
import com.ricardodev.screenmatch.service.SeriesService;

@RestController
@RequestMapping("/series")
public class SeriesController {
    @Autowired
    private SeriesService seriesService;

    @Autowired
    private MovieQuoteService quoteService;

    @GetMapping()
    public List<SeriesDTO> getSeries() {
        return seriesService.getAllSeries();
    }

    @GetMapping("/top5")
    public List<SeriesDTO> getTop5Series() {
        return seriesService.getTop5Series();
    }

    @GetMapping("recent-releases")
    public List<SeriesDTO> getRecentReleases() {
        return seriesService.getRecentReleases();
    }

    @GetMapping("/{id}")
    public SeriesDTO getSeriesById(@PathVariable Long id) {
        return seriesService.getSeriesById(id);
    }

    @GetMapping("/{id}/seasons/all")
    public List<EpisodeDTO> getAllSeasons(@PathVariable Long id) {
        return seriesService.getAllSeasons(id);
    }

    @GetMapping("/{id}/seasons/{seasonId}")
    public List<EpisodeDTO> getSeasonById(@PathVariable Long id, @PathVariable Long seasonId) {
        return seriesService.getSeasonById(id, seasonId);
    }

    @GetMapping("/{id}/seasons/top")
    public List<EpisodeDTO> getTop5EpisodesBySeriesId(@PathVariable Long id) {
        return seriesService.getTop5EpisodesBySeriesId(id);
    }

    @GetMapping("/genre/{genre}")
    public List<SeriesDTO> getSeriesBygenre(@PathVariable String genre) {
        return seriesService.getSeriesByGenre(genre);
    }

    @GetMapping("/frases")
    public MovieQuote getRandomQuote() {
        return quoteService.getRandomQuote();
    }
}
