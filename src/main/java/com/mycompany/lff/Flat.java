/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.lff;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 *
 * @author teddy
 */
    
@Entity
@Table(name = "flats")
public class Flat{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "postcode")
    private String PostCode;

    @Column(name = "pcm")
    private int PCM;

    @Column(name = "rooms")
    private int Rooms;

    @Column(name = "furnished")
    private boolean Furnished;

    @Column(name = "floorarea")
    private String FloorArea;

    @Column(name = "agentnumber")
    private String AgentNumber;

    @Column(name = "availablefrom")
    private String AvailableFrom;
    
    @Column(name = "origin")
    private String Origin;

    
    
public Flat(){}

public Flat(String postcode,int pcm, int rooms, boolean furnished, 
        String floorarea, String agentnumber,String availablefrom, String origin){

    this.PostCode = postcode;
    this.PCM = pcm;
    this.Rooms = rooms;
    this.Furnished = furnished;
    this.FloorArea = floorarea;
    this.AgentNumber = agentnumber;
    this.AvailableFrom = availablefrom;
    this.Origin = origin;

    }
    public long GetID(){
        return this.id;
    }
    public int  GetPCM(){
        return this.PCM;
    }
    public void SetPostCode(String code){
        this.PostCode = code;
    }
    public void SetPCM (int pcm){
        this.PCM = pcm;
    }
    public void SetRooms (int rooms){
        this.Rooms = rooms;
    }
    public void SetFurnished (String furnished){
        if("Furnished".equals(furnished)){
            this.Furnished = true;
        }
        else{
            this.Furnished = false;
        }       
    }
    public void SetFloorArea(String area){
        this.FloorArea = area;
    }
    public void SetAgentNumber(String number){
        this.AgentNumber = number;
    }
    public void SetAvailableFrom(String date){
        this.AvailableFrom = date;
    }
    public void SetOrigin(String origin){
        this.Origin = origin;
    }
    public String GetAgentNumber(){
        return this.AgentNumber;
    }
    public String GetPostCode(){
        return this.PostCode;
    }
    public String GetFloorArea(){
        return this.FloorArea;
    }
    public String GetAvailableFrom(){
        return this.AvailableFrom;
    }
    public int GetRooms(){
        return this.Rooms;
    }
    public String GetOrigin(){
        return this.Origin;
    }
    public List ResultDetails(){
        
        List<Object> Results = new ArrayList<>();
                
        Results.add(this.AgentNumber);
        Results.add(this.AvailableFrom);
        Results.add(this.PCM);
        Results.add(this.Rooms);
        Results.add(this.PostCode);
        Results.add(this.Origin);
        return Results;
    }

}
