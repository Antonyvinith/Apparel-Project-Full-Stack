package com.pivotree.appzone.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
@RestControllerAdvice

public class IllegalArgumentExceptionHandler {
    /**
     * Handles IllegalArgumentException, which is thrown when the input is not valid.
     *
     * @param illegalArgumentException the exception that was thrown
     * @return a ResponseEntity with the error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {

        List<String> errorList = new ArrayList<>();
        errorList.add("Category ID Doesn't Exist");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(500));
    }
}
