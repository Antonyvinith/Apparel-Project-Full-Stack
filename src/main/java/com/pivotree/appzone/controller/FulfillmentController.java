package com.pivotree.appzone.controller;

import com.pivotree.appzone.appconfig.ResponseMessage;
import com.pivotree.appzone.entity.FulFillment;
import com.pivotree.appzone.intemplate.FulfillmentStatusUpdate;
import com.pivotree.appzone.service.FulfillmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/fulfillment")
public class FulfillmentController {
    @Autowired
    FulfillmentService fulFillmentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assign")
    public ResponseEntity<Object> assignFulfillment(@Valid @RequestBody FulfillmentStatusUpdate fulfillmentStatusUpdate) {
        Optional<FulFillment> fulFillment = fulFillmentService.updateToAssign(fulfillmentStatusUpdate);
        if (!fulFillment.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage(fulFillment.get()), HttpStatusCode.valueOf(200));
        } else {
            return  new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/acknowledge")
    public ResponseEntity<Object> acknowledgeFulfillment(@Valid @RequestBody FulfillmentStatusUpdate fulfillmentStatusUpdate){
        Optional<FulFillment> fulFillment = fulFillmentService.updateToAcknowledge(fulfillmentStatusUpdate);
        return new ResponseEntity<>(new ResponseMessage(fulFillment.get()), HttpStatusCode.valueOf(200));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/pick")
    public ResponseEntity<Object> pickFulfillment(@Valid @RequestBody FulfillmentStatusUpdate fulfillmentStatusUpdate){
        Optional<FulFillment> fulFillment = fulFillmentService.updateToPick(fulfillmentStatusUpdate);
        return new ResponseEntity<>(new ResponseMessage(fulFillment.get()), HttpStatusCode.valueOf(200));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/pack")
    public ResponseEntity<Object> packFulfillment(@Valid @RequestBody FulfillmentStatusUpdate fulfillmentStatusUpdate){
        Optional<FulFillment> fulFillment = fulFillmentService.updateToPack(fulfillmentStatusUpdate);
        return new ResponseEntity<>(new ResponseMessage(fulFillment.get()), HttpStatusCode.valueOf(200));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ship")
    public ResponseEntity<Object> shipFulfillment(@Valid @RequestBody FulfillmentStatusUpdate fulfillmentStatusUpdate){
        Optional<FulFillment> fulFillment = fulFillmentService.updateToShip(fulfillmentStatusUpdate);
        if(fulFillment.isEmpty()) {
            return null;
        }
        return new ResponseEntity<>(new ResponseMessage(fulFillment.get()), HttpStatusCode.valueOf(200));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/deliver")
    public ResponseEntity<Object> deliverFulfillment(@Valid @RequestBody FulfillmentStatusUpdate fulfillmentStatusUpdate){
        Optional<FulFillment> fulFillment = fulFillmentService.updateToDeliver(fulfillmentStatusUpdate);
        return new ResponseEntity<>(new ResponseMessage(fulFillment.get()), HttpStatusCode.valueOf(200));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/readytocollect")
    public ResponseEntity<Object> readytocollectFulfillment(@Valid @RequestBody FulfillmentStatusUpdate fulfillmentStatusUpdate){
        Optional<FulFillment> fulFillment = fulFillmentService.updateToReadyToCollect(fulfillmentStatusUpdate);
        return new ResponseEntity<>(new ResponseMessage(fulFillment.get()), HttpStatusCode.valueOf(200));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/collect")
    public ResponseEntity<Object> collectFulfillment(@Valid @RequestBody FulfillmentStatusUpdate fulfillmentStatusUpdate){
        Optional<FulFillment> fulFillment = fulFillmentService.updateToCollect(fulfillmentStatusUpdate);
        return new ResponseEntity<>(new ResponseMessage(fulFillment.get()), HttpStatusCode.valueOf(200));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/refund")
    public ResponseEntity<Object> refundFulfillment(@Valid @RequestBody FulfillmentStatusUpdate fulfillmentStatusUpdate){
        Optional<FulFillment> fulFillment = fulFillmentService.updateToRefund(fulfillmentStatusUpdate);
        return new ResponseEntity<>(new ResponseMessage(fulFillment.get()), HttpStatusCode.valueOf(200));
    }
}
