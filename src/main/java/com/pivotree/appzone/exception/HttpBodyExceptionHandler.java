package com.pivotree.appzone.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice

public class HttpBodyExceptionHandler {

    /**
     * Handles HttpMediaTypeException, which is thrown when the request or response media type is not supported.
     *
     * @param e the exception that was thrown
     * @return a ResponseEntity with the error message
     */
    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<Object> handleHttpMediaTypeException(HttpMediaTypeException e){
        List<String> errorList = new ArrayList<>();
        errorList.add("Invalid Media Type");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(400));
    }
    /**
     * Handles HttpMessageNotReadableException, which is thrown when the request body is not valid JSON or does not match the expected content type.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity with the error message
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        List<String> errorList = new ArrayList<>();
        errorList.add("Invalid Body");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }
    /**
     * Handles InvalidFormatException, which is thrown when the input data is not in the correct format.
     *
     * @param e the exception that was thrown
     * @return a ResponseEntity with the error message
     */
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException e){
        List<String> errorList =new ArrayList<>();
        errorList.add("Invalid Format");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage,HttpStatusCode.valueOf(400));
    }

}
