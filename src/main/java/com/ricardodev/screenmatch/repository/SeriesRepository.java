package com.ricardodev.screenmatch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ricardodev.screenmatch.model.Episode;
import com.ricardodev.screenmatch.model.Genre;
import com.ricardodev.screenmatch.model.Series;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByTitleContainsIgnoreCase(String seriesName);

    List<Series> findTop5ByOrderByRatingDesc();

    List<Series> findByGenre(Genre genre);

    List<Series> findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(Integer seasons, Double rating);

    @Query("SELECT s FROM Series s WHERE s.totalSeasons <= :seasons AND s.rating >= :rating")
    List<Series> findBySeasonsAndRating(Integer seasons, Double rating);

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE e.title ILIKE %:episodeName%")
    List<Episode> findEpisodeByName(String episodeName);

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE s = :series ORDER BY e.rating DESC LIMIT 5")
    List<Episode> findTop5Episodes(Series series);

    @Query("SELECT s FROM Series s JOIN s.episodes e GROUP BY s ORDER BY MAX(e.releaseDate) DESC LIMIT 5")
    List<Series> findRecentSeries();
}
