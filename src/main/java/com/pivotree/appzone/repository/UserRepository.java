package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findUser(String username);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
}
