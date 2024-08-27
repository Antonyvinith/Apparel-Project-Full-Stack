package com.pivotree.appzone.service;

import com.pivotree.appzone.entity.*;
import com.pivotree.appzone.enums.FulfillmentStatus;
import com.pivotree.appzone.enums.StatusType;
import com.pivotree.appzone.enums.Type;
import com.pivotree.appzone.repository.FullFillmentLinesRepository;
import com.pivotree.appzone.repository.FullFillmentRepository;
import com.pivotree.appzone.repository.InventoryQuantityRepository;
import com.pivotree.appzone.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class OrderWithFulfillment {
    private FullFillmentLinesRepository fullFillmentLinesRepository;
    private FullFillmentRepository fullFillmentRepository;
    private ProductService productService;
    private InventoryRepository inventoryRepository;
    private InventoryService inventoryService;
    private LocationService locationService;

    @Autowired
    private InventoryQuantityService inventoryQuantityService;
    @Autowired
    StatusLoggerService statusLoggerService;
    @Autowired
    private InventoryQuantityRepository inventoryQuantityRepository;
    /**
     * Method to construct availability map.
     * Iterate through inventories and group them by location.
     *
     * @param inventories {@link List} is the list of inventories retrieved.
     * @return {@link Map} a map of available items grouped by location
     */
    private static HashMap<String, HashMap<String, Double>> generateAvailabilityMap(List<Inventory> inventories) {
        HashMap<String, HashMap<String, Double>> availabilityMap = new HashMap<>();

        for (Inventory inventory : inventories) {
            if (!availabilityMap.containsKey(inventory.getLocationReference())) {
                HashMap<String, Double> locAvQtyMap = new HashMap<>();
                locAvQtyMap.put(inventory.getProductReference(), inventory.getQuantity());
                availabilityMap.put(inventory.getLocationReference(), locAvQtyMap);
            } else {
                HashMap<String, Double> locAvQtyMap = availabilityMap.get(inventory.getLocationReference());
                locAvQtyMap.put(inventory.getProductReference(), inventory.getQuantity());
            }
        }
        return availabilityMap;
    }

    /**
     * This method creates an order with fulfillment. It takes an order as input and creates a fulfillment for each location where the products are available.
     *
     * @param order the order object
     * @return the updated order object with fulfillments
     */
    public Order createOrderWithFulfillment(Order order) {
        System.out.println(order.toString());
        Set<String> prodReferences = new HashSet<>();
        HashMap<String, Double> unAllocatedMap = new HashMap<>();
        for (OrderLine orderLine : order.getOrderLines()) {
            prodReferences.add(orderLine.getProductReference());
            unAllocatedMap.put(orderLine.getProductReference(), orderLine.getQuantity());
        }
        for (Map.Entry<String, Double> entry : unAllocatedMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        List<Inventory> inventories = getInventoriesByProductReferences(prodReferences);
        log.info("This is the inventory retrieved for order [{}], is [{}]", order.getId(), inventories);
        HashMap<String, HashMap<String, Double>> availabilityMap = generateAvailabilityMap(inventories);
        log.info("This is the availability map for order [{}], is [{}]", order.getId(), availabilityMap);

        int i = 0;

        List<FulFillment> fulFillmentList = new ArrayList<>();
        for (String location : availabilityMap.keySet()) {
            HashMap<String, Double> skuQtyByLocMap = availabilityMap.get(location);
            List<FulfillmentLine> fullFillmentLineList = new ArrayList<>();
            for (String productRef : skuQtyByLocMap.keySet()) {
                Double reqQuantity = unAllocatedMap.get(productRef);
                if (reqQuantity <= skuQtyByLocMap.get(productRef)) {
                    FulfillmentLine fullFillmentLine = new FulfillmentLine(productRef, reqQuantity, reqQuantity, 0.0);
                    unAllocatedMap.remove(productRef);
                    fullFillmentLinesRepository.save(fullFillmentLine);
                    fullFillmentLineList.add(fullFillmentLine);

                    inventoryQuantityService.createInventoryQuantity(fullFillmentLine, inventories, i, reqQuantity);
//                    newInventoryQuantity.setStatusType(StatusType.valueOf("INACTIVE"));
                    i++;
                } else {
                    Double fulfilledQuantity = reqQuantity - skuQtyByLocMap.get(productRef);
                    FulfillmentLine fullFillmentLine = new FulfillmentLine(productRef, reqQuantity, fulfilledQuantity, 0.0);
                    fullFillmentLinesRepository.save(fullFillmentLine);
                    fullFillmentLineList.add(fullFillmentLine);

                    inventoryQuantityService.createInventoryQuantity(fullFillmentLine, inventories, i, fulfilledQuantity);
//                    newInventoryQuantity.setStatusType(StatusType.valueOf("INACTIVE"));
                    i++;
                }
            }
            FulFillment fulfillment = new FulFillment();
            fulfillment.setFulfillmentLines(fullFillmentLineList);
            fulFillmentList.add(fulfillment);
            fulfillment.setLocationReference(location);
            fulfillment.setStatus(FulfillmentStatus.valueOf("CREATED"));
            Location location1 = locationService.findLocationByReference(fulfillment.getLocationReference());
            fulfillment.setFromAddress(location1.getLocationAddress());
            fulfillment.setToAddress(order.getShippingAddress());
            fullFillmentRepository.save(fulfillment);
            StatusLogger statusLogger = new StatusLogger(null, FulfillmentStatus.CREATED, fulfillment.getFulfillmentId());
            statusLoggerService.save(statusLogger);
            deductInventory(fulfillment);
        }
        log.info("Fulfillment's generate for order [{}] are: [{}]", order.getId(), fulFillmentList);
        if (!unAllocatedMap.isEmpty()) {
            generateSystemRejectedFulfilment(unAllocatedMap);
        }
        order.setFulfillments(fulFillmentList);
        System.out.println(order.toString());
        return order;
    }



    /**
     * This method generates a system rejected fulfillment for the unallocated items in the order.
     *
     * @param unAllocatedMap a map of unallocated items in the order, with the key as the product reference and the value as the quantity
     */

    private void generateSystemRejectedFulfilment(HashMap<String, Double> unAllocatedMap) {
        FulFillment fulFillment = new FulFillment();
        List<FulfillmentLine> rejectedFulfillments = new ArrayList<>();
        for (String entry : unAllocatedMap.keySet()) {
            if (unAllocatedMap.get(entry) > 0) {
                /**
                 * Creates a new FullFillmentLine object with the given product reference, requested quantity, fulfilled quantity, and rejection reason.
                 *
                 * @param productReference the product reference of the item
                 * @param requestedQuantity the requested quantity of the item in the order
                 * @param fulfilledQuantity the fulfilled quantity of the item in the fulfillment
                 */
                FulfillmentLine fullFillmentLine = new FulfillmentLine(entry, unAllocatedMap.get(entry), 0.0, unAllocatedMap.get(entry));
                rejectedFulfillments.add(fullFillmentLine);
                fullFillmentLinesRepository.save(fullFillmentLine);
            }
        }
        fulFillment.setFulfillmentLines(rejectedFulfillments);
        fulFillment.setLocationReference("RejectedLocation");
        fulFillment.setStatus(FulfillmentStatus.valueOf("SYSTEMREJECTED"));
        fullFillmentRepository.save(fulFillment);
    }

    /**
     * This method deducts the inventory for the given fulfillment.
     *
     * @param fulfillment the fulfillment object
     */
    private void deductInventory(FulFillment fulfillment) {
        Map<String, Double> allocateQty = fulfillment.getFulfillmentLines().stream().collect(Collectors.toMap(FulfillmentLine::getProductReference, FulfillmentLine::getRequestedQuantity));
        String locationReference = fulfillment.getLocationReference();
        List<Inventory> invToUpdateList = inventoryService.getListInventoryByLocationAndProductRefs(List.of(locationReference), allocateQty.keySet().stream().toList());
        for (Inventory iterator : invToUpdateList) {

            iterator.setQuantity(iterator.getQuantity() - allocateQty.get(iterator.getProductReference()));
        }
        inventoryService.bulkUpdateInventory(invToUpdateList);
    }

    /**
     * This method retrieves a list of inventories based on the given product references.
     *
     * @param prodReferences the set of product references
     * @return the list of inventories
     */
    private List<Inventory> getInventoriesByProductReferences(Set<String> prodReferences) {
        return inventoryRepository.getAllInventoriesByProdRefList(prodReferences);
    }

}
