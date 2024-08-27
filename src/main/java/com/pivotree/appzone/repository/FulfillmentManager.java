package com.pivotree.appzone.repository;


import com.pivotree.appzone.entity.FulFillment;
import com.pivotree.appzone.intemplate.FulfillmentStatusUpdate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class FulfillmentManager {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    FullFillmentRepository fullFillmentRepository;

    public void save(FulFillment fulFillment){
        entityManager.persist(fulFillment);
    }

    public Optional<FulFillment> findById(FulfillmentStatusUpdate fulfillmentStatusUpdate) {
        Optional<FulFillment> fulFillment = fullFillmentRepository.findById(fulfillmentStatusUpdate.getId());
        return fulFillment;
    }
}
