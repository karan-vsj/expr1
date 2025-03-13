package com.examly.springapp.service;

import com.examly.springapp.entity.Weather;
import com.examly.springapp.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WeatherService {

@Value("${weather.api.key}")
private String apiKey;

@Value("${weather.api.url}")
private String apiUrl;

private final RestTemplate restTemplate;
private final WeatherRepository weatherRepository;

public WeatherService(RestTemplate restTemplate, WeatherRepository weatherRepository) {
this.restTemplate = restTemplate;
this.weatherRepository = weatherRepository;
}

public Weather getWeatherByCity(String city) {
Optional<Weather> cachedWeather = weatherRepository.findTopByCityOrderByTimestampDesc(city);
if (cachedWeather.isPresent() && cachedWeather.get().getTimestamp().isAfter(LocalDateTime.now().minusMinutes(30))) {
return cachedWeather.get();
}

String url = apiUrl.replace("{city}", city).replace("{key}", apiKey);
WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);
if (response == null || response.getMain() == null) {
return null;
}

Weather weather = new Weather();
weather.setCity(city);
weather.setTemperature(response.getMain().getTemp());
weather.setDescription(response.getWeather().get(0).getDescription());
weather.setHumidity(response.getMain().getHumidity());
weather.setWindSpeed(response.getWind().getSpeed());
weather.setPressure(response.getMain().getPressure());
weather.setTimestamp(LocalDateTime.now());

weatherRepository.save(weather);
return weather;
    }
    }
                                                                        