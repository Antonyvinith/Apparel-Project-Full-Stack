package com.pivotree.appzone.intemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressInput {

    private String addressType;

    private String line1;


    private String line2;


    private String city;


    private String state;


    private String country;


    private String pinCode;

    //private long id;

    private String username;



}
