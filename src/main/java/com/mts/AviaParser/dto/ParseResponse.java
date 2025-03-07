
package com.mts.AviaParser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Результат парсинга")
public class ParseResponse {
    @Schema(description = "Подробная информация о результате", example = "Products successfully parsed")
    private String message;

    @Schema(description = "Статус парсинга", example = "true")
    private boolean success;
}