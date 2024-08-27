package com.pivotree.appzone.outtemplate;

import com.pivotree.appzone.entity.Address;
import com.pivotree.appzone.enums.LocationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LocationOutput {
    private Integer locationID;
    private String locationReference;
    private String locationName;
    private LocationType locationType;
    private Address locationAddress;

    public LocationOutput(Integer locationID, String locationReference, String locationName, LocationType locationType,Address locationAddress) {
        this.locationID = locationID;
        this.locationReference = locationReference;
        this.locationName = locationName;
        this.locationType = locationType;
        this.locationAddress=locationAddress;
    }
}
