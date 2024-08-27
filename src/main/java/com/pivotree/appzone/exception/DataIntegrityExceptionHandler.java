package com.pivotree.appzone.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class   DataIntegrityExceptionHandler {

    /*Handling Error For any duplicate values that is unique values constraints */


    /**
     * Handles DataIntegrityViolationException, which is thrown when there is a duplicate value in the database that violates a unique constraint.
     *
     * @param dataIntegrityViolationException the exception that was thrown
     * @return a ResponseEntity with the error message
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> UniqueConstraintException(DataIntegrityViolationException dataIntegrityViolationException) {
        List<String> errorList = new ArrayList<>();
        errorList.add("Duplicate Data are not allowed");
        ResponseMessage responseMessage = new ResponseMessage(errorList);

        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(400));
    }
}