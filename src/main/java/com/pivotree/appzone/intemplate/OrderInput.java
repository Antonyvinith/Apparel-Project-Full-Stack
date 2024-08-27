package com.pivotree.appzone.intemplate;

import com.pivotree.appzone.entity.Address;
import com.pivotree.appzone.entity.Customer;
import com.pivotree.appzone.entity.OrderLine;
import com.pivotree.appzone.enums.DeliveryType;
import com.pivotree.appzone.enums.OrderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderInput {
    @NotBlank
    @NotNull
    private String orderReference;
    @NotBlank
    @NotNull
    private Address shippingAddress;
    @NotBlank
    @NotNull
    private Address billingAddress;
    @NotBlank
    @NotNull
    private OrderType orderType;
    private DeliveryType deliveryType;
    @NotBlank
    @NotNull
    private Customer customer;
    @NotBlank
    @NotNull
    private List<OrderLine> orderLines;
}
