package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    @Query("SELECT A FROM Address A WHERE A.user.username=:username")
    List<Address> findAllByUsername(String username);

    Optional<Address> findById(Long id);

}
