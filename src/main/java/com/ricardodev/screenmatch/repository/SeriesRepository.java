package com.ricardodev.screenmatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricardodev.screenmatch.model.Series;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByTitleContainsIgnoreCase(String seriesName);
}
