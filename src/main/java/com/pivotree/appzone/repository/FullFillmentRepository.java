package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.FulFillment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FullFillmentRepository extends JpaRepository<FulFillment,Integer> {
}
