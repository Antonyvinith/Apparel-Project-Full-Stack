package com.pivotree.appzone.intemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequest {
    private String username; // New field to specify the username
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    // Getters and setters
}
