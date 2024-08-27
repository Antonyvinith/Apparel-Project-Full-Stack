package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.StatusLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusLoggerRepository extends JpaRepository<StatusLogger, Integer> {
}
