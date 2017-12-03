package com.weather.test.services;

import java.net.URI;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.test.weather.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

/**
 * Created by oleg on 29.11.2017.
 */
@Service
public class WeatherService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${open.weather.map.url}")
    private String weatherUrl;
    @Value("${open.weather.map.apiKey}")
    private String apiKey;

    public Map<String, Object> weatherIn(String city){
        URI url = new UriTemplate(weatherUrl).expand(city, this.apiKey);
        Weather weather = invoke(url, Weather.class);

        if(weather == null){
            weather = new Weather();
        }

        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> weatherMap = oMapper.convertValue(weather, Map.class);

        return  weatherMap;
    }

    private <T> T invoke(URI url, Class<T> responseType){
        T response = null;

        try {
            RequestEntity<?> request = RequestEntity.get(url)
                    .accept(MediaType.APPLICATION_JSON).build();
            ResponseEntity<T> exchange = this.restTemplate
                    .exchange(request, responseType);

            if(exchange.getStatusCode().is2xxSuccessful()){
                response = exchange.getBody();
            }
        } catch(Exception ex){
            System.out.println(ex.toString());
        }

        return response;
    }
}
