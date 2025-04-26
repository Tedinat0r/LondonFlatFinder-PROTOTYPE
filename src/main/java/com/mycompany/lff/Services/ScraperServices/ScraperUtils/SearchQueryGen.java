/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lff.Services.ScraperServices.ScraperUtils;

import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SearchQueryGen {

    HashMap<String, Object> QueryDetails = new HashMap();

    ObjectMapper mapper = new ObjectMapper();

    File PCtoOC = Paths.get("src/main/resources/londonposts.JSON").toFile();

    List<Map<String, Object>> unmapped = mapper.readValue(PCtoOC, new TypeReference<>() {});
    Map<String, Object> OCs = new HashMap<>();


    public SearchQueryGen() throws IOException {
        for (Map<String, Object> obj : unmapped){
            OCs.putAll(obj);
        }
    }

    public void SearchQueryGen (){}  


    private String PostCodeToOutCode(Object PC){
        String postcode = String.valueOf(OCs.get(PC));
        String outcode = "5E".concat(postcode);

        return outcode;

    }

    private String ToRightMoveURL(List<String>QueryTerms){

       String QueryRoot = "https://www.rightmove.co.uk/property-to-rent/find.html?";
       String outcode = PostCodeToOutCode(QueryTerms.get(0));
       String UserQuery = QueryRoot + "searchLocation=" + QueryTerms.get(0) +
                          "&useLocationIdentifier=true&locationIdentifier=OUTCODE"
                        + "%"+outcode+"&radius=0.0" + "&maxPrice=" + QueryTerms.get(1)
                        + "&maxBedrooms=" + QueryTerms.get(2)+"&_includeLetAgreed=on"
                        + "&keywords=" + QueryTerms.get(3);
       return UserQuery;
    }
       
    
    private String ToOnTheMarketURL(List QueryTerms){
        
        String QueryRoot = "https://www.onthemarket.com/to-rent/property/";
        String Postcode = (String) QueryTerms.get(0);
        String UserQuery = QueryRoot + Postcode.toLowerCase() + "/?furnished&max-bedrooms="+QueryTerms.get(2)
                           + "&max-price=" + QueryTerms.get(1) + "&radius=0.5&"
                           + "sort-field=keywords&view=map-list";

        return UserQuery;
    }
    private String ToOpenRentURL(List QueryTerms){
      
        String QueryRoot = "https://www.openrent.co.uk/properties-to-rent/";
        String UserQuery = QueryRoot + QueryTerms.get(0)+ "?term="+ QueryTerms.get(0) + "&" +
                            "prices_max=" + QueryTerms.get(1) + "&bathrooms_max=2"
                          + "&furnishedType=1";
        return UserQuery;
    }
    
    public String URLFactory(List QueryTerms, String Site){
        String FormattedRequest = "";
        
        switch (Site){
            case "RightMove":
                 FormattedRequest = this.ToRightMoveURL(QueryTerms);
                 break;
            case "OpenRent":
                 FormattedRequest = this.ToOpenRentURL(QueryTerms);
                 break;
            case "OnTheMarket":
                 FormattedRequest = this.ToOnTheMarketURL(QueryTerms);
                 break;
        }
        return FormattedRequest;
    }
}
