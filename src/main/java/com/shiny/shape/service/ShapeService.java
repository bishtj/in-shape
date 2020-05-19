package com.shiny.shape.service;

import com.shiny.shape.exception.ShapeDatabaseException;
import com.shiny.shape.exception.ShapeOverlapException;
import com.shiny.shape.repository.SquareShapeRepository;
import com.shiny.shape.repository.entity.Square;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Component
@Slf4j
public class ShapeService {

    @Autowired
    private SquareShapeRepository squareShapeRepository;

    @Autowired
    private ShapeOverlapChecker shapeOverlapChecker;

    public List<com.shiny.shape.user.dto.Square> findSquareShapes() {

        List<Square> shapes = squareShapeRepository.findAll();

        return mapToDto(shapes);
    }

    public com.shiny.shape.user.dto.Square createSquareShape(com.shiny.shape.user.dto.Square square) throws Exception {
        Square entity = mapToEntity(square);
        checkOverlapWithPersisted(entity);
        try {
            Square resp = squareShapeRepository.save(entity);
            return mapToDto(resp);

        } catch(DataIntegrityViolationException ex) {
            throw new ShapeDatabaseException(ex.getMessage());
        }
    }

    public void deleteSquareShapes() {
        squareShapeRepository.deleteAll();
    }



    private void checkOverlapWithPersisted(Square square) throws ShapeOverlapException {
        List<Square> shapes = squareShapeRepository.findAll();
        if (shapes.stream().filter(shape -> shapeOverlapChecker.isOverlap(shape, square)).count() > 0) {
            throw new ShapeOverlapException("Shape overlapped with existing shape");
        }
    }

    private Square mapToEntity(com.shiny.shape.user.dto.Square sq) {
        Square square = new Square();
        square.setType(sq.getType());
        square.setName(sq.getName());
        square.setBottomLeftPointX(sq.getBottomLeftPointX());
        square.setBottomLeftPointY(sq.getBottomLeftPointY());
        square.setWidth(sq.getWidth());
        return square;
    }

    private List<com.shiny.shape.user.dto.Square> mapToDto(List<Square> shapes) {
        return shapes.stream().map(ent -> mapToDto(ent)).collect(Collectors.toList());
    }

    private com.shiny.shape.user.dto.Square mapToDto(Square resp) {
        return new com.shiny.shape.user.dto.Square(resp.getType(), resp.getName(), resp.getBottomLeftPointX(), resp.getBottomLeftPointY(), resp.getWidth());
    }

}
