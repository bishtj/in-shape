package com.shiny.shape;

import static org.junit.Assert.assertEquals;

public abstract class AbstractBaseTest {

    protected void assertSquare(com.shiny.shape.user.dto.Square expected, com.shiny.shape.user.dto.Square actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getBottomLeftPointX(), actual.getBottomLeftPointX());
        assertEquals(expected.getBottomLeftPointY(), actual.getBottomLeftPointY());
        assertEquals(expected.getWidth(), actual.getWidth());
    }
}
