package com.shiny.shape.user.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "type", "name", "bottomLeftPointX", "bottomLeftPointY", "width"})
public class Square {

    private String type;
    private String name;
    private int bottomLeftPointX;
    private int bottomLeftPointY;
    private int width;

}



