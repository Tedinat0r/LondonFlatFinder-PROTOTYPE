/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lff.Services.ScraperServices;

import com.mycompany.lff.Flat;
import com.mycompany.lff.Services.ScraperServices.ScraperUtils.FlatValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SuperScraper {


    private final FlatValidator Validator;

    @Autowired
    SuperScraper(FlatValidator validator) throws IOException {
        this.Validator = validator;
    }




    public int[] ExtractHREF(char[] x, char Term1, char Term2) {


        int[] TrimPoints = {-1, -1};
        int i = 0;
        boolean matched = false;

        while (matched == false) {
            if (x[i] == Term2 && TrimPoints[0] != -1) {
                TrimPoints[1] = i;
                matched = true;
            }

            if (x[i] == Term1 && TrimPoints[0] == -1) {
                TrimPoints[0] = i;
            }
            i++;
        }
        TrimPoints[1] -= TrimPoints[0];
        return TrimPoints;
    }

    public boolean Verify(String Ext, String Type) {
        char first = '.';
        char last = '.';
        char[] Content = Ext.toCharArray();
        int end = Content.length;
        if ("Extension".equals(Type)) {
            first = '/';
            last = 'T';
        }
        if (Content[0] == first && Content[end - 1] == last) {
            return true;
        } else {
            return false;
        }
    }

    public List GenURLS(List page, char char1, char char2) {
        List<String> FlatUrls = new ArrayList<>();
        for (var x : page) {
            char[] Injectable = x.toString().toCharArray();

            int[] TrimPoints = this.ExtractHREF(Injectable, char1, char2);
            String NewURL = new String(Injectable, TrimPoints[0] + 1,
                    TrimPoints[1] - 1);

            FlatUrls.add(NewURL);

        }
        return FlatUrls;
    }
    @Transactional
    public void ValidateAndSave(Flat flat, String origin){
        String Address = this.Validator.ValidateAddress(flat);
        String FloorArea = this.Validator.ValidateFloorArea(flat);

        if(!origin.equals("OpenRent") && !origin.equals("RightMove")){
            if(!Address.equals("Invalid Address") && !FloorArea.equals("Invalid Floor Area")){
                if(!FloorArea.equals("Valid Floor Area")){
                    flat.SetFloorArea(FloorArea);
                }
                flat.SetPostCode(Address);
                this.Validator.Validated(flat);
            }
        }else{
            if(!Address.equals("Invalid Address") && origin.equals("OpenRent")){
                flat.SetFloorArea(FloorArea);
                this.Validator.Validated(flat);
            }else{
                this.Validator.Validated(flat);

            }
        }
    }


        public void ScrapeFlats (String QueryURL) throws IOException {
        }

    }



