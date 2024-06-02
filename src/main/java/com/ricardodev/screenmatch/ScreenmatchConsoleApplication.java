package com.ricardodev.screenmatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ricardodev.screenmatch.main.Main;
import com.ricardodev.screenmatch.repository.SeriesRepository;
import com.ricardodev.screenmatch.service.SecretsService;

@SpringBootApplication
@EnableConfigurationProperties(SecretsService.class)
public class ScreenmatchConsoleApplication implements CommandLineRunner {

    @Autowired
    private SeriesRepository repository;
    @Autowired
    private SecretsService secretsService;
    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Main main = new Main(repository, secretsService);
        main.showMenu();
    }
}
