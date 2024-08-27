package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.FulfillmentLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FullFillmentLinesRepository extends JpaRepository<FulfillmentLine,Integer> {
}
