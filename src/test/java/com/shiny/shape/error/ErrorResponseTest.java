package com.shiny.shape.error;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ErrorResponseTest {

    @Test
    public void testErrorResponse() {

        ErrorResponse errorResponse = new ErrorResponse("message", "errorCode", "description");

        assertThat(errorResponse.getMessage(), is("message"));
        assertThat(errorResponse.getErrorCode(), is("errorCode"));
        assertThat(errorResponse.getDescription(), is("description"));

    }

}