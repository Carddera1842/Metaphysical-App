package com.metaphysical.metaphysical.dto;

import lombok.Data;

@Data
public class HoroscopeRequest {
    private int year;
    private int month;
    private int date;
    private int hours;
    private int minutes;
    private int seconds;
    private double latitude;
    private double longitude;
    private double timezone;

    private Config config;

    @Data
    public static class Config {
        private String observation_point; // topocentric or geocentric
        private String ayanamsha;         // tropical, sayana, lahiri
        private String language;          // en, es, fr, etc.
    }
}
