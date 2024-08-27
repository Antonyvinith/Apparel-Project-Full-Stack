package com.pivotree.appzone.entity;

import com.pivotree.appzone.enums.FulfillmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "status_logger")
public class StatusLogger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Integer statusId;
    @Enumerated(EnumType.STRING)
    private FulfillmentStatus previousStatus;
    @Enumerated(EnumType.STRING)
    private FulfillmentStatus currentStatus;

    private Integer fulfillmentId;

    public StatusLogger(FulfillmentStatus previousStatus, FulfillmentStatus currentStatus, Integer fulfillmentId) {
        this.previousStatus = previousStatus;
        this.currentStatus  = currentStatus;
        this.fulfillmentId = fulfillmentId;
    }
}
