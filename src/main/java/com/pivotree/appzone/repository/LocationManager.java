package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationManager {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private LocationRepository locationRepository;
    public void save(Location location){
        entityManager.persist(location);
    }
    public void createLocation(Location location)
    {
        entityManager.persist(location);
    }

    public Location findLocationByReference(String locationReference) {
        return locationRepository.findByLocationName(locationReference);
    }
}
