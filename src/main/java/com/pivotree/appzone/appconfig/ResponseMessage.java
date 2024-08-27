package com.pivotree.appzone.appconfig;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
public class ResponseMessage {

    private Object responseData;
    private LocalDateTime timeStamp;
    private String successMessage;
    private List<String> errorDetails;

    public ResponseMessage(List<String> errorDetails) {

        this.timeStamp = LocalDateTime.now();
        this.errorDetails = errorDetails;
    }


    public ResponseMessage(Object productOutput) {
        this.responseData = productOutput;
        this.successMessage = "created";
        this.timeStamp = LocalDateTime.now();
    }


    /**
     * DTO
     * Message
     * Error Details
     *
     */


}
