package com.pivotree.appzone.exception.advice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.pivotree.appzone.appconfig.ResponseMessage;
import jakarta.transaction.TransactionalException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.NonUniqueResultException;
import org.springframework.dao.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class    ControllerAdviceHandler {

    /*Handling Error For any duplicate values that is unique values constraints */


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> UniqueConstraintException(DataIntegrityViolationException dataIntegrityViolationException) {
        List<String> errorList = new ArrayList<>();
        errorList.add("Duplicate Data are not allowed");
        ResponseMessage responseMessage = new ResponseMessage(errorList);

        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(400));
    }

    /*Handling error for request body*/

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        List<String> errorList = new ArrayList<>();
        errorList.add("Invalid Body");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    /*Handling for constraints in the entity class*/


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


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {

        List<String> errorList = new ArrayList<>();
        errorList.add("Category ID Doesn't Exist");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(500));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException e) {
        List<String> errorList = new ArrayList<>();
        errorList.add("Field are missing"+e.getCause().getMessage());
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(404));
    }
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<Object> handleIndexOutOfBoundsException(IndexOutOfBoundsException e) {
        List<String> errorList = new ArrayList<>();
        errorList.add("Index Out Of Bound");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(401));
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<Object> handleOptimisticLockingFailureException(OptimisticLockingFailureException e) {
        List<String> errorList = new ArrayList<>();
        errorList.add("Optimistic Locking Detected");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(409));
    }

    @ExceptionHandler(UncategorizedDataAccessException.class)
    public ResponseEntity<Object> handleUncategorizedDataAccessException(UncategorizedDataAccessException e) {
        List<String> errorList = new ArrayList<>();
        errorList.add("SQL Problem:Table is non-existent"+e.getCause().getMessage());
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(500));
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<Object> handleHttpMediaTypeException(HttpMediaTypeException e){
        List<String> errorList = new ArrayList<>();
        errorList.add("Invalid Media Type");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage,HttpStatusCode.valueOf(400));
    }
    @ExceptionHandler(JsonProcessingException.class)

    public ResponseEntity<Object> handleJsonProcessingException(JsonProcessingException e){
        List<String> errorList = new ArrayList<>();
        errorList.add("Invalid JSON Body");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage,HttpStatusCode.valueOf(400));
    }
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException e){
        List<String> errorList =new ArrayList<>();
        errorList.add("Invalid Format");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage,HttpStatusCode.valueOf(400));
    }
    @ExceptionHandler(ConcurrencyFailureException.class)
    public ResponseEntity<Object> handleConcurrencyFailureException(ConcurrencyFailureException e){
        List<String> errorList =new ArrayList<>();
        errorList.add("Concurrency Conflict Detected,Please Refresh Page");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage,HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(DeadlockLoserDataAccessException.class)
    public ResponseEntity<Object> handleDeadlockLoserDataAccessException(DeadlockLoserDataAccessException e){
        List<String> errorList =new ArrayList<>();
        errorList.add("Deadlock  Detected,Please Refresh Page");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage,HttpStatusCode.valueOf(400));
    }
    @ExceptionHandler(TransactionalException.class)
    public ResponseEntity<Object> handleTransactionalException(TransactionalException e){
        List<String> errorList =new ArrayList<>();
        errorList.add("Data Transaction Failed");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage,HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e){
        List<String> errorList =new ArrayList<>();
        errorList.add(e.getMessage());
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage,HttpStatusCode.valueOf(400));
    }
    @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<Object> handleNonUniqueResultException(NonUniqueResultException e){
        List<String> errorList =new ArrayList<>();
        errorList.add("Query returned More Values");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage,HttpStatusCode.valueOf(400));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e){
        List<String> errorList =new ArrayList<>();
        errorList.add(e.getMessage());
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage,HttpStatusCode.valueOf(500));
    }

}

