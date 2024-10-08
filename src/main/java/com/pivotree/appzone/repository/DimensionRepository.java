package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Dimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimensionRepository extends JpaRepository<Dimension, Integer> {
}
