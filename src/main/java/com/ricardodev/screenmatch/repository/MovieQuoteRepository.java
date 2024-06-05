package com.ricardodev.screenmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ricardodev.screenmatch.model.MovieQuote;

public interface MovieQuoteRepository extends JpaRepository<MovieQuote, Long> {

    @Query("SELECT q from MovieQuote q ORDER BY RANDOM() LIMIT 1")
    MovieQuote getRandomQuote();

}
