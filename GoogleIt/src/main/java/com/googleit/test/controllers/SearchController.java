package com.googleit.test.controllers;

import com.googleit.test.services.SearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Map;

/**
 * Created by oleg on 30.11.2017.
 */
@RestController
public class SearchController {
    private SearchService searchService;

    public SearchController(SearchService ss){
        searchService = ss;
    }

    @RequestMapping("/google/lucky")
    public Map<String, Object> iFeelLucky(@RequestParam("term") String term){
        return searchService.iFeelLucky(term);
    }
    @RequestMapping("/google/results/amount")
    public Map<String, Object> howManyResults(@RequestParam("term") String term){
        return searchService.howManyResults(term);
    }
}
