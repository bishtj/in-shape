package com.shiny.shape.service;

import com.shiny.shape.AbstractBaseTest;
import com.shiny.shape.exception.ShapeOverlapException;
import com.shiny.shape.repository.SquareShapeRepository;
import com.shiny.shape.repository.entity.Square;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(value = MockitoJUnitRunner.class)
public class ShapeServiceTest extends AbstractBaseTest {

    @InjectMocks
    private ShapeService shapeService;

    @Mock
    private SquareShapeRepository squareShapeRepository;

    @Mock
    private ShapeOverlapChecker shapeOverlapChecker;


    @Test
    public void testFindsSquareShapesSuccess() throws Exception {
        List<Square> squares = getDbSquares();
        when(squareShapeRepository.findAll()).thenReturn(squares);

        List<com.shiny.shape.user.dto.Square> squareShapes = shapeService.findSquareShapes();

        Mockito.verify(squareShapeRepository, times(1)).findAll();

        assertNotNull(squareShapes);
    }


    @Test
    public void testCreateSquareShapeSuccess() throws Exception {
        String type = "square";
        String name = "newName";
        int baselineX = 50;
        int baselineY = 60;
        int width = 10;

        com.shiny.shape.user.dto.Square inputSquare = new com.shiny.shape.user.dto.Square(type, name, baselineX, baselineY, width);
        Square saveSquare = new Square(null, type, name, baselineX, baselineY, width);

        when(squareShapeRepository.findAll()).thenReturn(getDbSquares());
        when(shapeOverlapChecker.isOverlap(any(), any())).thenReturn(false);

        when(squareShapeRepository.save(any())).thenReturn(saveSquare);

        com.shiny.shape.user.dto.Square resultSquare = shapeService.createSquareShape(inputSquare);

        verify(squareShapeRepository, times(1)).save(saveSquare);

        assertSquare(inputSquare, resultSquare);
    }


    @Test(expected = ShapeOverlapException.class)
    public void testCreateSquareShapeFailGivenThatShapeOverlapsWithExistingShapes() throws Exception {
        String type = "square";
        String name = "newName";
        int baselineX = 50;
        int baselineY = 60;
        int width = 10;

        com.shiny.shape.user.dto.Square inputSquare = new com.shiny.shape.user.dto.Square(type, name, baselineX, baselineY, width);

        List<Square> squares = getDbSquares();
        when(squareShapeRepository.findAll()).thenReturn(squares);
        when(shapeOverlapChecker.isOverlap(any(), any())).thenReturn(true);

        shapeService.createSquareShape(inputSquare);
    }

    @Test
    public void testDeleteAllSquareShapesSuccess() throws Exception {
        List<Square> squares = getDbSquares();

        shapeService.deleteSquareShapes();

        Mockito.verify(squareShapeRepository, times(1)).deleteAll();

    }


    private List<Square> getDbSquares() {
        final Square square1 =
                new Square(0L, "square", "name1", 0, 0, 5);

        final Square square2 =
                new Square(1L, "square", "name2", 10, 10, 5);

        return Arrays.asList(square1, square2);
    }




}