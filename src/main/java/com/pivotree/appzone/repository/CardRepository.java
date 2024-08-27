package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findById(Long id);


    @Query("SELECT C FROM Card C WHERE C.user.username=:username")
    List<Card> findAllByUserUsername(String username);


}

