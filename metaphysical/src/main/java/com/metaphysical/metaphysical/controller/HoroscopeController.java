package com.metaphysical.metaphysical.controller;

import com.metaphysical.metaphysical.dto.HoroscopeRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/horoscope")
public class HoroscopeController {

    private final RestTemplate restTemplate;
    private final String apiKey = "dcK6WOXwUM4jxCEkdtZo175ImDtBjmQI631caiCH";

    public HoroscopeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/planets")
    public ResponseEntity<?> getPlanetaryPositions(@RequestBody HoroscopeRequest request) {
        try {
            String apiUrl = "https://json.freeastrologyapi.com/western/planets";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", apiKey);  // ✅ MUST be this

            HttpEntity<HoroscopeRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl, HttpMethod.POST, entity, String.class);

            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException.Forbidden e) {
            Map<String, String> fallback = new HashMap<>();
            fallback.put("description", "Planetary data service temporarily unavailable (403).");
            fallback.put("status", "error");
            return ResponseEntity.status(403).body(fallback);
        } catch (Exception e) {
            Map<String, String> fallback = new HashMap<>();
            fallback.put("description", "Planetary data service temporarily unavailable.");
            fallback.put("status", "error");
            return ResponseEntity.status(503).body(fallback);
        }
    }
}
