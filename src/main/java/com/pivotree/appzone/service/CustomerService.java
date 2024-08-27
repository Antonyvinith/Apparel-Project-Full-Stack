package com.pivotree.appzone.service;

import com.pivotree.appzone.entity.Customer;
import com.pivotree.appzone.entity.User;
import com.pivotree.appzone.intemplate.CustomerInput;
import com.pivotree.appzone.intemplate.UpdateCustomerRequest;
import com.pivotree.appzone.repository.CustomerRepository;
import com.pivotree.appzone.repository.CustomerManager;
import com.pivotree.appzone.repository.UserManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CustomerService {

    @Autowired
    CustomerManager customerManager;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder bCrypt;

    @Autowired
    UserManager userManager;

    @Value("${upload.dir}")
    private String uploadDir;

    public Customer findByid(Long id) {
        return customerManager.findByid(id);
    }
    public Customer getCustomerById(Long id) {
        return customerManager.findByid(id);
    }

    /**
     * Adds a new customer to the system.
     *
     * @param apiInput the customer information
     * @return the newly created customer
     */
    @Transactional(rollbackOn = Exception.class, value = Transactional.TxType.REQUIRED)
    public Customer addCustomer(CustomerInput apiInput) {

        Optional<User> user = userManager.findUser(apiInput.getUserInput().getUsername());

        if(!user.isEmpty()) {
            throw new RuntimeException("Username already exist");
        }else {

            String password = bCrypt.encode(apiInput.getUserInput().getPassword());



            User user1 = new User(apiInput.getUserInput().getUsername(), password,
                    apiInput.getUserInput().getType(), apiInput.getEmail());

            Customer customer = new Customer(apiInput.getFirstName(),apiInput.getLastName(),apiInput.getEmail(), apiInput.getPhone(), apiInput.getDateOfBirth(),user1);

            userManager.save(user1);

            customerManager.save(customer);

            return customer;
        }
    }

    /**
     * Returns a customer based on their customer ID.
     *
     * @param customerID the ID of the customer
     * @return the customer with the matching ID, or null if no customer with the specified ID exists
     */
    public Customer findByCustomerID(Long customerID) {
        return customerManager.findByCustomerID(customerID);
    }
    /**
     * Returns a customer based on their fullName.
     *
     * @param fullName the fullName of the customer
     * @return the customer with the matching fullName, or null if no customer with the specified fullName exists*/
    public String[] getNames(String fullName) {

        return fullName.split(" ");
    }

    public Customer getCustomerByUsername(String username) {
        return customerManager.getCustomerByUsername(username);
    }

    public List<Customer> getAllCustomersForAdmin() {
        return customerRepository.findAll();
    }

    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer updateCustomerByUsername(UpdateCustomerRequest updateRequest) {
        Optional<Customer> optionalCustomer = customerRepository.getOptionalByUsername(updateRequest.getUsername());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            // Update the customer details
            if (updateRequest.getFirstName() != null) {
                customer.setFirstName(updateRequest.getFirstName());
            }
            if (updateRequest.getLastName() != null) {
                customer.setLastName(updateRequest.getLastName());
            }
            if (updateRequest.getEmail() != null) {
                customer.setEmail(updateRequest.getEmail());
            }
            if (updateRequest.getPhone() != null) {
                customer.setPhone(updateRequest.getPhone());
            }
            // Save the updated customer
            return customerRepository.save(customer);
        } else {
            return null; // Customer not found
        }
    }



    // Directory where uploaded images will be stored
//    private static final String UPLOAD_DIR = "\"C:\\Users\\alson.dsouza\\Desktop\\FinalBack\\ProfilePictures\"";

