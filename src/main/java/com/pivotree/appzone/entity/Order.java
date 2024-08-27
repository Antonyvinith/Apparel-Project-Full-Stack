package com.pivotree.appzone.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pivotree.appzone.enums.DeliveryType;
import com.pivotree.appzone.enums.OrderType;
import com.pivotree.appzone.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * This class represents an order in the system.
 *
 * @author Tabnine
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_order")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Order extends BaseEntity {

    /**
     * The unique identifier of the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The reference number of the order.
     */
    @Column(name = "order_reference", unique = true)
    private String orderReference;

    /**
     * The total amount of the order.
     */
    @Column(name = "order_total", columnDefinition = "numeric(10,2)")
    private Double orderTotal;

    /**
     * The shipping address of the order.
     */
    @ManyToOne
    private Address shippingAddress;

    /**
     * The billing address of the order.
     */
    @ManyToOne
    private Address billingAddress;

    /**
     * The status of the order.
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * The type of the order.
     */
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    /**
     * The delivery type of the order.
     */
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    /**
     * The customer associated with the order.
     */
    @ManyToOne
    @JoinColumn(name = "customerId")
    @JsonBackReference
    private Customer customer;

    /**
     * The fulfillments associated with the order.
     */
    @OneToMany
    private List<FulFillment> fulfillments;

    /**
     * The order lines associated with the order.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;

    /**
     * Creates a new order.
     *
     * @param orderReference  The reference number of the order.
     * @param orderType       The type of the order.
     * @param orderLines      The order lines of the order.
     * @param shippingAddress The shipping address of the order.
     * @param billingAddress  The billing address of the order.
     * @param customer        The customer associated with the order.
     * @param deliveryType    The delivery type of the order.
     */
    public Order(String orderReference, OrderType orderType, List<OrderLine> orderLines, Address shippingAddress,
                 Address billingAddress, Customer customer, DeliveryType deliveryType) {
        this.orderReference = orderReference;
        this.orderType = orderType;
        this.orderLines = orderLines;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.customer = customer;
        this.deliveryType = deliveryType;
    }

}