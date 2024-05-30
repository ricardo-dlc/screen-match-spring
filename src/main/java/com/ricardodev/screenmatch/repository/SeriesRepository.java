package com.ricardodev.screenmatch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricardodev.screenmatch.model.Genre;
import com.ricardodev.screenmatch.model.Series;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByTitleContainsIgnoreCase(String seriesName);

    List<Series> findTop5ByOrderByRatingDesc();

    List<Series> findByGenre(Genre genre);

    List<Series> findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(Integer seasons, Double rating);
}
