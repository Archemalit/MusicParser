package com.mts.AviaParser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Ошибка парсинга")
public class ErrorResponse {
    @Schema(description = "Статус код ошибки", example = "500")
    private int statusCode;

    @Schema(description = "Подробная информация об ошибке", example = "Error while parsing products")
    private String message;
}