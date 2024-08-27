package com.pivotree.appzone.controller;

import com.pivotree.appzone.entity.Customer;
import com.pivotree.appzone.entity.User;
import com.pivotree.appzone.enums.UserType;
import com.pivotree.appzone.intemplate.UserInTemplate;
import com.pivotree.appzone.intemplate.UsernameInput;
import com.pivotree.appzone.outtemplate.AuthenticationResponse;
import com.pivotree.appzone.outtemplate.UserOutput;
import com.pivotree.appzone.service.AuthenticationService;
import com.pivotree.appzone.service.CustomerService;
import com.pivotree.appzone.service.UserService;
import com.pivotree.appzone.appconfig.ResponseMessage;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AuthenticationService authenticationService;
    /**
     * This method is used to authenticate a user using their username and password.
     *
     * @param loginInput the username and password of the user
     * @return a response indicating whether the login was successful or not
     */

    @PostMapping("/login")
    private ResponseEntity<Object> userAuth(@RequestBody UserInTemplate loginInput){
        AuthenticationResponse validUser = authenticationService.authenticate(loginInput.getUsername(), loginInput.getPassword());
        return new ResponseEntity<>(new com.pivotree.appzone.exception.ResponseMessage(validUser), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/forgotpassword/{email}")
    private ResponseEntity<Object> resetPassword(@PathVariable String email){
        boolean check = userService.resetPassword(email);

        if(check) {
            return ResponseEntity.status(HttpStatus.OK).body("Email has been sent");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Unable to send email");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers(
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userService.findAll(pageable);
        List<User> users = userPage.getContent();
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

}
