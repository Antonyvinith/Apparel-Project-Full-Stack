package com.pivotree.appzone.outtemplate;

import com.pivotree.appzone.entity.Address;
import com.pivotree.appzone.entity.Customer;
import com.pivotree.appzone.entity.FulFillment;
import com.pivotree.appzone.entity.OrderLine;
import com.pivotree.appzone.enums.DeliveryType;
import com.pivotree.appzone.enums.OrderType;
import com.pivotree.appzone.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderOutput {
    private Long orderID;

    private String orderReference;
    private Status status;
    private OrderType orderType;
    private DeliveryType deliveryType;
    private Double orderTotal;
    private Address billingAddress;
    private Address shippingAddress;
    private Customer customer;
    private List<OrderLine> orderLines;
    private List<FulFillment> fulfillments;


    public OrderOutput(Long orderId, String orderReference, Address shippingAddress, Status status,
                       List<OrderLine> orderLines, OrderType orderType, Customer customer, Address billingAddress,
                       List<FulFillment> fulfillments, DeliveryType deliveryType, Double orderTotal) {
        this.orderID = orderId;
        this.orderReference = orderReference;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.orderLines = orderLines;
        this.orderType = orderType;
        this.customer = customer;
        this.billingAddress = billingAddress;
        this.fulfillments = fulfillments;
        this.deliveryType = deliveryType;
        this.orderTotal = orderTotal;
    }
}
