package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UserManager {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Searches for a user with the given username in the database and returns the result as an {@link Optional}.
     *
     * @param username the username of the user to be searched for
     * @return an {@link Optional} containing the user with the given username, if found, or an empty {@link Optional} if no user with the given username was found
     */
    public Optional<User> findUser(String username) {

        return userRepository.findUser(username);

    }

    /**
     * Saves the given user to the database.
     *
     * @param user1 the user to be saved
     */
    public void save(User user1) {

        entityManager.persist(user1);

    }

    /**
     * Returns the user with the given email address, if it exists in the database.
     *
     * @param email the email address of the user
     * @return the user with the given email address, or null if no user with that email address exists
     */
    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email);

    }
}
