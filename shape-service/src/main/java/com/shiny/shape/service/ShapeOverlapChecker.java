package com.shiny.shape.service;

import com.shiny.shape.repository.entity.Square;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class ShapeOverlapChecker {

    @Getter
    @AllArgsConstructor
    class Point {
        private int x;
        private int y;
    }

    @Getter
    @AllArgsConstructor
    class SquareCoordinate {
        private Point bottomLeft;
        private Point topRight;
    }

    public boolean isOverlap(Square src, Square target) {
        SquareCoordinate srcCoordinate = toSquareCordinates(src);
        SquareCoordinate targetCoordinate = toSquareCordinates(target);

        if (srcCoordinate.getBottomLeft().getY() >= targetCoordinate.getTopRight().getY() ||
                srcCoordinate.getTopRight().getY() <= targetCoordinate.getBottomLeft().getY()) {
            return false;
        }

        if (srcCoordinate.getBottomLeft().getX() >= targetCoordinate.getTopRight().getX() ||
                srcCoordinate.getTopRight().getX() <= targetCoordinate.getBottomLeft().getX()) {
            return false;
        }
        return true;
    }

    private SquareCoordinate toSquareCordinates(Square src) {
        Point bottomLeftPoint = new Point(src.getBottomLeftPointX(), src.getBottomLeftPointY());
        Point topRightPoint = new Point(src.getBottomLeftPointX() + src.getWidth(), src.getBottomLeftPointY() + src.getWidth());

        return new SquareCoordinate(bottomLeftPoint, topRightPoint);
    }


}
