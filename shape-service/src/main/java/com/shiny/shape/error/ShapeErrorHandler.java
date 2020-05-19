package com.shiny.shape.error;

import com.shiny.shape.exception.ShapeDatabaseException;
import com.shiny.shape.exception.ShapeOverlapException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ShapeErrorHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleError(Throwable ex) {
        return buildErrorResponse(ex);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Throwable ex) {
        ResponseEntity responseEntity;
        if (ex instanceof ShapeOverlapException) {
            ErrorResponse errorResponse = new ErrorResponse("400", "invalid request", ex.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } else if(ex instanceof ShapeDatabaseException) {
            ErrorResponse errorResponse = new ErrorResponse("400", "database error", ex.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("500", "unknown error", ex.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
        }
        return responseEntity;
    }

}
