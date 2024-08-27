package com.pivotree.appzone.service;

import com.pivotree.appzone.entity.FulFillment;
import com.pivotree.appzone.entity.FulfillmentLine;
import com.pivotree.appzone.entity.Order;
import com.pivotree.appzone.entity.StatusLogger;
import com.pivotree.appzone.enums.FulfillmentStatus;
import com.pivotree.appzone.enums.OrderType;
import com.pivotree.appzone.intemplate.FulfillmentStatusUpdate;
import com.pivotree.appzone.repository.FulfillmentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FulfillmentService {
    @Autowired
    FulfillmentManager fulfillmentDBManager;
    @Autowired
    StatusLoggerService statusLoggerService;
    @Autowired
    OrderService orderService;

    public Optional<FulFillment> updateToAssign(FulfillmentStatusUpdate fulfillmentStatusUpdate) {
        Optional<FulFillment> fulFillment = fulfillmentDBManager.findById(fulfillmentStatusUpdate);
        FulfillmentStatus retreievedStatus = fulFillment.get().getStatus();
        if (fulFillment.get().getStatus().equals(FulfillmentStatus.CREATED)) {
            fulFillment.get().setStatus(FulfillmentStatus.ASSIGNED);
        }
        fulfillmentDBManager.save(fulFillment.get());
        StatusLogger statusLogger = new StatusLogger(retreievedStatus, FulfillmentStatus.ASSIGNED, fulFillment.get().getFulfillmentId());
        statusLoggerService.save(statusLogger);
        return fulFillment;
    }

    public Optional<FulFillment> updateToAcknowledge(FulfillmentStatusUpdate fulfillmentStatusUpdate) {
        Optional<FulFillment> fulFillment = fulfillmentDBManager.findById(fulfillmentStatusUpdate);
        FulfillmentStatus retreievedStatus = fulFillment.get().getStatus();
        if (fulFillment.get().getStatus().equals(FulfillmentStatus.ASSIGNED)) {
            fulFillment.get().setStatus(FulfillmentStatus.ACKNOWLEDGED);
        }
        fulfillmentDBManager.save(fulFillment.get());
        StatusLogger statusLogger = new StatusLogger(retreievedStatus, FulfillmentStatus.ACKNOWLEDGED, fulFillment.get().getFulfillmentId());
        statusLoggerService.save(statusLogger);
        return fulFillment;
    }

    public Optional<FulFillment> updateToPick(FulfillmentStatusUpdate fulfillmentStatusUpdate) {
        Optional<FulFillment> fulFillment = fulfillmentDBManager.findById(fulfillmentStatusUpdate);
        FulfillmentStatus retreievedStatus = fulFillment.get().getStatus();
        if (fulFillment.get().getStatus().equals(FulfillmentStatus.ACKNOWLEDGED) || fulFillment.get().getStatus().equals(FulfillmentStatus.ASSIGNED)) {
            boolean isFulfilled = true;
            for (FulfillmentLine fullFillmentLine : fulFillment.get().getFulfillmentLines()) {
                if (fullFillmentLine.getFulfilledQuantity() < fullFillmentLine.getRequestedQuantity()) {
                    fulFillment.get().setStatus(FulfillmentStatus.PARTIALLYFULFILLED);
                    isFulfilled = false;
                    break;
                }
            }
            if (isFulfilled) {
                fulFillment.get().setStatus(FulfillmentStatus.FULFILLED);
            }
        }
        fulfillmentDBManager.save(fulFillment.get());
        StatusLogger statusLogger = new StatusLogger(retreievedStatus, fulFillment.get().getStatus(), fulFillment.get().getFulfillmentId());
        statusLoggerService.save(statusLogger);
        return fulFillment;
    }

    public Optional<FulFillment> updateToPack(FulfillmentStatusUpdate fulfillmentStatusUpdate) {
        Optional<FulFillment> fulFillment = fulfillmentDBManager.findById(fulfillmentStatusUpdate);
        FulfillmentStatus retreievedStatus = fulFillment.get().getStatus();
        if (fulFillment.get().getStatus().equals(FulfillmentStatus.PARTIALLYFULFILLED) || fulFillment.get().getStatus().equals(FulfillmentStatus.FULFILLED)) {
            fulFillment.get().setStatus(FulfillmentStatus.PACKED);
        }
        fulfillmentDBManager.save(fulFillment.get());
        StatusLogger statusLogger = new StatusLogger(retreievedStatus, FulfillmentStatus.PACKED, fulFillment.get().getFulfillmentId());
        statusLoggerService.save(statusLogger);
        return fulFillment;
    }

    public Optional<FulFillment> updateToShip(FulfillmentStatusUpdate fulfillmentStatusUpdate) {
        Optional<FulFillment> fulFillment = fulfillmentDBManager.findById(fulfillmentStatusUpdate);
//        Order order = orderService.findOrderByFulfillmentId(fulFillment.get().getFulfillmentId());

//        System.out.println("Ship order: " + order);
//        if(order.getOrderType() == OrderType.valueOf("HD")) {
            FulfillmentStatus retreievedStatus = fulFillment.get().getStatus();
            if (fulFillment.get().getStatus().equals(FulfillmentStatus.PACKED)) {
                fulFillment.get().setStatus(FulfillmentStatus.SHIPPED);
            }
            fulfillmentDBManager.save(fulFillment.get());
            StatusLogger statusLogger = new StatusLogger(retreievedStatus, FulfillmentStatus.SHIPPED, fulFillment.get().getFulfillmentId());
            statusLoggerService.save(statusLogger);
            return fulFillment;
//        }
//        return null;
    }

    public Optional<FulFillment> updateToReadyToCollect(FulfillmentStatusUpdate fulfillmentStatusUpdate) {
        Optional<FulFillment> fulFillment = fulfillmentDBManager.findById(fulfillmentStatusUpdate);
//        Order order = orderService.findOrderByFulfillmentId(fulFillment.get().getFulfillmentId());

//        if(order.getOrderType() == OrderType.valueOf("CC")) {
            FulfillmentStatus retreievedStatus = fulFillment.get().getStatus();
            if (fulFillment.get().getStatus().equals(FulfillmentStatus.PACKED)) {
                fulFillment.get().setStatus(FulfillmentStatus.READYTOCOLLECT);
            }
            fulfillmentDBManager.save(fulFillment.get());
            StatusLogger statusLogger = new StatusLogger(retreievedStatus, FulfillmentStatus.READYTOCOLLECT, fulFillment.get().getFulfillmentId());
            statusLoggerService.save(statusLogger);
            return fulFillment;
//        }
//        return null;
    }

    public Optional<FulFillment> updateToDeliver(FulfillmentStatusUpdate fulfillmentStatusUpdate) {
        Optional<FulFillment> fulFillment = fulfillmentDBManager.findById(fulfillmentStatusUpdate);

        FulfillmentStatus retreievedStatus = fulFillment.get().getStatus();
        if (fulFillment.get().getStatus().equals(FulfillmentStatus.SHIPPED)) {
            fulFillment.get().setStatus(FulfillmentStatus.DELIVERED);
        }
        fulfillmentDBManager.save(fulFillment.get());
        StatusLogger statusLogger = new StatusLogger(retreievedStatus, FulfillmentStatus.DELIVERED, fulFillment.get().getFulfillmentId());
        statusLoggerService.save(statusLogger);
        return fulFillment;
    }

    public Optional<FulFillment> updateToCollect(FulfillmentStatusUpdate fulfillmentStatusUpdate) {
        Optional<FulFillment> fulFillment = fulfillmentDBManager.findById(fulfillmentStatusUpdate);
        FulfillmentStatus retreievedStatus = fulFillment.get().getStatus();
        if (fulFillment.get().getStatus().equals(FulfillmentStatus.READYTOCOLLECT)) {
            fulFillment.get().setStatus(FulfillmentStatus.COLLECTED);
        }
        fulfillmentDBManager.save(fulFillment.get());
        StatusLogger statusLogger = new StatusLogger(retreievedStatus, FulfillmentStatus.COLLECTED, fulFillment.get().getFulfillmentId());
        statusLoggerService.save(statusLogger);
        return fulFillment;
    }

    public Optional<FulFillment> updateToRefund(FulfillmentStatusUpdate fulfillmentStatusUpdate) {
        Optional<FulFillment> fulFillment = fulfillmentDBManager.findById(fulfillmentStatusUpdate);
        FulfillmentStatus retreievedStatus = fulFillment.get().getStatus();
        if (fulFillment.get().getStatus().equals(FulfillmentStatus.SYSTEMREJECTED)) {
            fulFillment.get().setStatus(FulfillmentStatus.REFUNDED);
        }
        fulfillmentDBManager.save(fulFillment.get());
        StatusLogger statusLogger = new StatusLogger(retreievedStatus, FulfillmentStatus.REFUNDED, fulFillment.get().getFulfillmentId());
        statusLoggerService.save(statusLogger);
        return fulFillment;
    }
}