//    @Transactional(rollbackOn = Exception.class)
//    public String uploadProfileImage(String email, MultipartFile imageFile) throws IOException {
//        // Check if the image file is empty
//        if (imageFile.isEmpty()) {
//            throw new IllegalArgumentException("Image file is empty");
//        }
//
//        // Generate a unique filename for the image
//        String filename = UUID.randomUUID().toString() + ".jpg";
//
//        // Path to the directory where the image will be saved
//        Path uploadPath = Paths.get(uploadDir);
//
//        // Create the directory if it doesn't exist
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        // Save the image file to the upload directory
//        Path filePath = uploadPath.resolve(filename);
//        Files.copy(imageFile.getInputStream(), filePath);
//
//        // Construct the URL of the uploaded image
//        String imageUrl = uploadDir + "/" + filename;
//
//        // Retrieve the customer from the repository
//        Customer customer = customerRepository.findByEmail(email);
//
//        // Update the customer's profile picture URL
//        if (customer != null) {
//            customer.setImageUrl(imageUrl);
//            customerRepository.save(customer);
//        } else {
//            throw new IllegalArgumentException("Customer not found");
//        }
//
//        return imageUrl;
//
//    }
//
//    @Transactional(rollbackOn = Exception.class)
//    public String updateProfilePicture(String email, MultipartFile imageFile) throws IOException {
//        // Check if the image file is empty
//        if (imageFile.isEmpty()) {
//            throw new IllegalArgumentException("Image file is empty");
//        }
//
//        // Generate a unique filename for the image
//        String filename = UUID.randomUUID().toString() + ".jpg";
//
//        // Path to the directory where the image will be saved
//        Path uploadPath = Paths.get(uploadDir);
//
//        // Create the directory if it doesn't exist
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        // Save the image file to the upload directory
//        Path filePath = uploadPath.resolve(filename);
//        Files.copy(imageFile.getInputStream(), filePath);
//
//        // Construct the URL of the uploaded image
//        String imageUrl =  uploadDir;
//        //"http://your-domain.com/" + UPLOAD_DIR + "/" + filename+email;
//
//        // Retrieve the user from the repository
//        Customer customer = customerRepository.findByEmail(email);
//
//        // Update the user's profile picture URL
//        if (customer != null) {
//            customer.setImageUrl(imageUrl);
//            customerRepository.save(customer);
//        } else {
//            throw new IllegalArgumentException("User not found");
//        }
//
//        return imageUrl;
//    }
//
//    public String getProfileImageUrl(String email) {
//        // Retrieve the customer from the repository based on the email
//        Customer customer = customerRepository.findByEmail(email);
//
//        // If customer exists, return the profile image URL
//        if (customer != null) {
//            return customer.getImageUrl();
//        } else {
//            return null; // Customer not found
//        }
//    }
//}

    @Transactional(rollbackOn = Exception.class)
    public String uploadProfileImage(String email, MultipartFile imageFile,String picName) throws IOException {
        // Check if the image file is empty
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file is empty");
        }

        // Generate a unique filename for the image
        String filename = email + ".jpg";

        // Path to the directory where the image will be saved
        Path uploadPath = Paths.get(uploadDir);

        // Create the directory if it doesn't exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save the image file to the upload directory
        Path filePath = uploadPath.resolve(filename);

        Files.deleteIfExists(filePath);
        Files.copy(imageFile.getInputStream(), filePath);


        // Construct the URL of the uploaded image
        String imageUrl = uploadDir + filename;

        // Retrieve the customer from the repository
        Customer customer = customerRepository.findByEmail(email);

        // Update the customer's profile picture URL
        if (customer != null) {
            customer.setImageUrl(imageUrl);
            customer.setImageName(picName);
            customerRepository.save(customer);
        } else {
            throw new IllegalArgumentException("Customer not found");
        }

        return imageUrl;

    }


    public String getProfileImageUrl(String email) {
        // Retrieve the customer from the repository based on the email
        Customer customer = customerRepository.findByEmail(email);

        // If customer exists, return the profile image URL
        if (customer != null) {
            return customer.getImageUrl();
        } else {
            return null; // Customer not found
        }
    }

}