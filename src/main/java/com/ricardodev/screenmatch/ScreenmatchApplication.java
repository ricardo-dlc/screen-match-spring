package com.ricardodev.screenmatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ricardodev.screenmatch.main.Main;
import com.ricardodev.screenmatch.repository.SeriesRepository;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    @Autowired
    private SeriesRepository repository;
    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Main main = new Main(repository);
        main.showMenu();
    }
}
