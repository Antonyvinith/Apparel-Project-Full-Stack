package com.pivotree.appzone.intemplate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryInput {
    @NotBlank
    private String locationRef;
    @NotBlank
    private String productRef;
    @NotNull
    private Double quantity;

    @Override
    public String toString() {
        return "InventoryInput{" +
                "productRef='" + productRef + '\'' +
                ", locationRef='" + locationRef + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
