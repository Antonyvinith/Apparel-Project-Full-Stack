package com.pivotree.appzone.entity;

import com.pivotree.appzone.enums.FulfillmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FulFillment {

    /**
     * The unique identifier of the FulFillment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fulfillment_id")
    private Integer fulfillmentId;

    /**
     * The location reference of the FulFillment
     */
    @Column(name = "location_reference")
    private String locationReference;

    /**
     * The list of FulFillmentLines associated with the FulFillment
     */
    @OneToMany
    private List<FulfillmentLine> fulfillmentLines;
    @Enumerated(EnumType.STRING)
    private FulfillmentStatus status;
    @ManyToOne
    private Address fromAddress;
    @ManyToOne
    private Address toAddress;

}
