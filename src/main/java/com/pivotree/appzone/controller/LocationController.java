package com.pivotree.appzone.controller;

import com.pivotree.appzone.entity.Customer;
import com.pivotree.appzone.entity.Inventory;
import com.pivotree.appzone.enums.UserType;
import com.pivotree.appzone.exception.ResponseMessage;
import com.pivotree.appzone.intemplate.LocationInput;
import com.pivotree.appzone.intemplate.UsernameInput;
import com.pivotree.appzone.outtemplate.LocationOutput;
import com.pivotree.appzone.service.CustomerService;
import com.pivotree.appzone.service.LocationService;
import com.pivotree.appzone.entity.Location;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;
    @Autowired
    private CustomerService customerService;

    /**
     * Creates a new location based on the given input data.
     *
     * @param locations a list of location data
     * @return a list of created locations
     */

    @PostMapping
    ResponseEntity<Object> createLocation(@Valid @RequestBody List<LocationInput> locations) {
        List<Location> locationList = locationService.createLocation(locations);

        return new ResponseEntity<>(new ResponseMessage(locationList), HttpStatusCode.valueOf(201));
    }



    @GetMapping("/allLocations")
    public ResponseEntity<Page<Location>> getAllLocations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Location> locationPage = locationService.getAllLocations(pageable);
        return new ResponseEntity<>(locationPage, HttpStatus.OK);
    }

}

