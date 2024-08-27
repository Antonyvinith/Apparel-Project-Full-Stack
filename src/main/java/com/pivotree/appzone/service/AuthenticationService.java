package com.pivotree.appzone.service;

import com.pivotree.appzone.entity.Customer;
import com.pivotree.appzone.entity.User;
import com.pivotree.appzone.intemplate.CustomerInput;
import com.pivotree.appzone.outtemplate.AuthenticationResponse;
import com.pivotree.appzone.repository.CustomerManager;
import com.pivotree.appzone.repository.UserManager;
import com.pivotree.appzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    UserManager userManager;

    @Autowired
    CustomerManager customerManager;

    @Autowired
    CustomerService customerService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    @Transactional
    public AuthenticationResponse register(CustomerInput apiInput) {
        Optional<User> user = userManager.findUser(apiInput.getUserInput().getUsername());

        if (!user.isEmpty()) {
            throw new RuntimeException("Username already exist");
        } else {
            String password = passwordEncoder.encode(apiInput.getUserInput().getPassword());


            User user1 = new User(apiInput.getUserInput().getUsername(), password,
                    apiInput.getUserInput().getType(), apiInput.getEmail());

            Customer customer = new Customer(apiInput.getFirstName(), apiInput.getLastName(), apiInput.getEmail(), apiInput.getPhone(), apiInput.getDateOfBirth(), user1);
            System.out.println(customer.toString());
//            customer.setImageName(apiInput.getImageName);
            userManager.save(user1);

            customerManager.save(customer);

            String token = jwtService.generateToken(user1);
            Customer customer1=customerService.getCustomerByUsername(apiInput.getUserInput().getUsername());

            return new AuthenticationResponse(token,customer1);
        }
    }
    @Transactional
    public AuthenticationResponse authenticate(String userName,String password){
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        userName,password
//                )
//        );



        User     user=userRepository.findUser(userName).orElseThrow();
        String dbPassword = user.getPassword();
        if(passwordEncoder.matches(password,dbPassword)) {
            Customer customer=customerService.getCustomerByUsername(userName);

            String token = jwtService.generateToken(user);

            return new AuthenticationResponse(token,customer);

        }
        return null;
    }
}
