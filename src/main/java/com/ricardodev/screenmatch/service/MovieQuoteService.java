package com.ricardodev.screenmatch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardodev.screenmatch.model.MovieQuote;
import com.ricardodev.screenmatch.repository.MovieQuoteRepository;

@Service
public class MovieQuoteService {
    @Autowired
    private MovieQuoteRepository repository;

    public MovieQuote save(MovieQuote quote) {
        return repository.save(quote);
    }

    public MovieQuote getRandomQuote() {
        return repository.getRandomQuote();
    }
}
