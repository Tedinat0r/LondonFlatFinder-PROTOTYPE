/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lff.Services.ScraperServices;

import java.io.IOException;

import com.mycompany.lff.Flat;
import com.mycompany.lff.Services.FlatService;
import com.mycompany.lff.Services.ScraperServices.ScraperUtils.FlatValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.lang.StringBuilder;
import java.util.Set;

@Service
public class OnTheMarketScraper extends SuperScraper {


    @Autowired
    public OnTheMarketScraper(FlatValidator validator) throws IOException {
        super(validator);
    }

    @Override
    public void ScrapeFlats(String QueryURL) {

        try {
            String room;
            String FloorArea;
            String Availability;
            String Furnished;
            String AgentNumber;

            Document doc = Jsoup.connect(QueryURL).get();

            List page = doc.select("a[href*=\"/details/\"]");

            Set<String> flatURLs = Set.copyOf(this.GenURLS(page,'"' ,'"'));

            int iter = 1;
            for (var k : flatURLs) {
                System.out.println(k);
                if (iter != 7) {
                    iter++;
                    continue;
                } else {
                    String RootURL = "https://www.onthemarket.com";
                    RootURL += k;
                    System.out.println(RootURL);
                    Document flatpage = Jsoup.connect(RootURL).get();
                    if(!flatpage.select("h1[data-test=\"property-title\"]:contains(Parking to rent)").text().isEmpty()){
                        continue;
                    }
                    StringBuilder rooms = new StringBuilder(flatpage.select("div[class*="
                            + "\"block border flex justify"
                            + "-between\"] > div > div:containsOwn(be)").text());
                    if (rooms.length() != 0) {
                         room = new String(rooms.substring(0, 1));
                    }
                    else {
                         room = "0";
                    }
                    char[] PCM = flatpage.select("a[class=\"F79Qjm text-denim mb-0 text-lg "
                            + "font-bold\"]").text().toCharArray();
                    int[] PCMPoints = this.ExtractHREF(PCM, '£', ' ');
                    String pcm = new String(PCM, PCMPoints[0] + 1, PCMPoints[1] - 1)
                            .replace(",", "");

                    char[] fa = flatpage.select("div[class*=\"block border flex justify"
                                    + "-between\"] > div > div:containsOwn(sq m)").text()
                            .toCharArray();
                    if (fa.length != 0) {
                        int[] faPoints = this.ExtractHREF(fa, '/', 'm');
                        FloorArea = new String(fa, faPoints[0] + 2, faPoints[1] - 1);
                    }
                    else {
                        FloorArea = "00 sq m";
                    }

                    List FurnishedAndAvailability = flatpage.select("ul[class=\"list-disc ml"
                            + "-5 mt-5 font-heading text-semibold text-body text-denim\"] > li");
                    try {
                        char[] AvailabilityArr = FurnishedAndAvailability.get(0).toString()
                                .toCharArray();
                        if (AvailabilityArr.length != 0) {
                            try {
                                int[] AvailabilityPoints = this.ExtractHREF(AvailabilityArr, ':', '<');
                                Availability = new String(AvailabilityArr, AvailabilityPoints[0]
                                        + 2, AvailabilityPoints[1] - 2);
                            } catch (IndexOutOfBoundsException e) {
                                Availability = "";
                                System.out.println(e.getMessage());
                            }
                        } else {
                            Availability = "";
                        }

                        char[] FurnishedArr = FurnishedAndAvailability.get(1).toString()
                                .toCharArray();

                        if (FurnishedArr.length != 0) {
                            try {
                                int[] FurnishedPoints = this.ExtractHREF(FurnishedArr, 'F', 'd');
                                Furnished = new String(FurnishedArr, FurnishedPoints[0],
                                        FurnishedPoints[1] + 1);
                            } catch (ArrayIndexOutOfBoundsException e) {
                                Furnished = "";
                            }
                        } else {
                            Furnished = "";
                        }
                        AgentNumber = flatpage.select("div[class=\"h5\"]:contains(Call) + div[role=\"button\"]").text().replace(" ", "");
                        System.out.println(AgentNumber);
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }

                    Flat NewFlat = new Flat();
                    NewFlat.SetOrigin("OnTheMarket");
                    NewFlat.SetAgentNumber(AgentNumber);
                    NewFlat.SetRooms(Integer.parseInt(room));
                    NewFlat.SetPCM(Integer.parseInt(pcm));
                    NewFlat.SetPostCode(flatpage.select("div[class=\"text-slate text-body2"
                                    + " font-normal leading-none font-heading md:leading-normal\"]")
                            .text());
                    NewFlat.SetFloorArea(FloorArea);
                    NewFlat.SetAvailableFrom(Availability);
                    NewFlat.SetFurnished(Furnished);

                    ValidateAndSave(NewFlat, "OnTheMarket");


                }
            }
            System.out.println("done");
        } catch (IOException | NullPointerException  | NumberFormatException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}

