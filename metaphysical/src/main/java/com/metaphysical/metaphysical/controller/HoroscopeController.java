package com.metaphysical.metaphysical.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/api/horoscope")
public class HoroscopeController {

    private final RestTemplate restTemplate;

    public HoroscopeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/daily/{sign}")
    public ResponseEntity<?> getDailyHoroscope(@PathVariable String sign) {
        // aztro API URL
        String url = "https://aztro.sameerkumar.website/";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sign", sign.toLowerCase())
                .queryParam("day", "today");

        // POST request to aztro API
        ResponseEntity<Map> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                HttpEntity.EMPTY,
                Map.class
        );

        return ResponseEntity.ok(response.getBody());
    }
}
