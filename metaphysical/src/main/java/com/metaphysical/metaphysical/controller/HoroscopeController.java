package com.metaphysical.metaphysical.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.io.*;

@RestController
@RequestMapping("/api/horoscope")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // allow your frontend
public class HoroscopeController {

    private final String apiKey = "dcK6WOXwUM4jxCEkdtZo175ImDtBjmQI631caiCH";

    @PostMapping("/planets")
    public ResponseEntity<String> getPlanets(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody String payload) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\":\"User not authenticated\"}");
        }

        try {
            URL url = new URL("https://json.freeastrologyapi.com/western/planets");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-api-key", apiKey);
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            int status = conn.getResponseCode();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    status >= 200 && status < 300 ? conn.getInputStream() : conn.getErrorStream()
            ))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    content.append(line);
                }
                return ResponseEntity.status(status)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(content.toString());
            } finally {
                conn.disconnect();
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Failed to fetch horoscope\"}");
        }
    }
}
