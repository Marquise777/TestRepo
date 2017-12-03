package com.money.test.controllers;

import com.money.test.domain.CurrencyConversion;
import com.money.test.services.MoneyService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
 * Created by oleg on 30.11.2017.
 */
@RestController("/")
public class CurrencyController {
    private MoneyService moneyService;

    public  CurrencyController(MoneyService ms){
        moneyService = ms;
    }

    @RequestMapping("/currency/conversion")
    public Map<String, Object> conversionRate(@RequestParam("base") String base,
                                              @RequestParam("counter") String counterCurrency,
                                              @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){

        return moneyService.conversionRate(base, counterCurrency, date);
    }
    @RequestMapping("/currency/conversion/dollars")
    public Map<String, Object> valueInDollar(@RequestParam("counter")  String counterCurrency,
                                             @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){

        return moneyService.valueInDollar(counterCurrency, date);
    }
}
