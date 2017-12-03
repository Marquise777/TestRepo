package com.weather.test.controllers;

import com.weather.test.services.WeatherService;
import com.weather.test.weather.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by oleg on 29.11.2017.
 */
@RestController("/")
public class WeatherController {
    private WeatherService weatherService;

    public WeatherController(WeatherService ws){
        weatherService = ws;
    }

    @RequestMapping("/weather/{city}")
    public Map<String, Object> weatherIn(@PathVariable("city")  String city){
        return weatherService.weatherIn(city);
    }
}
