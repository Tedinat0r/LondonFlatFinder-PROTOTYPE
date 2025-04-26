/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lff.Services.ScraperServices;
import java.io.IOException;

import com.mycompany.lff.Flat;
import com.mycompany.lff.Services.FlatService;
import com.mycompany.lff.Services.ScraperServices.ScraperUtils.FlatValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OpenRentScraper extends SuperScraper {


    @Autowired
    public OpenRentScraper (FlatValidator validator) throws IOException{
        super(validator);

    }
    
  
    
    @Override
    public void ScrapeFlats(String QueryURL) throws IOException{

        Document doc = Jsoup.connect(QueryURL).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
            .get();
        List page = doc.select("a[href*=\"property-to-rent\"]");

        Set<String> flatURLs = Set.copyOf(this.GenURLS(page,'"' ,'"'));

        for(var k: flatURLs){

            if(k.equals("/find-property-to-rent-from-private-landlords") 
            || k.equals("btn btn-primary")){
                continue;
            }
            try{
                String RootURL = "https://www.openrent.co.uk";
                RootURL += k;
                Document flatpage = Jsoup.connect(RootURL).get();

                Flat NewFlat = new Flat();
                NewFlat.SetOrigin("OpenRent");
                String Address = flatpage.select("h1[class=\"mb-0\"]").text();
                System.out.println(Address);

                String bedroomarray = flatpage.select("div[class=\"vstack gap-4\"] dt:contains(Bedrooms) + dd").text();
                
                char[] PCMArray = flatpage.select("p:contains(£) ").getFirst().text().toCharArray();
                int[] PCMPoints = this.ExtractHREF(PCMArray, '£', '.');
                String PCM = flatpage.select("p:contains(£) ").getFirst().text().substring(PCMPoints[0] + 1, PCMPoints[1]).replace(",", "");

                NewFlat.SetAgentNumber(RootURL);
                NewFlat.SetFurnished(flatpage.select("tbody > tr > td:contains(Furnishing) + td").text());
                NewFlat.SetRooms(Integer.valueOf(bedroomarray));
                NewFlat.SetPCM(Integer.valueOf(PCM));
                NewFlat.SetAvailableFrom(flatpage.select("tbody > tr > td:contains(Available From) + td").text());

                ValidateAndSave(NewFlat, "OpenRent");
            }catch(Exception StringIndexOutOfBoundsException){

               }
             break;
            }
        System.out.println("done");
        }
    }
    


