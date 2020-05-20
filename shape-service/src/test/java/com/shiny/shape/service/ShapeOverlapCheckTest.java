package com.shiny.shape.service;

import com.shiny.shape.repository.entity.Square;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ShapeOverlapCheckTest {

    ShapeOverlapChecker shapeOverlapChecker = new ShapeOverlapChecker();

    @Test
    public void testShapeDoNotOverlap() {
        assertNoOverlap(squareShape(10,10, 10), squareShape(20,20,10));
        assertNoOverlap(squareShape(15,15, 10), squareShape(25,25,5));
        assertNoOverlap(squareShape(40,40, 10), squareShape(0,30,5));
        assertNoOverlap(squareShape(0,0, 10), squareShape(20,20,20));
    }

    @Test
    public void testShapeOverlap() {
        assertOverlap(squareShape(10,10, 10), squareShape(9,10,5));
        assertOverlap(squareShape(20,20, 10), squareShape(19,18,20));
        assertOverlap(squareShape(0,0, 10), squareShape(5,5,10));
    }

    private void assertNoOverlap(Square square1, Square square2) {
        assertFalse(shapeOverlapChecker.isOverlap(square1, square2));
    }

    private void assertOverlap(Square square1, Square square2) {
        assertTrue(shapeOverlapChecker.isOverlap(square1, square2));
    }

    private Square squareShape(int x, int y, int width) {
        return new Square(0L, "Square", "name1", y, y, width);
    }


}