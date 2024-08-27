package com.pivotree.appzone.intemplate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryValidation {
    @NotNull
    private List<InventoryInput> inventoryData;
    @NotNull
    private Boolean validateData;
}
