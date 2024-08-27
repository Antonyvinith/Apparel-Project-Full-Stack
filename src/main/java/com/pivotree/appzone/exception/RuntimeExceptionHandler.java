package com.pivotree.appzone.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class RuntimeExceptionHandler {
    /**
     * Handles RuntimeException, which is a general exception class.
     *
     * @param e the exception that was thrown
     * @return a ResponseEntity with the error message
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e){
        List<String> errorList =new ArrayList<>();
        errorList.add(e.getMessage());
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(500));
    }

}
