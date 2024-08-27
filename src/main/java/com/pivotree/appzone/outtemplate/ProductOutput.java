package com.pivotree.appzone.outtemplate;

import com.pivotree.appzone.entity.Category;
import com.pivotree.appzone.entity.Dimension;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductOutput {

    private Integer productId;
    private String productReference;
    private String description;
    private Long price;
    private String brand;
    private Date manDate;
    private Date expDate;
    //private Category category;
    private List<Category> category;  //(shravya)
    private Dimension dimension;

}
