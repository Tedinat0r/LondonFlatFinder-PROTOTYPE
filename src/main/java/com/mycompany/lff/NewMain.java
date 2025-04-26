/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.lff;
import com.mycompany.lff.Services.ScraperServices.RightMoveScraper;
import com.mycompany.lff.Services.ScraperServices.ScraperUtils.SearchQueryGen;

import java.util.*;
import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @author teddy
 */
public class NewMain {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException, ParseException {

        
        /*FlatDAO FlatCRUD = new FlatDAO();
        FlatCRUD.saveFlat(myflat);
        FlatCRUD.saveFlat(thisflat);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<String> QueryOptions = new ArrayList<>();
        QueryOptions.add("from Flat flat where flat.PostCode = 'N16 6TU' and flat.Rooms = 1 and flat.Furnished = True");
        Query<Flat> query = session.createQuery(QueryOptions.get(0), Flat.class);
        List<Flat> results = query.getResultList();*/
        /*Document doc = Jsoup.connect("https://www.rightmove.co.uk/property-to-rent/find.html?searchLocation=SE17&useLocationIdentifier=true&locationIdentifier=OUTCODE%5E2317&radius=0.25&_includeLetAgreed=on").get();
        List page = doc.select("div[class*=\"PropertyCard_propertyCardContainer__\"]").select("a[href*=\"/properties/\"]");
        
        List<String> FlatUrls = new ArrayList<>();
        
        // =====================================================================
        for (var x : page){
            char[] Injectable = x.toString().toCharArray();
            int TrimStart = -1;
            int TrimEnd = -1;
            int i = 0;
            boolean matched = false;
            
            while(matched == false){
                if(Injectable[i] == '"' && TrimStart ==-1){
                    TrimStart = i;    
                }
          
                if (Injectable[i] == '"' && i != TrimStart){
                    TrimEnd = i;
                    matched = true;
                } 
                i++;
            }
            
            TrimEnd -= TrimStart;
            
            String NewURL = new String(Injectable, TrimStart + 1, TrimEnd - 1);
            char[] VerifyURL = NewURL.toCharArray();
            int end = VerifyURL.length;
            char first = '/';
            char last = 'T';
            if(VerifyURL[0] == first && VerifyURL[end - 1] == last){
                FlatUrls.add(NewURL);
            }

        }
        // =====================================================================
        // ** Generating URLs
        
        // ** to save individual flat details
        
  
        Map<String, Object> FlatDetails = new HashMap<>();
        List<String> SelectorTerms = new ArrayList();
        SelectorTerms.add("[itemprop=\"streetAddress\"]");
        SelectorTerms.add("span:contains(pcm)");
        SelectorTerms.add("dd:contains(Furnished)");
        // make sure to add .get(0) (this is available from)
        SelectorTerms.add("article:nth-of-type(0n+2) dd");
        // make sure to add .get(4)
        SelectorTerms.add("main > article:nth-of-type(0n+2) a[href]");
        
        // this returns rooms and floor area
        SelectorTerms.add("dd > span:has(p)");
        
        
        for (String suffix : FlatUrls){
            String RootURL = "https://rightmove.co.uk";
            RootURL += suffix;
            Document flatpage = Jsoup.connect("https://rightmove.co.uk/properties/157113770#/?channel=RES_LET").get();
            Elements flat = flatpage.select("main:first-child").select("[data-skip-to-content] > div:first-child");
            System.out.println(flatpage.select(SelectorTerms.get(4)).get(4).toString());
            break;
            }
        }
    }


*/
        /*List<String> quez = new ArrayList<>();
        quez.add("N16");
        quez.add("1100");
        quez.add("2");
        quez.add("Furnished");

        RightMoveScraper scraper = new RightMoveScraper();
        SearchQueryGen searchQueryGen = new SearchQueryGen();
        scraper.ScrapeFlats(searchQueryGen.URLFactory(quez, "RightMove"));



        }
        
    }   

*/
    }
}