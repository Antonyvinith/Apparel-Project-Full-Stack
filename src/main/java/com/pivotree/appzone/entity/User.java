package com.pivotree.appzone.entity;

import com.pivotree.appzone.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entity class for Users
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_info")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User extends BaseEntity implements UserDetails {

    /**
     * The primary key of the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The username of the user
     */
    @Column(name = "username", unique = true)
    private String username;

    /**
     * The password of the user
     */
    @Column(name = "password")
    private String password;

    /**
     * The type of the user
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    /**
     * The email of the user
     */
    @Column(name = "email")
    private String email;

    /**
     * Constructs a new User with the given parameters
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param type     the type of the user
     * @param email    the email of the user
     */
    public User(String username, String password, UserType type, String email) {
        this.username = username;
        this.password = password;
        this.userType = type;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userType.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
