/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.quipux.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author ander
 */
@Service
public class SpotifyService {

    private final WebClient webClient;

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    public SpotifyService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.spotify.com/v1").build();
    }

    public String obtenerToken() {
        String credentials = clientId + ":" + clientSecret;
        String base64 = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());

        WebClient tokenClient = WebClient.builder()
                .baseUrl("https://accounts.spotify.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + base64)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        Map<String, Object> response = tokenClient.post()
                .uri("/api/token")
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return (String) response.get("access_token");
    }

    public List<String> obtenerGeneros() {
        String token = obtenerToken();

        //.uri("/browse/new-releases")
        Map<String, Object> response = webClient.get()
                .uri("/recommendations/available-genre-seeds")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return (List<String>) response.get("genres");
    }
}
