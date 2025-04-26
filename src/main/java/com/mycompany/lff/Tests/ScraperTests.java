package com.mycompany.lff.Tests;

import org.junit.Test;
import com.mycompany.lff.Services.ScraperServices.RightMoveScraper;
import com.mycompany.lff.Services.ScraperServices.ScraperUtils.SearchQueryGen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScraperTests {
    List<String> QueryTerms = new ArrayList<>();
    SearchQueryGen TestQueries = new SearchQueryGen();

    public ScraperTests() throws IOException {

        QueryTerms.add("W1");
        QueryTerms.add("1200");
        QueryTerms.add("1");
        QueryTerms.add("Furnished");

    }


    //@Test
    /*public void RightMoveScraperTest() throws IOException {
        String BaseURL = this.TestQueries.URLFactory(QueryTerms, "RightMove");
        RightMoveScraper Scraper = new RightMoveScraper();

        try {
            Scraper.ScrapeFlats(BaseURL);
        } catch (IOException e){
            System.out.println(e.getMessage() + "\n The Query URL is most likely malformed.");

        } catch (NullPointerException e){
            System.out.println(e.getMessage() + "\n The CSS selectors found nothing, and so the flat was passed a null " +
                                                "value.");
        }
*/

    }


