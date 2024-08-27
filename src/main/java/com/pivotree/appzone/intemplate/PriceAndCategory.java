package com.pivotree.appzone.intemplate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PriceAndCategory {
    PriceInput priceInput;
    CategorySearch categorySearch;
    String key;
}
