package com.pivotree.appzone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class for OrderLine
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_line")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class OrderLine extends BaseEntity {

    /**
     * The primary key of the OrderLine
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The product reference of the OrderLine
     */
    @Column(name = "product_reference")
    private String productReference;

    /**
     * The quantity of the OrderLine
     */
    @Column(name = "quantity")
    private Double quantity;

    /**
     * The line total of the OrderLine
     */
    @Column(name = "line_total")
    private Double lineTotal;

    /**
     * The unit price of the OrderLine
     */
    @Column(name = "unit_price")
    private Double unitPrice;

    /**
     * The order that the OrderLine belongs to
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * The constructor of the OrderLine
     *
     * @param productReference The product reference of the OrderLine
     * @param quantity         The quantity of the OrderLine
     * @param lineTotal        The line total of the OrderLine
     * @param unitPrice        The unit price of the OrderLine
     */
    public OrderLine(String productReference, Double quantity, Double lineTotal, Double unitPrice) {
        this.productReference = productReference;
        this.quantity = quantity;
        this.lineTotal = lineTotal;
        this.unitPrice = unitPrice;
    }
}