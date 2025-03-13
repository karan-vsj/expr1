package com.examly.springapp;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.TestPropertySource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application.properties")
class SpringappApplicationTests {

	@Autowired
    private MockMvc mockMvc;

    private final String baseUrl = "/weather";

	@Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Order(1)
    @Test
    void testGetWeatherByCity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/weather")
        .param("city", "London")
        .accept(MediaType.ALL))  // Accepts any response type
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.main.temp").exists()); // Example check for temperature field

    }

    @Order(2)
    @Test
    void testGetWeatherByInvalidCity() throws Exception {
		mockMvc.perform(get("/weather")
				.param("city", "InvalidCity") // Sending an invalid city name
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
    }

    @Order(3)
    @Test
    void testGetWeatherWithoutCityParameter() throws Exception {
        mockMvc.perform(get(baseUrl))
                .andExpect(status().isBadRequest());
    }

    @Order(4)
    @Test
    void testWeatherApiResponseContainsTemperature() throws Exception {
        mockMvc.perform(get(baseUrl + "?city=New York"))
                .andExpect(jsonPath("$.main.temp").exists());
    }

    @Order(5)
    @Test
    void testWeatherApiResponseContainsWeatherDescription() throws Exception {
        mockMvc.perform(get(baseUrl + "?city=Paris"))
                .andExpect(jsonPath("$.weather[0].description").exists());
    }

    @Order(6)
    @Test
    void testWeatherApiResponseContainsHumidity() throws Exception {
        mockMvc.perform(get(baseUrl + "?city=Tokyo"))
                .andExpect(jsonPath("$.main.humidity").exists());
    }

    @Order(7)
    @Test
    void testWeatherApiResponseContainsWindSpeed() throws Exception {
        mockMvc.perform(get(baseUrl + "?city=Berlin"))
                .andExpect(jsonPath("$.wind.speed").exists());
    }

    @Order(8)
    @Test
    void testWeatherApiResponseContainsPressure() throws Exception {
        mockMvc.perform(get(baseUrl + "?city=Sydney"))
                .andExpect(jsonPath("$.main.pressure").exists());
    }

    @Test
    void testFolderStructure() {
        String[] directories = {
                "src/main/java/com/examly/springapp/controller"
        };
        for (String directoryPath : directories) {
            File directory = new File(directoryPath);
            assertTrue(directory.exists() && directory.isDirectory(), "Directory not found: " + directoryPath);
        }
    }

    @Test
    void testControllerFileExistence() {
        String filePath = "src/main/java/com/examly/springapp/controller/WeatherController.java";
        File file = new File(filePath);
        assertTrue(file.exists() && file.isFile(), "File not found: " + filePath);
    }

    @Test
    void testApplicationPropertiesExistence() {
        String filePath = "src/main/resources/application.properties";
        File file = new File(filePath);
        assertTrue(file.exists() && file.isFile(), "File not found: " + filePath);
    }

    @Test
    void testApiKeyIsConfigured() {
        assertTrue(apiKey != null && !apiKey.isEmpty(), "API Key is not configured properly");
    }

    @Test
    void testApiUrlIsConfigured() {
        assertTrue(apiUrl != null && !apiUrl.isEmpty(), "API URL is not configured properly");
    }

	@Test
    void testApiKeyIsNotEmpty() {
        // Additional test to ensure that the API Key is not empty
        assertTrue(apiKey.trim().length() > 0, "API Key should not be empty");
    }

	@Test
    void testApiUrlInjectionWithValueAnnotation() {
        // Check if the @Value annotation correctly injected the apiUrl
        assertNotNull(apiUrl, "API URL should be injected with @Value annotation");
    }

}
