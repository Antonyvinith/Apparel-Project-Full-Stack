package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    @Query("SELECT i FROM Inventory i WHERE i.productReference IN :productRefList AND i.locationReference IN :locRefList")
    List<Inventory> getListInventoryByLocationAndProductRefs(List<String> locRefList, List<String> productRefList);
    @Query("SELECT i FROM Inventory i WHERE i.productReference IN :prodReferences")
    List<Inventory> getAllInventoriesByProdRefList(Set<String> prodReferences);

    @Query("select i from Inventory i where i.productReference=?2 and i.locationReference=?1")
    Inventory findInventory(String locationReference,String productReference);

}
