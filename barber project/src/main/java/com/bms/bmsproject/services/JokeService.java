package com.bms.bmsproject.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.bms.bmsproject.models.Joke;

@Service
public class JokeService {
    private static final String API_URL = "https://api.api-ninjas.com/v1/jokes";
    private static final String API_KEY = "5bxSbmfaNIc6ky4wDygGFg==ACyrHKbBy9EVM7qM";

    @Autowired
    private final RestTemplate restTemplate = new RestTemplate();

    public Joke[] getJokes() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Joke[]> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity,
                    Joke[].class);

            System.out.println("Vim -_-_-_-_-_-_-_-_ tony-sexy");
            System.out.print(response.getBody());
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Unexpected response format: " + e.getMessage());
            return new Joke[0];
        }
    }
}
