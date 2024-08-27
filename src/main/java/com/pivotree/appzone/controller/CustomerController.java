package com.pivotree.appzone.controller;


import com.pivotree.appzone.entity.Customer;
import com.pivotree.appzone.exception.ResponseMessage;
import com.pivotree.appzone.intemplate.CustomerInput;
import com.pivotree.appzone.intemplate.EmailInput;
import com.pivotree.appzone.intemplate.GetCustomerByUsernameRequest;
import com.pivotree.appzone.intemplate.UpdateCustomerRequest;
import com.pivotree.appzone.outtemplate.AuthenticationResponse;
import com.pivotree.appzone.outtemplate.CustomerOutput;
import com.pivotree.appzone.service.AuthenticationService;
import com.pivotree.appzone.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AuthenticationService authenticationService;
    /**
     * Adds a new customer to the system
     *
     * @param customerInput the customer information to add
     * @return the newly created customer
     */
    @PostMapping("/addCustomer")
    public ResponseEntity<Object> addCustomer(@Valid @RequestBody CustomerInput customerInput){
        AuthenticationResponse validUser = authenticationService.register(customerInput);
//        CustomerOuput customerOutput = convertCustomerToCustomerOutput(customer);
        return new ResponseEntity<>(new ResponseMessage(validUser), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/get-by-username")
    public ResponseEntity<Object> getCustomerByUsername(@RequestBody GetCustomerByUsernameRequest request) {

        Customer customer = customerService.getCustomerByUsername(request.getUsername());
        System.out.println(customer.toString());
        if (customer != null) {
            CustomerOutput customerOutput = convertCustomerToCustomerOutput(customer);
            return new ResponseEntity<>(new com.pivotree.appzone.exception.ResponseMessage(customerOutput), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update-by-username")
    public ResponseEntity<Object> updateCustomerByUsername(@Valid @RequestBody UpdateCustomerRequest updateRequest) {
        Customer customer = customerService.updateCustomerByUsername(updateRequest);
        if (customer != null) {
            CustomerOutput customerOutput = convertCustomerToCustomerOutput(customer);
            return new ResponseEntity<>(new com.pivotree.appzone.exception.ResponseMessage(customerOutput), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
    }


//    @PostMapping("/upload-profile-image")
//    public ResponseEntity<Object> uploadProfileImage(
//            @RequestParam("email") String email,
//            @RequestParam("imageFile") MultipartFile imageFile
//    ) {
//        try {
//            String imageUrl = customerService.uploadProfileImage(email, imageFile);
//            return new ResponseEntity<>(imageUrl, HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>("Failed to upload profile image", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//    @PostMapping("/update-profile-picture")
//    public ResponseEntity<Object> updateProfilePicture(
//            @RequestParam("email") String email,
//            @RequestParam("imageFile") MultipartFile imageFile
//    ) {
//        try {
//            // Update profile picture
//            String imageUrl = customerService.updateProfilePicture(email, imageFile);
//            if (imageUrl != null) {
//                return new ResponseEntity<>(new com.pivotree.appzone.exception.ResponseMessage("Profile picture updated successfully"), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Failed to update profile picture", HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        } catch (IOException e) {
//            return new ResponseEntity<>("Failed to update profile picture", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//    @PostMapping("/profile-image")
//    public ResponseEntity<Object> getProfileImage(@RequestBody EmailInput emailInput) {
////    public ResponseEntity<Object> getProfileImage(@RequestBody Email email) {
//
//        // Retrieve the image URL from the database based on the email (Assuming it's stored in the database)
//        String imageUrl = customerService.getProfileImageUrl(emailInput.getEmail());
//        log.info(imageUrl);
//        if (imageUrl == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return new ResponseEntity<>(imageUrl, HttpStatusCode.valueOf(201));
////        try {
////            // Convert the image URL to a Path
//////            Paths paths=Paths.
////            Path imagePath = Paths.get(new URL(imageUrl).toURI());
////
////            // Read the image file as byte array
////            byte[] imageData = Files.readAllBytes(imagePath);
////
////            // Return the image data as a ResponseEntity with appropriate content type
////            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
//////            return new ResponseEntity<>(new ResponseMessage(imagePath), HttpStatus.OK);
////        } catch (IOException | URISyntaxException e) {
////            // Handle any errors and return appropriate response
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
////        }
//    }
//    private CustomerOutput convertCustomerToCustomerOutput(Customer customer) {
//        return new CustomerOutput(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(),customer.getPhone(), customer.getDateOfBirth(),customer.getUser().getUsername());
//    }
//
//
//
//    @GetMapping("/getAll")
//    public ResponseEntity<Object> getAllCustomers(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Customer> customersPage = customerService.getAllCustomers(pageable);
//        return new ResponseEntity<>(customersPage, HttpStatus.OK);
//    }
@PostMapping("/upload-profile-image")
public ResponseEntity<Object> uploadProfileImage(@RequestParam("email") String email, @RequestParam("imageFile") MultipartFile imageFile,@RequestParam("picName") String picName) throws IOException {
    List<String>result=new ArrayList<>();
    result.add(picName);
    String imageUrl = customerService.uploadProfileImage(email, imageFile,picName);
    result.add(imageUrl);
    return new ResponseEntity<>(result, HttpStatus.OK);

}



    @PostMapping("/profile-image")
    public ResponseEntity<Object> getProfileImage(@RequestBody EmailInput email) {
//    public ResponseEntity<Object> getProfileImage(@RequestBody Email email) {

        // Retrieve the image URL from the database based on the email (Assuming it's stored in the database)
        String imageUrl = customerService.getProfileImageUrl(email.getEmail()); // Implement this method in CustomerService

        log.info(imageUrl);
        if (imageUrl == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(imageUrl, HttpStatusCode.valueOf(201));
//        try {
//            // Convert the image URL to a Path
////            Paths paths=Paths.
//            Path imagePath = Paths.get(new URL(imageUrl).toURI());
//
//            // Read the image file as byte array
//            byte[] imageData = Files.readAllBytes(imagePath);
//
//            // Return the image data as a ResponseEntity with appropriate content type
//            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
////            return new ResponseEntity<>(new ResponseMessage(imagePath), HttpStatus.OK);
//        } catch (IOException | URISyntaxException e) {
//            // Handle any errors and return appropriate response
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
    }

    private CustomerOutput convertCustomerToCustomerOutput(Customer customer) {
        return new CustomerOutput(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getDateOfBirth(), customer.getUser().getUsername());
    }

}
