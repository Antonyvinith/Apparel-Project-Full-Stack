package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @Query("SELECT c FROM Customer c WHERE c.id = ?1")
    Customer findByCustomerID(Long id);

    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    Customer findByEmail(String email);

    @Query("DELETE FROM Customer c WHERE c.email =?1")
    void deleteByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.user.username = :username ")
    Customer getCustomerByUsername(String username);

    @Query("SELECT c FROM Customer c WHERE c.user.username = :username ")
    Optional<Customer> getOptionalByUsername(String username);

    @Query("SELECT c FROM Customer c WHERE c.id = ?1")
    Customer findByid(Long id);
}
