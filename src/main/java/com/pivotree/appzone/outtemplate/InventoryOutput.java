package com.pivotree.appzone.outtemplate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InventoryOutput {
    private Integer inventoryID;

    private String locationReference;
    private String productReference;
    private Double quantity;

    public InventoryOutput(Integer inventoryID, String locationReference, String productReference, Double quantity) {
        this.inventoryID = inventoryID;
        this.locationReference = locationReference;
        this.productReference = productReference;
        this.quantity = quantity;
    }
}
