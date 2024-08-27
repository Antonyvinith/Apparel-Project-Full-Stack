package com.pivotree.appzone.exception;

import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RestController

public class PSQLExceptionHandler {
    /**
     * Handles UncategorizedDataAccessException, which is thrown when an unknown exception occurs during data access.
     *
     * @param e the exception that was thrown
     * @return a ResponseEntity with the error message
     */

    @ExceptionHandler(UncategorizedDataAccessException.class)
    public ResponseEntity<Object> handleUncategorizedDataAccessException(UncategorizedDataAccessException e) {
        List<String> errorList = new ArrayList<>();
        errorList.add("SQL Problem:Table is non-existent"+e.getCause().getMessage());
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(500));
    }
}
