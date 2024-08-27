package com.pivotree.appzone.outtemplate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor

public class CardOutput {

    private Long cardId;
    private Long cardNumber;
    private String cardholderName;
    @JsonFormat(pattern = "yyyy-mm")
    private Date expiryDate;
    private Integer cvv;
}
