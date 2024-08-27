package com.pivotree.appzone.intemplate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PriceInput {
    private double minPrice;
    private double maxPrice;
}