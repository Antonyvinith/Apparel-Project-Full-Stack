package com.pivotree.appzone.exception;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class OptimisticLockingHandler {
    /**
     * Handles OptimisticLockingFailureException, which is thrown when an update or delete operation fails due to a version conflict.
     *
     * @param e the exception that was thrown
     * @return a ResponseEntity with the error message
     */
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<Object> handleOptimisticLockingFailureException(OptimisticLockingFailureException e) {
        List<String> errorList = new ArrayList<>();
        errorList.add("Optimistic Locking Detected");
        ResponseMessage responseMessage = new ResponseMessage(errorList);
        return new ResponseEntity<>(responseMessage, HttpStatusCode.valueOf(409));
    }
}
