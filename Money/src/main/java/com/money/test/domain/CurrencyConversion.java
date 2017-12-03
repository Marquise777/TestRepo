package com.money.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oleg on 30.11.2017.
 */
@Data
public class CurrencyConversion implements Serializable {
    private String base;
    private String date;
    private String counterCurrency;
    private Map<String, Object> conversionRates;

    public CurrencyConversion(){
    }

    @JsonProperty("rates")
    public void setRates(Map<String, Object> rates) {
        for(Map.Entry<String, Object> entry: rates.entrySet()){
            setCounterCurrency(entry.getKey());
        }
        conversionRates = rates;
    }
}
