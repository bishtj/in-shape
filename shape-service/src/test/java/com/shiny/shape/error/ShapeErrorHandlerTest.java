package com.shiny.shape.error;

import com.shiny.shape.exception.ShapeDatabaseException;
import com.shiny.shape.exception.ShapeOverlapException;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ShapeErrorHandlerTest {

    @Test
    public void testHandleErrorForOverlapError() {
        ShapeErrorHandler shapeErrorHandler = new ShapeErrorHandler();

        String description = "Overlap error description";
        ResponseEntity<ErrorResponse> overlap_error = shapeErrorHandler.handleError(new ShapeOverlapException(description));

        assertThat(overlap_error.getStatusCode().value(), is(HttpStatus.BAD_REQUEST.value()));
        assertThat(requireNonNull(overlap_error.getBody()).getDescription(), is(description));
        assertThat(overlap_error.getBody().getErrorCode(), is("invalid request"));
    }

    @Test
    public void testHandleErrorForDatabaseError() {
        ShapeErrorHandler shapeErrorHandler = new ShapeErrorHandler();

        String description = "Database error description";
        ResponseEntity<ErrorResponse> database_error = shapeErrorHandler.handleError(new ShapeDatabaseException(description));

        assertThat(database_error.getStatusCode().value(), is(HttpStatus.BAD_REQUEST.value()));
        assertThat(requireNonNull(database_error.getBody()).getDescription(), is(description));
        assertThat(database_error.getBody().getErrorCode(), is("database error"));
    }



}