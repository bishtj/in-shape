package com.shiny.shape.repository;

import com.shiny.shape.repository.entity.Square;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SquareShapeRepository extends JpaRepository<Square, Long> {

}
