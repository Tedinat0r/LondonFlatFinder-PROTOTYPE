package com.mycompany.lff.Services.ScraperServices.ScraperUtils;

import com.mycompany.lff.Flat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlatSanitizerVisitor {

    private boolean isPostcode(String string) {
        Pattern pattern = Pattern.compile(".*[a-zA-z][\\d]");
        Matcher matcher = pattern.matcher(string);

        return matcher.find();
    }

    public String SanitizeAddress(Flat flat) {
        String Address = flat.GetPostCode();
        String[] AddressPartitions;
        StringBuilder SanitizedString = new StringBuilder();
        int findLondon = Address.indexOf("London");
        String semiSanitized1;
        String semiSanitized2;
        String Postcode = "";
        String Street = "";

        if (findLondon != -1) {
            try {
                semiSanitized1 = Address.substring(findLondon + 7);
            }
            catch (Exception e) {
                semiSanitized1 = Address.substring(findLondon + 6);;
            }
            semiSanitized2 = Address.substring(0, findLondon);
            Address = semiSanitized2.trim() + semiSanitized1.trim();
        }

        if(Address.contains("|")){
            AddressPartitions = Address.split("\\|");
        }else{
            AddressPartitions = Address.split(",");
        }
        if(AddressPartitions[0].contains("ouse") || AddressPartitions[0].contains("hare") ||
                AddressPartitions[0].contains("oom") || AddressPartitions[0].contains("-") || AddressPartitions[0]
                .contains("ed")) {
            String[] NewPartitions = new String[AddressPartitions.length - 1];
            for (int i = 0; i < AddressPartitions.length - 1; i++) {
                NewPartitions[i] = AddressPartitions[i + 1].trim();
            }
            AddressPartitions = NewPartitions;
        }
        for (int i = 0; i < AddressPartitions.length; i++) {
            if(AddressPartitions[i].split(" ").length == 2 && i == 0) {
                Street = AddressPartitions[i].trim();
            }

            if(isPostcode(AddressPartitions[i])){
                Postcode = AddressPartitions[i].trim();
            }
        }

        if(!Postcode.isEmpty()){
            if(!Street.isEmpty()) {
                SanitizedString.append(Street);
                SanitizedString.append(", ");
                SanitizedString.append(Postcode);
            }
            else{
                SanitizedString.append(Postcode);
            }
            return SanitizedString.toString();
        }
        return Address;
    }

    public String SanitizeFloorArea(Flat flat) {

        int i = 0;
        String floorArea = flat.GetFloorArea();
        StringBuilder SanitizedString = new StringBuilder();
        String digits = floorArea.substring(i, i + 2);
        while(!digits.matches(".*\\d{2}.*")){
            if(i + 1 == floorArea.length()){
                break;
            }
            digits = floorArea.substring(i, i + 2);
            i++;
        }
        if(i + 1 < floorArea.length()){
            SanitizedString.append(digits);
            SanitizedString.append(" ");
            SanitizedString.append("sq ");
            SanitizedString.append("m");
        }
        if(!SanitizedString.isEmpty()){
            return SanitizedString.toString();
        }
        return floorArea;

    }

}
