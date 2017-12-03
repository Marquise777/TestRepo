package com.googleit.test.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oleg on 30.11.2017.
 */
@Service
public class SearchService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${search.it.url}")
    private String searchrUrl;
    @Value("${search.results.amount.url}")
    private String resultAmountrUrl;

    public Map<String, Object> iFeelLucky(String term){
        URI url = new UriTemplate(searchrUrl).expand(term);
        String response = invoke(url, String.class);

        if(response == null){
            response = "Not found";
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("I feel lucky", response);

        return  resultMap;
    }
    public Map<String, Object> howManyResults(String term){
        int result = -1;
        String key = null;

        try {
            URI url = new UriTemplate(resultAmountrUrl).expand(term);

            String html = invoke(url, String.class);

            if (html != null) {

                Document document = Jsoup.parse(html);
                String text = document.select("#resultStats").first().ownText();

                System.out.println(text);

                result = Integer.parseInt(text.trim().replaceAll("[^\\d]", ""));
                key = "How many results";
            }
        } catch(Exception ex){
            key = ex.toString();
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(key, result);

        return resultMap;
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
        }catch(Exception ex){
            System.out.println(ex.toString());
        }

        return response;
    }
}
