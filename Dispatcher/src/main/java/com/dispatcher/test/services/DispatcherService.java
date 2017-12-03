package com.dispatcher.test.services;

import com.dispatcher.test.domain.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oleg on 01.12.2017.
 */
@Service
public class DispatcherService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${weather.service.url}")
    private String weatherUrl;
    @Value("${currency.conversion.service.url}")
    private String currencyUrl;
    @Value("${currency.conversion.dollars.url}")
    private String conversionToDollarsUrl;
    @Value("${google.iFeelLucky.url}")
    private String iFeelLuckyUrl;
    @Value("${google.howManyResults.url}")
    private String howManyResultsUrl;

    public Map<Integer, String[]> weatherIn(String city){
        URI url = new UriTemplate(weatherUrl).expand(city);

        return invoke(url, ServiceType.WEATHER);
    }
    public Map<Integer, String[]> conversionRate(String base, String counterCurrency, LocalDate date){
        URI url = new UriTemplate(currencyUrl).expand(base, counterCurrency, date.toString());

        return invoke(url, ServiceType.MONEY);
    }
    public Map<Integer, String[]> valueInDollar(String counterCurrency, LocalDate date){
        URI url = new UriTemplate(conversionToDollarsUrl).expand(counterCurrency, date.toString());

        return invoke(url, ServiceType.MONEY);
    }

    public Map<Integer, String[]> iFeelLucky(String term){
        URI url = new UriTemplate(iFeelLuckyUrl).expand(term);

        return  invoke(url, ServiceType.GOOGLE);
    }
    public Map<Integer, String[]> howManyResults(String term){
        URI url = new UriTemplate(howManyResultsUrl).expand(term);

        return  invoke(url, ServiceType.GOOGLE);
    }

    private Map<Integer, String[]> invoke(URI url, ServiceType serviceType){
        RequestEntity<?> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<?> exchange = this.restTemplate
                .exchange(request, Map.class);

        Map<Integer, String[]> response = new HashMap<>();

        if(exchange.getStatusCode().is2xxSuccessful()){
            Map<String, Object> body = (Map<String, Object>) exchange.getBody();
            response = convert(body, serviceType);
        }

        return response;
    }

    private Map<Integer, String[]> convert(Map<String, Object> input, ServiceType serviceType){
        List<String> parameters = new ArrayList<>();
        Map<Integer, String[]> outFormat = new HashMap<>();

        for(Map.Entry<String, Object> entry: input.entrySet()){
            String param = entry.getKey() + "=" + entry.getValue();
            parameters.add(param);
        }
        outFormat.put(serviceType.getNumValue(), parameters.toArray(new String[parameters.size()]));

        return outFormat;
    }
}
