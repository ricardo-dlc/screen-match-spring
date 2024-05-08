package com.ricardodev.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ricardodev.screenmatch.model.SeriesData;
import com.ricardodev.screenmatch.service.ApiConsuming;
import com.ricardodev.screenmatch.service.DataConverter;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // System.out.println("Hello World from Spring!");
        Dotenv env = Dotenv.load();
        String API_KEY = env.get("API_KEY");
        String apiEndpoint = "http://www.omdbapi.com/?apikey=%s&t=futurama".formatted(API_KEY);

        DataConverter dataCOnverter = new DataConverter();
        ApiConsuming api = new ApiConsuming();
        String json = api.getData(apiEndpoint);
        SeriesData data = dataCOnverter.getData(json, SeriesData.class);
        System.out.println(data);
    }

}
