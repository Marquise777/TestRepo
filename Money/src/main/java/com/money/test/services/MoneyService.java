package com.money.test.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.money.test.domain.CurrencyConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import static java.lang.Double.parseDouble;

/**
 * Created by oleg on 30.11.2017.
 */
@Service
public class MoneyService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${currency.conversion.url}")
    private String currencyUrl;

    public Map<String, Object> conversionRate(String base, String countCurrency, LocalDate dt){
        URI url = new UriTemplate(currencyUrl).expand(dt.toString(), base, countCurrency);
        CurrencyConversion cConversion = invoke(url, CurrencyConversion.class);

        return convert(cConversion);
    }
    public Map<String, Object> valueInDollar(String counterCurrency, LocalDate dt){
        URI url = new UriTemplate(currencyUrl).expand(dt.toString(), counterCurrency, "USD");
        CurrencyConversion cc = invoke(url, CurrencyConversion.class);

        return convert(cc);
    }

    private Map<String, Object> convert(CurrencyConversion cc){
        if(cc == null){
            cc = new CurrencyConversion();
        }

        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> moneyMap = oMapper.convertValue(cc, Map.class);

        return moneyMap;
    }
    private <T> T invoke(URI url, Class<T> responseType){
        T response = null;

        try {
            RequestEntity<?> request = RequestEntity.get(url)
                    .accept(MediaType.APPLICATION_JSON).build();
            ResponseEntity<T> exchange = this.restTemplate
                    .exchange(request, responseType);

            //if (exchange.getStatusCode().is2xxSuccessful()) {
                response = exchange.getBody();
            //}
        } catch (Exception ex){
            //System.out.println(ex.toString());
        }
        return response;
    }
}
