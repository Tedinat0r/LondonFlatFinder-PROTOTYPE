/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lff.Tests;
import org.junit.Test;
import org.junit.Assert;
import com.mycompany.lff.Services.ScraperServices.ScraperUtils.SearchQueryGen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class URLGenTests {


    private void FindURLDifference(String TargetURL, String TestFunc) {
        char[] Real_URL = TargetURL.toCharArray();
        char[] Expected_URL = TestFunc.toCharArray();
        for (int i = 0; i < Real_URL.length; i++) {
            if (Real_URL[i] != Expected_URL[i]) {
                int EndPoint;
                if (i > Expected_URL.length) {
                    EndPoint = i - Expected_URL.length;
                } else {
                    EndPoint = Expected_URL.length - i;
                }
                String Violation = new String(Expected_URL, i, EndPoint);
                System.out.println("AssertionError: The URLS are not identical");
                System.out.println(Violation);
                break;
            }
        }
    }

    @Test
    public void RightMoveQueryTest() throws IOException {
    String TargetURL = "https://www.rightmove.co.uk/property-to-rent/find.html?search"
        + "Location=N16&useLocationIdentifier=true&locationIdentifier="
        + "OUTCODE%5E1673&radius=0.0&maxPrice=1100&maxBedrooms=1&"
        + "_includeLetAgreed=on&keywords=Furnished";        
        List<String> SampleTerms = new ArrayList();
        SampleTerms.add("N16");
        SampleTerms.add("1100");
        SampleTerms.add("1");
        SampleTerms.add("Furnished");
        
        
        String TestFunc = new SearchQueryGen()
                              .URLFactory(SampleTerms, "RightMove");
        try {
            Assert.assertSame(TargetURL, TestFunc);
        }
        catch (AssertionError e){
           this.FindURLDifference(TargetURL, TestFunc);

        }
        
    }

    @Test
    public void OnTheMarketTest() throws IOException {
        String TargetURL = "https://www.onthemarket.com/to-rent/property/n16/?furnished=furnished&max-bedrooms=1" +
                            "&max-price=1250&radius=0.5&view=map-list";
        List<String> SampleTerms = new ArrayList();
        SampleTerms.add("N16");
        SampleTerms.add("1100");
        SampleTerms.add("1");



        String TestFunc = new SearchQueryGen()
                .URLFactory(SampleTerms, "OnTheMarket");
        try {
            Assert.assertSame(TargetURL, TestFunc);
        }
        catch (AssertionError e){
            this.FindURLDifference(TargetURL, TestFunc);
        }
    }

    @Test
    public void OpenRentTest() throws IOException {
        String TargetURL = "https://www.onthemarket.com/to-rent/property/n16/?furnished=furnished&max-bedrooms=1" +
                "&max-price=1250&radius=0.5&view=map-list";
        List<String> SampleTerms = new ArrayList();
        SampleTerms.add("N16");
        SampleTerms.add("1100");



        String TestFunc = new SearchQueryGen()
                .URLFactory(SampleTerms, "OpenRent");
        try {
            Assert.assertSame(TargetURL, TestFunc);
        }
        catch (AssertionError e){
            this.FindURLDifference(TargetURL, TestFunc);
        }
    }
}
