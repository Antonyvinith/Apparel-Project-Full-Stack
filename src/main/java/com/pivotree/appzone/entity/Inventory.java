package com.pivotree.appzone.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory",uniqueConstraints = {@UniqueConstraint(columnNames = {"location_reference","product_reference"})})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Inventory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "location_reference")
    private String locationReference;
    @Column(name = "product_reference")
    private String productReference;
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Double quantity;

    public Inventory(String locationReference, String productReference, Double quantity) {
        this.locationReference = locationReference;
        this.productReference = productReference;
        this.quantity = quantity;
    }
}
