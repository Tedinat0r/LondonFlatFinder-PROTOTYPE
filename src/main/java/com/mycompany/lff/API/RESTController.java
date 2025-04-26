/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.lff.API;
import com.mycompany.lff.Services.ScraperServices.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mycompany.lff.Services.MainAggregatorService;

import java.util.concurrent.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api")
class RESTController {


    public  RESTController() throws IOException {};

    @Autowired
    private MainAggregatorService Aggregator;

    @Autowired
    private ScraperService Scraper;


    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/search")
    public ResponseEntity<String> Search(@RequestBody List<String> Terms) throws IOException, ParseException{
        Scraper.ScrapeFlats(Terms);
        ResponseEntity response = new ResponseEntity<>("Queries recieved and processed", HttpStatus.OK);

    return response;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/fetchresults")
    public ResponseEntity<?> GetResults() throws IOException {
        List<List<Object>> Results = Aggregator.GetCheapest();
        return new ResponseEntity<>(Results, HttpStatus.OK);
    }

}







