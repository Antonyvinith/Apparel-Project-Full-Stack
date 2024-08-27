package com.pivotree.appzone.entity;


import com.pivotree.appzone.enums.LocationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class for locations
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "location")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Location extends BaseEntity {

    /**
     * The primary key of the location
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * A unique reference code for the location
     */
    @Column(name = "location_reference", unique = true)
    private String locationRef;

    /**
     * The type of location
     */
    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    /**
     * The name of the location
     */
    @Column(name = "location_name")
    private String locationName;
    @OneToOne
    private Address locationAddress;

    /**
     * Constructor for the Location entity
     *
     * @param locationRef  the unique reference code for the location
     * @param locationType the type of location
     * @param locationName the name of the location
     */
    public Location(String locationRef, LocationType locationType, String locationName, Address locationAddress) {
        this.locationRef = locationRef;
        this.locationType = locationType;
        this.locationName = locationName;
        this.locationAddress = locationAddress;
    }
}
