package com.ricardodev.screenmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricardodev.screenmatch.model.Series;

public interface SeriesRepository extends JpaRepository<Series, Long> {

}
