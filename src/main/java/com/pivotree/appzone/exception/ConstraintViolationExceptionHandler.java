package com.pivotree.appzone.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@RestControllerAdvice

public class ConstraintViolationExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> list = ex.getConstraintViolations();
        List<String> errorMessage = new ArrayList<>();
        for (ConstraintViolation<?> obj : list) {

            String propertyPath = obj.getPropertyPath().toString();

            errorMessage.add(propertyPath + "Is Empty");
        }

        ResponseMessage responseMessage = new ResponseMessage(errorMessage);

        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(400));
    }

}
