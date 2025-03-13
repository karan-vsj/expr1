package com.examly.springapp.controller;

import com.examly.springapp.entity.Weather;
import com.examly.springapp.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

private final WeatherService weatherService;

public WeatherController(WeatherService weatherService) {
this.weatherService = weatherService;
}

@GetMapping
public ResponseEntity<?> getWeather(@RequestParam(required = false) String city) {
if (city == null || city.isEmpty()) {
return ResponseEntity.badRequest().body("City parameter is required");
}
try {
com.examly.springapp.entity.Weather weather = weatherService.getWeatherByCity(city);
if (weather == null) {
return ResponseEntity.status(404).body("City not found");
}
return ResponseEntity.ok(weather);
} catch (Exception e) {
return ResponseEntity.status(500).body("An error occurred");
}
}
}
