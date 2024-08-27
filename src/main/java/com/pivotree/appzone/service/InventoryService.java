package com.pivotree.appzone.service;

import com.pivotree.appzone.entity.Inventory;
import com.pivotree.appzone.entity.InventoryQuantity;
import com.pivotree.appzone.entity.Location;
import com.pivotree.appzone.entity.Product;
import com.pivotree.appzone.enums.StatusType;
import com.pivotree.appzone.enums.Type;
import com.pivotree.appzone.intemplate.InventoryInput;
import com.pivotree.appzone.intemplate.InventoryValidation;
import com.pivotree.appzone.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@AllArgsConstructor
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private InventoryManager inventoryManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryQuantityRepository inventoryQuantityRepository;

    /**
     * Returns a list of inventory objects filtered by location reference and product reference.
     *
     * @param locRefList a list of location references
     * @param productRefList a list of product references
     * @return a list of inventory objects
     */
    public List<Inventory> getListInventoryByLocationAndProductRefs(List<String> locRefList, List<String> productRefList) {
        return inventoryManager.getListInventoryByLocationAndProductRefs(locRefList, productRefList);
    }

    /**
     * Updates a list of inventory objects in the database.
     *
     * @param invToUpdateList a list of inventory objects to update
     */
    public void bulkUpdateInventory(List<Inventory> invToUpdateList) {
        inventoryManager.saveAll(invToUpdateList);
    }

    public Page<Inventory> getAllInventory(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

//    public List<Inventory> getAllInventory() {
//        return inventoryRepository.findAll();
//    }


    /**
     * A custom exception class to handle the InsufficientCapacityException.
     */
    public static class InsufficientCapacityException extends RuntimeException {
        public InsufficientCapacityException(String message) {
            super(message);
        }
    }
    /**
     * Creates a new inventory record.
     *
     * @param inventoryData a list of inventory data objects
     * @return a list of accepted inventory data objects
     */
    @Transactional
    public List<Inventory> create(InventoryValidation inventoryData) {
        List<Inventory>acceptedList=new ArrayList<>();
        List<InventoryInput> rejected = new ArrayList<>();
        List<InventoryInput> input = inventoryData.getInventoryData();

        if (Boolean.TRUE.equals(inventoryData.getValidateData())) {
            for (InventoryInput inputVal : input) {
                Inventory inv = inventoryManager.findInventory(inputVal.getLocationRef(), inputVal.getProductRef());
                if (Objects.isNull(inv)) {
                    Optional<Location> location = Optional.ofNullable(locationRepository.findByLocationName(inputVal.getLocationRef()));
                    Optional<Product> product = Optional.ofNullable(productRepository.findByProductReference(inputVal.getProductRef()));
                    if (product.isEmpty() || location.isEmpty() || inputVal.getQuantity() <= 0) {
                        rejected.add(inputVal);
                    } else {
                        Inventory newInventory = new Inventory(inputVal.getLocationRef(), inputVal.getProductRef(), inputVal.getQuantity());
                        acceptedList.add(newInventory);
                        inventoryRepository.save(newInventory);
                        InventoryQuantity inventoryQuantity = new InventoryQuantity(Type.valueOf("ON_HAND"), StatusType.valueOf("ACTIVE"), newInventory, newInventory.getQuantity());
                        inventoryQuantityRepository.save(inventoryQuantity);
                    }
                } else {
                    try {
                        Double newQuantity = inputVal.getQuantity() + inv.getQuantity();
                        inv.setQuantity(newQuantity);
                        acceptedList.add(inv);
                        inventoryRepository.save(inv);
                        InventoryQuantity inventoryQuantity = new InventoryQuantity(Type.valueOf("ON_HAND"), StatusType.valueOf("ACTIVE"), inv, inv.getQuantity());
                        inventoryQuantityRepository.save(inventoryQuantity);
                    } catch (Exception e) {
                        rejected.add(inputVal);
                    }
                }
            }
        } else {
            for (InventoryInput inputVal : input) {
                Inventory inv = inventoryManager.findInventory(inputVal.getLocationRef(), inputVal.getProductRef());
                if (Objects.isNull(inv)) {
                    Inventory newInventory = new Inventory(inputVal.getLocationRef(), inputVal.getProductRef(), inputVal.getQuantity());
                    acceptedList.add(newInventory);
                    inventoryRepository.save(newInventory);
                    InventoryQuantity inventoryQuantity = new InventoryQuantity(Type.valueOf("ON_HAND"), StatusType.valueOf("ACTIVE"), newInventory, newInventory.getQuantity());
                    inventoryQuantityRepository.save(inventoryQuantity);
                } else {
                    try {
                        Double newQuantity = inputVal.getQuantity() + inv.getQuantity();
                        inv.setQuantity(newQuantity);
                        acceptedList.add(inv);
                        inventoryRepository.save(inv);
                        InventoryQuantity inventoryQuantity = new InventoryQuantity(Type.valueOf("ON_HAND"), StatusType.valueOf("ACTIVE"), inv, inv.getQuantity());
                        inventoryQuantityRepository.save(inventoryQuantity);
                    } catch (Exception e) {
                        rejected.add(inputVal);
                    }
                }

            }
        }
        return acceptedList;
    }

}
