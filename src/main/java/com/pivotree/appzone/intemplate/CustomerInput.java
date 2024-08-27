package com.pivotree.appzone.intemplate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CustomerInput {
    @NotBlank
    @NotNull
    private String firstName;
    @NotBlank
    @NotNull
    private String lastName;
    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private UserInTemplate userInput;

    public CustomerInput(String firstName, String lastName, String email, String phone, Date dateOfBirth, UserInTemplate userInput) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.userInput = userInput;
    }
}
