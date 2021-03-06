package com.shiny.shape.controller;

import com.shiny.shape.service.ShapeService;
import com.shiny.shape.user.dto.Square;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shape")
public class ShapeController {

    @Autowired
    private ShapeService shapeService;

    /*
     Returns all square shapes
     Parameters : none
    */
    @GetMapping(value = "/square")
    public ResponseEntity<List<Square>> findSquareShapes() {
        List<Square> squares = shapeService.findSquareShapes();
        return ResponseEntity.ok(squares);
    }

    /*
     Adds a square shape
     Input : Square DTO
     Output : Created Square shape DTO
     */
    @PostMapping(value = "/square/create")
    public ResponseEntity<Square> createSquareShape(@RequestBody Square square) throws Exception {
        Square response = shapeService.createSquareShape(square);
        return ResponseEntity.ok(response);
    }

    /*
    Deletes all square shapes
    Parameters :none
    */
    @DeleteMapping(value = "/square")

    public ResponseEntity<Void> deleteSquareShapes() {
        shapeService.deleteSquareShapes();
        return ResponseEntity.noContent().build();
    }

}
