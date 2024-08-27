package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
    @Query("SELECT l FROM Location l WHERE l.locationRef =:locationName")
    Location findByLocationName(String locationName);
}
