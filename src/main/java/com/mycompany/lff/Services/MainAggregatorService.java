package com.mycompany.lff.Services;


import com.mycompany.lff.Flat;
import com.mycompany.lff.Services.ScraperServices.OnTheMarketScraper;
import com.mycompany.lff.Services.ScraperServices.OpenRentScraper;
import com.mycompany.lff.Services.ScraperServices.RightMoveScraper;
import com.mycompany.lff.Services.ScraperServices.ScraperUtils.SearchQueryGen;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.PersistenceContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class MainAggregatorService {


    private FlatService flatService;

    @Autowired
    public MainAggregatorService(FlatService flatService) throws IOException {
        this.flatService = flatService;
    }

    public List<List<Object>> GetCheapest() throws IOException {
        return flatService.Aggregate();
    }





}
