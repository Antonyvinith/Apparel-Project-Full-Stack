package com.pivotree.appzone.service;

import com.pivotree.appzone.entity.FulfillmentLine;
import com.pivotree.appzone.entity.Inventory;
import com.pivotree.appzone.entity.InventoryQuantity;
import com.pivotree.appzone.enums.StatusType;
import com.pivotree.appzone.enums.Type;
import com.pivotree.appzone.repository.InventoryQuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryQuantityService {

    @Autowired
    InventoryQuantityRepository inventoryQuantityRepository;

    public void createInventoryQuantity(FulfillmentLine fullFillmentLine, List<Inventory> inventories, int i, Double reqQuantity) {

        Optional<InventoryQuantity> olderInventoryQuantity = Optional.ofNullable(inventoryQuantityRepository.findByInventoryAndStatus(inventories.get(i)));

        if(!olderInventoryQuantity.isEmpty()) {
            olderInventoryQuantity.get().setStatusType(StatusType.INACTIVE);
            inventoryQuantityRepository.save(olderInventoryQuantity.get());
        }

        InventoryQuantity inventoryQuantity = new InventoryQuantity(Math.toIntExact(fullFillmentLine.getId()), Type.valueOf("COMMITTED"), StatusType.valueOf("ACTIVE"), inventories.get(i), reqQuantity);
        inventoryQuantityRepository.save(inventoryQuantity);

        inventoryQuantity.setStatusType(StatusType.valueOf("INACTIVE"));
        InventoryQuantity newInventoryQuantity = new InventoryQuantity(inventoryQuantity.getLineId(), Type.valueOf("SALE"), StatusType.valueOf("ACTIVE"), inventoryQuantity.getInventory(), inventoryQuantity.getQuantity());
        inventoryQuantityRepository.save(newInventoryQuantity);

        Optional<InventoryQuantity> oldInventoryQuantity = Optional.ofNullable(inventoryQuantityRepository.findByTypeInventoryId(newInventoryQuantity.getInventory()));
        Double quantity = oldInventoryQuantity.get().getQuantity();
        oldInventoryQuantity.get().setQuantity(quantity - reqQuantity);
    }

}
