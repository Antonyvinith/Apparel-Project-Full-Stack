package com.pivotree.appzone.entity;

import com.pivotree.appzone.intemplate.AddressInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class for Address
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "line1")
    private String line1;

    @Column(name = "line2")
    private String line2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "pinCode")
    private String pinCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;


    public Address(AddressInput addressInput) {
        this.addressType=addressInput.getAddressType();
        this.line1=addressInput.getLine1();
        this.line2=addressInput.getLine2();
        this.city=addressInput.getCity();
        this.state=addressInput.getState();
        this.country=addressInput.getCountry();
        this.pinCode=addressInput.getPinCode();
    }
}