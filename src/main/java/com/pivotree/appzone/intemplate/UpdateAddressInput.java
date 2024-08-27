package com.pivotree.appzone.intemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressInput {
    private String addressType;

    private String line1;


    private String line2;


    private String city;


    private String state;


    private String country;


    private String pinCode;

    private long id;


    private String username;

    public UpdateAddressInput(String addressType, String line1, String line2, String city, String state, String country, String pinCode) {
            this.addressType=addressType;
            this.line1=line1;
            this.line2=line2;
            this.city=city;
            this.state=state;
            this.country=country;
            this.pinCode=pinCode;
    }
}
