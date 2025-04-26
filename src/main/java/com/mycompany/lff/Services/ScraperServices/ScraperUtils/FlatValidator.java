package com.mycompany.lff.Services.ScraperServices.ScraperUtils;

import com.mycompany.lff.Flat;
import com.mycompany.lff.Services.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FlatValidator {

    private final FlatSanitizerVisitor visitor = new FlatSanitizerVisitor();

    private final FlatService FlatService;

    @Autowired
    public FlatValidator(FlatService flatService) throws IOException {
        this.FlatService = flatService;
    }

    public String ValidateAddress(Flat flat){

        String Address = flat.GetPostCode();
        if(Address == null || Address.isEmpty()){
            System.out.println("Address is null or empty");
            return "Invalid Address";
        }
        if(Address.split(" ").length > 2 || Address.split("\\|").length < 2){
            return visitor.SanitizeAddress(flat);
        }
        else{
            System.out.println("Address isn't valid");
            return "Invalid Address";
        }
    }

    public String ValidateFloorArea(Flat flat){
        if(!flat.GetFloorArea().isEmpty()) {
            if (flat.GetFloorArea().split(" ").length != 3) {
                return visitor.SanitizeFloorArea(flat);
            }
            else{
                return "Valid Floor Area";
            }
        }
        System.out.println("Floor area is empty");
        return "Invalid Floor Area";
    }

    public void Validated(Flat flat){;
        FlatService.saveFlat(flat);
        }
}


