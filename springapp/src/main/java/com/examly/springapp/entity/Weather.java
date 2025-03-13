package com.examly.springapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Weather {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String city;
private double temperature;
private String description;
private int humidity;
private double windSpeed;
private int pressure;
private LocalDateTime timestamp;

// Constructors, getters, and setters
}
