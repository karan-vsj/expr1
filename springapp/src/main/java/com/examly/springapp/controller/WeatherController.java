package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WeatherController {

@Value("${weather.api.key}")
private String apiKey;

@Value("${weather.api.url}")
private String apiUrl;

@Autowired
private RestTemplate restTemplate;

@GetMapping("/weather")
public ResponseEntity<?> getWeatherByCity(@RequestParam(required = false) String city) {
    if (city == null || city.trim().isEmpty()) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "City parameter is required");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
        String url = apiUrl.replace("{city}", city).replace("{key}", apiKey);

        Object weatherResponse = restTemplate.getForObject(url, Object.class);
        return ResponseEntity.ok(weatherResponse);
        } catch (HttpClientErrorException.NotFound e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "City not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            }
        }