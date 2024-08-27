package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Inventory;
import com.pivotree.appzone.entity.InventoryQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryQuantityRepository extends JpaRepository<InventoryQuantity, Long> {

    @Query("SELECT i FROM InventoryQuantity i WHERE i.inventory = :inventory AND i.type = ON_HAND")
    InventoryQuantity findByTypeInventoryId(Inventory inventory);

    @Query("SELECT i FROM InventoryQuantity i WHERE i.inventory = :inventory AND i.type = SALE")
    InventoryQuantity findByInventoryAndStatus(Inventory inventory);

//    @Query("SELECT u FROM User u WHERE u.username = ?1")
//    Optional<User> findUser(String username);
}
