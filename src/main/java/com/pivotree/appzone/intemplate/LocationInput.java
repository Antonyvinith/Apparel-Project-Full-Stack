package com.pivotree.appzone.intemplate;

import com.pivotree.appzone.entity.Address;
import com.pivotree.appzone.enums.LocationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocationInput {
    @NotBlank(message = "no blank from locationRef")
    private String locationReference;
    @NotBlank(message = "no blank from locationName")
    private String locationName;
    @NotNull(message = "no blank from locationType")
    private LocationType locationType;
    @NotNull(message = "no blank from locationAddress")
    private Address locationAddress;

}
