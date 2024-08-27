package com.pivotree.appzone.intemplate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryInput {
    @NotBlank
    @NotNull
    private String categoryName;
    @NotBlank
    @NotNull
    private String description;
}
