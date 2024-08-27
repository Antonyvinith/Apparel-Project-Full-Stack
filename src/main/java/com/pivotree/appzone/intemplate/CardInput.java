package com.pivotree.appzone.intemplate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardInput {
    private Long cardNumber;
    private Integer cvv;
    private String cardholderName;
    @JsonFormat(pattern = "yyyy-mm")
    private Date expiryDate;

    private Long id;
    private String username;

}
