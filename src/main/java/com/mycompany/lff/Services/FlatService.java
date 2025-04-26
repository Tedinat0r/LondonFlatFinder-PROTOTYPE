
package com.mycompany.lff.Services;
import com.mycompany.lff.Flat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class FlatService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Flat getFlat(Long id){
        return entityManager.find(Flat.class, id);
    }

    @Transactional
    public void saveFlat(Flat flat){
        entityManager.persist(flat);
        System.out.println("SAVED");
    }
    @Transactional
    public void deleteFlat(Long id){
        Flat flat = entityManager.find(Flat.class, id);
        if(flat != null){
            entityManager.remove(flat);
        }

    }
    @Transactional
    public void updateFlat(Flat flat){
        entityManager.merge(flat);
    }

    private List GetTargetDetails(Flat flat){
        List<Object> FlatDetails = new ArrayList();
        FlatDetails.add(flat.GetPostCode());
        FlatDetails.add(flat.GetFloorArea());
        FlatDetails.add(flat.GetRooms());
        FlatDetails.add(flat.GetPCM());
        FlatDetails.add(flat.GetID());
        return FlatDetails;
    }

    @Transactional
    protected void FilterCheapest(Flat flat) {

        Query query = entityManager.createQuery("from Flat flat"
                        + " where flat.PostCode = (:post)"
                        + " and flat.FloorArea = (:fa)"
                        + " and flat.Rooms = (:rn)"
                        + " and flat.PCM < (:rent)"
                , Flat.class);

        List<String> QueryParams = new ArrayList(Arrays.asList("post", "fa",
                "rn", "rent"));

        List<Object> ParamsValues = GetTargetDetails(flat);

        for (int i = 0; i < QueryParams.size(); i++) {
            query.setParameter(QueryParams.get(i), ParamsValues.get(i));
        }

        List<Flat> Candidates = query.getResultList();
        Candidates.sort(Comparator.comparingInt(Flat::GetPCM));

        if (!Candidates.isEmpty()) {
            for (int i = 1; i < Candidates.size(); i++) {
                deleteFlat(Candidates.get(i).GetID());
                System.out.println(Candidates.size() + "<- size of candidates");
            }

        }
    }


    @Transactional
    public List<List<Object>> Aggregate() throws IOException {
        List<Long> fetchAll = entityManager.createQuery("from Flat flat select flat.id", long.class).getResultList();
        List<List<Object>> Results = new ArrayList<>();
        for(long flat: fetchAll){
            System.out.println("got a flat");
            this.FilterCheapest(getFlat(flat));
        }
        for(long flat: fetchAll){
            Results.add(getFlat(flat).ResultDetails());
        }
        for(List v: Results){
            for(Object o: v){
                System.out.println("wawa");
                System.out.println(o);
            }
        }
        return Results;
    }

}
