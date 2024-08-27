package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CustomerManager {
    @Autowired
    CustomerRepository customerRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Customer customer) {
        entityManager.persist(customer);
    }

    public Customer findByCustomerID(Long customerID) {
        return customerRepository.findByCustomerID(customerID);
    }


    public Customer getCustomerByUsername(String username) {
        return customerRepository.getCustomerByUsername(username);
    }
    public Customer findByid(Long id) {
        return customerRepository.findByid(id);
    }

}
