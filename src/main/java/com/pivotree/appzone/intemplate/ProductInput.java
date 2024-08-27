package com.pivotree.appzone.intemplate;

import com.pivotree.appzone.entity.Dimension;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductInput {

    @NotBlank(message = "no blank from productRef")
    private String productReference;
    @NotBlank(message = "no blank from description")
    private String description;
    @NotNull(message = "no blank from price")
    private Long price;
    @NotBlank(message = "no blank from brand")
    private String brand;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date manufactureDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;
    @NotNull(message = "no blank from image_urls")
    private List<String> imageUrls;
    @NotNull(message = "no blank from dims")
    private Dimension dimensions;
    @Nullable
    //private String categoryName;
    private List<CategoryInput> categories;  //(shravya)
}
