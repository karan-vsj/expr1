package com.examly.springapp.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

public Long getId() {
    return id;
}
public void setId(Long id) {
    this.id = id;
}
public String getCity() {
    return city;
}
public void setCity(String city) {
    this.city = city;
}
public double getTemperature() {
    return temperature;
}
public void setTemperature(double temperature) {
    this.temperature = temperature;
}
public String getDescription() {
    return description;
}
public void setDescription(String description) {
    this.description = description;
}
public int getHumidity() {
    return humidity;
}
public void setHumidity(int humidity) {
    this.humidity = humidity;
}
public double getWindSpeed() {
    return windSpeed;
}
public void setWindSpeed(double windSpeed) {
    this.windSpeed = windSpeed;
}
public int getPressure() {
    return pressure;
}
public void setPressure(int pressure) {
    this.pressure = pressure;
}
public LocalDateTime getTimestamp() {
    return timestamp;
}
public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
}
public Weather() {
}
public Weather(Long id, String city, double temperature, String description, int humidity, double windSpeed,
        int pressure, LocalDateTime timestamp) {
    this.id = id;
    this.city = city;
    this.temperature = temperature;
    this.description = description;
    this.humidity = humidity;
    this.windSpeed = windSpeed;
    this.pressure = pressure;
    this.timestamp = timestamp;
}






}
