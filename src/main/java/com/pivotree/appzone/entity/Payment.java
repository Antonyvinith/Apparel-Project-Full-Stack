package com.pivotree.appzone.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pivotree.appzone.enums.PaymentMode;
import com.pivotree.appzone.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    private PaymentMode paymentMode;

    @Column(name = "payment_value")
    private Double paymentValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    public Payment(PaymentMode paymentMode, Double paymentValue, PaymentStatus status) {
        this.paymentMode = paymentMode;
        this.paymentValue = paymentValue;
        this.status = status;
    }
}