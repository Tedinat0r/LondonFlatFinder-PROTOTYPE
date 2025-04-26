/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lff.Services.ScraperServices;
import java.io.IOException;
import java.util.*;

import com.mycompany.lff.Flat;
import com.mycompany.lff.Services.FlatService;
import com.mycompany.lff.Services.ScraperServices.ScraperUtils.FlatValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RightMoveScraper extends SuperScraper {


    @Autowired
    FlatValidator Validator;

    public RightMoveScraper(FlatValidator validator) throws IOException {
        super(validator);

    }

    @Override
    public void ScrapeFlats(String QueryURL) throws IOException{

        Document doc = Jsoup.connect(QueryURL)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                               "Chrome/107.0.0.0 Safari/537.36").get();

        List page = doc
                    .select("a[href*=\"/properties/\"]");

        Map<String, Object> RightMoveFlats = new HashMap<>();
        List<String> SelectorTerms = new ArrayList();
        SelectorTerms.add("[itemprop=\"streetAddress\"]");
        SelectorTerms.add("span:contains(pcm)");
        SelectorTerms.add("dd:contains(Furnished)");
        SelectorTerms.add("article:nth-of-type(0n+2) dd");
        SelectorTerms.add("main > article:nth-of-type(1n+0) a[href]:eq(1)");

        List Containers = doc.select("div[class*=\"PropertyCard_propertyCardContainerWrapper\"]");
        Map<String, String> AgentNumbers = new HashMap<>();
        for( var container : Containers){
            int trimBegin = container.toString().indexOf("tel:") + 4;
            String agentNumber = container.toString().substring(trimBegin, trimBegin + 13);
            int linkBegin = container.toString().indexOf("properties/");
            String linksub = container.toString().substring(linkBegin);
            int linkEnd = linksub.indexOf('"');
            String link = linksub.substring(0, linkEnd);
            AgentNumbers.put(link.replace(" ", ""), agentNumber);

        }
      
        SelectorTerms.add("dd > span:has(p)");
        List FlatUrls = this.GenURLS(page, '/', '"');
        Set<String> flatURLs = Set.copyOf(FlatUrls);
        
        for (var suffix : flatURLs){

            String RootURL = "https://rightmove.co.uk";
            RootURL = RootURL + "/" + suffix;

            Document flatpage = Jsoup.connect(RootURL).get();
            Elements flat = flatpage.select("main:first-child").select("[data-skip-to-content] > div:first-child");
            Flat NewFlat = new Flat();
            NewFlat.SetOrigin("RightMove");

            if(AgentNumbers.containsKey(suffix)){
                NewFlat.SetAgentNumber(AgentNumbers.get(suffix));
            }
            
            try{
                NewFlat.SetPostCode(flat.select(SelectorTerms.get(0)).text());

                NewFlat.SetFloorArea(flat.select(SelectorTerms.get(5)).select("p:nth-of-type(0n+2)").text());
                NewFlat.SetAvailableFrom(flat.select(SelectorTerms.get(3)).get(0).text());
            try{
                char[] rawPCM = flat.select(SelectorTerms.get(1)).toString().toCharArray();
                int[] pcmTrimpoints = this.ExtractHREF(rawPCM, '£', 'p');
                String pcm = new String(rawPCM, pcmTrimpoints[0] + 1, pcmTrimpoints[1] - 1)
                        .replace(",", "")
                        .replace(" ", "");

                NewFlat.SetPCM(Integer.parseInt(pcm));
                if (!flat.select(SelectorTerms.get(5)).select("p:eq(0):matches(^\\d+$)").isEmpty()) {
                    NewFlat.SetRooms(Integer.parseInt(flat.select(SelectorTerms.get(5)).select("p:eq(0):matches(^\\d+$)").get(0).text()));
                }
                else{
                    NewFlat.SetRooms(0);
                }
            }
            catch(Exception e){
                //System.err.println(e.getMessage());
                //System.err.println(e.getLocalizedMessage());

            }
            NewFlat.SetFurnished(flat.select(SelectorTerms.get(2)).text());

            ValidateAndSave(NewFlat, "RightMove");


            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        System.out.println("done");
    }
}
