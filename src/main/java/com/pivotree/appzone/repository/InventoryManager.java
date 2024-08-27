package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Inventory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@AllArgsConstructor
public class InventoryManager {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private LocationRepository locationRepo;
    @Autowired
    private ProductRepository productRepo;

    public void save(Inventory inventory) {
        entityManager.persist(inventory);
    }

    public List<Inventory> getListInventoryByLocationAndProductRefs(List<String> locRefList, List<String> productRefList) {
        return inventoryRepository.getListInventoryByLocationAndProductRefs(locRefList, productRefList);
    }
    public Inventory findInventory(String locationReference,String productReference){
        return inventoryRepository.findInventory(locationReference,productReference);
    }
    public void saveAll(List<Inventory> invToUpdateList) {
        inventoryRepository.saveAll(invToUpdateList);
    }
}
