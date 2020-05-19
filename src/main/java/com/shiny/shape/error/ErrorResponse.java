package com.shiny.shape.error;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String errorCode;
    private String description;
}