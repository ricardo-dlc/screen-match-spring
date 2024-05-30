package com.ricardodev.screenmatch.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties
@Validated
public class SecretsService {
    private String omdbApiKey;

    public String getOmdbApiKey() {
        return omdbApiKey;
    }

    public void setOmdbApiKey(String omdbApiKey) {
        this.omdbApiKey = omdbApiKey;
    }
}
