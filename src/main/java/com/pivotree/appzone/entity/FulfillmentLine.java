package com.pivotree.appzone.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class for the FullFillmentLine table in the database
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fulfillment_line")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class FulfillmentLine extends BaseEntity {

    /**
     * The primary key of the FullFillmentLine table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The product reference of the item being fulfilled
     */
    @Column(name = "product_reference")
    private String productReference;

    /**
     * The quantity requested for fulfillment
     */
    @Column(name = "requested_quantity")
    private Double requestedQuantity;

    /**
     * The quantity that has been fulfilled
     */
    @Column(name = "fulfilled_quantity")
    private Double fulfilledQuantity;

    /**
     * The quantity that has been rejected
     */
    @Column(name = "rejected_quantity")
    private Double rejectedQuantity;

    /**
     * The FulFillment that this line belongs to
     */
    @ManyToOne
    private FulFillment fulFillment;

    /**
     * The default constructor for the FullFillmentLine entity
     *
     * @param productRef   the product reference of the item being fulfilled
     * @param reqQuantity  the quantity requested for fulfillment
     * @param reqQuantity1 the quantity that has been fulfilled
     * @param v            the quantity that has been rejected
     */
    public FulfillmentLine(String productRef, Double reqQuantity, Double reqQuantity1, Double v) {
        productReference = productRef;
        requestedQuantity = reqQuantity;
        fulfilledQuantity = reqQuantity1;
        rejectedQuantity = v;
    }

}
