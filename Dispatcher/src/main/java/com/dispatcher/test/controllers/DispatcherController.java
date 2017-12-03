package com.dispatcher.test.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dispatcher.test.services.DispatcherService;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by oleg on 01.12.2017.
 */
@RestController
public class DispatcherController {
    private DispatcherService dispatcherService;

    public DispatcherController(DispatcherService ds){
        dispatcherService = ds;
    }

    @RequestMapping("/dispatcher/weather")
    public Map<Integer, String[]> weatherIn(@RequestParam("city")  String city){
        return dispatcherService.weatherIn(city);
    }
    @RequestMapping("/dispatcher/money/rate")
    public Map<Integer, String[]> conversionRate(@RequestParam("base") String base,
                                                 @RequestParam("currency") String counterCurrency,
                                                 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){

        return dispatcherService.conversionRate(base, counterCurrency, date);
    }
    @RequestMapping("/dispatcher/money/rate/dollars")
    public Map<Integer, String[]> valueInDollar(@RequestParam("currency")  String counterCurrency,
                                             @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){

        return dispatcherService.valueInDollar(counterCurrency, date);
    }
    @RequestMapping("/dispatcher/google")
    public Map<Integer, String[]> iFeelLucky(@RequestParam("term") String term){

        return dispatcherService.iFeelLucky(term);
    }
    @RequestMapping("/dispatcher/google/results/amount")
    public Map<Integer, String[]> howMantResults(@RequestParam("term") String term){

        return dispatcherService.howManyResults(term);
    }
}
