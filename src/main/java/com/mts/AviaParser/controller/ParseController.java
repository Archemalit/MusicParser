package com.mts.AviaParser.controller;

import com.mts.AviaParser.dto.ErrorResponse;
import com.mts.AviaParser.dto.ParseRequest;
import com.mts.AviaParser.dto.ParseResponse;
import com.mts.AviaParser.exception.ParsingException;
import com.mts.AviaParser.service.ParseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parse")
@Tag(name = "Парсинг", description = "API для парсинга информации с сайта")
public class ParseController {
    private final ParseService parseService;

    @Operation(
            summary = "Распарсить данные",
            description = "Позволяет распарсить данные с возможностью фильтрации по цене и новизне"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Данные успешно распаршены и сохранены",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ParseResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Ошибка во время парсинга данных",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @PostMapping("/{category}")
    public ResponseEntity<?> parseAllProducts(
            @PathVariable String category,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Настройки парсинга товаров",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ParseRequest.class)
                    )
            )
            @RequestBody ParseRequest parseRequest
    ) {
        try {
            parseService.parseProducts(category, parseRequest);
        } catch (ParsingException e) {
            ErrorResponse errorResponse = new ErrorResponse(500, "Error while parsing products: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
        ParseResponse response = new ParseResponse("Products successfully parsed", true);
        return ResponseEntity.ok(response);
    }
}
