package com.examly.springapp.repository;

import com.examly.springapp.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findTopByCityOrderByTimestampDesc(String city);
    }
