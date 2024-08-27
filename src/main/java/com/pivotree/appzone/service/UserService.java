package com.pivotree.appzone.service;

import com.pivotree.appzone.entity.Customer;
import com.pivotree.appzone.entity.User;
import com.pivotree.appzone.outtemplate.UserOutput;
import com.pivotree.appzone.repository.UserManager;
import com.pivotree.appzone.repository.UserRepository;
import jakarta.transaction.Transactional;
import okhttp3.*;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.passay.AllowedCharacterRule.ERROR_CODE;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCrypt;

    @Autowired
    private UserManager userManager;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Authenticates a user using their username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return true if the username and password match, false otherwise
     */
    @Transactional(rollbackOn = Exception.class, value = Transactional.TxType.REQUIRED)
    public Customer authenticate(String username, String password) {
        User user = getUserByUsername(username);
        String dbPassword = user.getPassword();

        if(bCrypt.matches(password,dbPassword)) {
            Customer customer=customerService.getCustomerByUsername(username);
            return customer;
        }
        return null;
    }
    /**
     * This method resets the password for the user with the given email.
     * If the user does not exist, the method returns false.
     * If the user exists, a new password is generated and emailed to the user.
     * The new password is also updated in the database for the user.
     * @param email the email of the user
     * @return true if the password was reset, false if the user does not exist
     * @throws IOException if there is an error sending the email
     */

    @Transactional(rollbackOn = Exception.class, value = Transactional.TxType.REQUIRED)
    public boolean resetPassword(String email) {

        User user = userManager.findUserByEmail(email);

        if(Objects.isNull(user)){
            return false;
        }else{
            String newPassword = generatePass();

            String cryptPass = bCrypt.encode(newPassword);

            try {
                mailer(email,newPassword);
                user.setPassword(cryptPass);
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * This method generates a secure password using the Passay library.
     * The password is composed of a combination of lowercase letters, uppercase letters, digits, and special characters.
     * The length of the password is 10 characters.
     * @return a secure password
     */

    public String generatePass(){
        PasswordGenerator passGen = new PasswordGenerator();

        CharacterData lowerChar = EnglishCharacterData.LowerCase;
        CharacterRule lowerCharRule = new CharacterRule(lowerChar,2);

        CharacterData upperChar = EnglishCharacterData.UpperCase;
        CharacterRule upperCharRule = new CharacterRule(upperChar,2);

        CharacterData digits = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digits,2);

        CharacterData characters = new CharacterData() {
            @Override
            public String getErrorCode() {
                return ERROR_CODE;
            }

            @Override
            public String getCharacters() {
                return "@#!$&*%";
            }
        };
        CharacterRule charRule = new CharacterRule(characters,1);

        return passGen.generatePassword(10,lowerCharRule,upperCharRule,digitRule,charRule);
    }

    /**
     * This method sends an email to the user with the given email address containing the new password.
     * The email is sent using the Mailtrap API.
     * @param email the email address of the user
     * @param pass the new password for the user
     * @throws IOException if there is an error sending the email
     */
    @Async
    public void mailer(String email, String pass) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"from\":{" +
                "\"email\":\"mailtrap@demomailtrap.com\"," +
                "\"name\":\"Mailtrap Test\"" +
                "},\"" +
                "to\":[{\"" +
                "email\":\""+ email +"\"" +
                "}]," +
                "\"subject\":\"Your new password\"," +
                "\"text\":\"Here is your new password: "+ pass +"\"," +
                "\"category\":\"Integration Test\"}");
        Request request = new Request.Builder()
                .url("https://send.api.mailtrap.io/api/send")
                .method("POST", body)
                .addHeader("Authorization", "Bearer 897bbc22901a4f246b6905d129c97435")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

    /**
     * This method retrieves a user from the database based on the given username.
     * If the user does not exist, a RuntimeException is thrown.
     * @param username the username of the user
     * @return the user with the given username
     */
    public User getUserByUsername(String username){
        // retrieve the user from the database
        Optional<User> userOptional = userManager.findUser(username);

        if(userOptional.isEmpty()){
            throw new RuntimeException("User does not exist");
        }

        User user = userOptional.get();

        return user;
    }

    public List<UserOutput> getAllUsers() {
        List<User>users=userRepository.findAll();
        return convertUserToUserOutput(users);
    }

    private List<UserOutput> convertUserToUserOutput(List<User> users) {
        List<UserOutput>userOutputs=new ArrayList<>();
        for(User user:users){
            UserOutput userOutput=new UserOutput(user.getUsername(),user.getEmail(),user.getUserType());
            userOutputs.add(userOutput);
        }
        return userOutputs;
    }

    public Page<User> getAllUsers(Pageable pageable) {
       return userRepository.findAll(pageable);
           }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}

