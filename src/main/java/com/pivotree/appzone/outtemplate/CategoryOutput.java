package com.pivotree.appzone.outtemplate;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryOutput{
    private Integer categoryId;
    private String categoryName;
    private String description;

    public CategoryOutput(Integer categoryId, String categoryName, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }
}
