package com.mycompany.lff.Services.ScraperServices;

import com.mycompany.lff.Services.FlatService;
import com.mycompany.lff.Services.ScraperServices.ScraperUtils.SearchQueryGen;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

@Service
public class ScraperService {




    private final RightMoveScraper RightMoveScraper;

    private final OnTheMarketScraper OnTheMarketScraper;

    private final OpenRentScraper OpenRentScraper;

    private SearchQueryGen SearchQueryGen;

    @Autowired
    ScraperService(RightMoveScraper rm, OnTheMarketScraper otm, OpenRentScraper or) throws IOException {
        this.RightMoveScraper = rm;
        this.OnTheMarketScraper = otm;
        this.OpenRentScraper = or;

        this.SearchQueryGen = new SearchQueryGen();
    }

    @Transactional
    public void ScrapeFlats(List<String> Terms) throws IOException {
        OnTheMarketScraper.ScrapeFlats(SearchQueryGen.URLFactory(Terms, "OnTheMarket"));
        RightMoveScraper.ScrapeFlats(SearchQueryGen.URLFactory(Terms, "RightMove"));
        OpenRentScraper.ScrapeFlats(SearchQueryGen.URLFactory(Terms, "OpenRent"));
    }

}
