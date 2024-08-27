package com.pivotree.appzone.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Customer extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@(pivotree|gmail)+\\.com$")
    private String email;
    @Column(name = "phone", unique = true)
    private String phone;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "imageUrl")
    private String imageUrl;

    private String imageName= "rachana.jpg";
    @OneToOne
    private User user;


    public Customer(String firstName, String lastName, String email, String phone, Date dateOfBirth, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.user = user;
    }

//    public Customer(String firstName, String lastName, String email, String phone, Date dateOfBirth, User user1) {
//        super();
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.phone = phone;
//        this.dateOfBirth = dateOfBirth;
//        this.user = user1;
//    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}



