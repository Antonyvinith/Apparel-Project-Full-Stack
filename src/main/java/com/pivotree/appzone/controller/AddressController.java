package com.pivotree.appzone.controller;

import com.pivotree.appzone.appconfig.ResponseMessage;
import com.pivotree.appzone.entity.Address;
import com.pivotree.appzone.entity.User;
import com.pivotree.appzone.intemplate.AddressInput;
import com.pivotree.appzone.intemplate.UpdateAddressInput;
import com.pivotree.appzone.repository.AddressRepository;
import com.pivotree.appzone.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/addresses")

public class AddressController {
    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;




    @PostMapping
    public ResponseEntity<Object> addAddress(@RequestBody AddressInput addressInput) {
        // Extract username from the request body
        String username = addressInput.getUsername();

        // Check if username is provided
        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required in the request body");
        }

        // Call the service method to add the address
        Address address=addressService.addAddress(addressInput);

        return new ResponseEntity<>( new ResponseMessage(address), HttpStatusCode.valueOf(200));
    }




    @PutMapping
    public ResponseEntity<String> updateAddress(@RequestBody UpdateAddressInput addressInput) {
        String username = addressInput.getUsername();
        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required in the request body");
        }

        Long addressId = addressInput.getId();
        if (addressId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Address ID is required in the request body");
        }

        // Check if the provided username matches the username associated with the address
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address with ID " + addressId + " not found");
        }

        Address address = optionalAddress.get();
        if (!address.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this address");
        }

        addressService.updateAddress(addressInput, username);
        return ResponseEntity.status(HttpStatus.OK).body("Address updated successfully");
    }



    @GetMapping("/{username}")
    public ResponseEntity<Object> getAllAddressesByUsername(@PathVariable String username) {
//        String username = user.getUsername();
        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required in the request body");
        }
        return ResponseEntity.ok(addressService.getAllAddressesByUsername(username));
    }


}
