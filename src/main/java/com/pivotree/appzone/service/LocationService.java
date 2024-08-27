package com.pivotree.appzone.service;

import com.pivotree.appzone.intemplate.LocationInput;
import com.pivotree.appzone.repository.LocationManager;
import com.pivotree.appzone.entity.Location;
import com.pivotree.appzone.repository.LocationRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2

@Service
public class LocationService {
    @Autowired
    private LocationManager locationManager;

    @Autowired
    private AddressService addressService;
    @Autowired
    private LocationRepository locationRepository;
    /**
     * Creates a list of locations based on the given location inputs.
     *
     * @param locations the list of location inputs
     * @return the list of created locations
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public List<Location> createLocation(List<LocationInput> locations) {
        List<Location>locationList=new ArrayList<>();
        try {
            for (LocationInput loc : locations) {
                Location locations1 = new Location(loc.getLocationReference(), loc.getLocationType(), loc.getLocationName(),loc.getLocationAddress());
                locationList.add(locations1);
                addressService.save(loc.getLocationAddress());
                locationManager.createLocation(locations1);
            }
        }catch (Exception e){
            log.info("Error while creating Location :",e );
        }
        return locationList;
    }

    public Location findLocationByReference(String locationReference) {
        return locationManager.findLocationByReference(locationReference);
    }

//    public List<Location> getAllLocation() {
//        return locationRepository.findAll();
//    }

    public Page<Location> getAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }
}
