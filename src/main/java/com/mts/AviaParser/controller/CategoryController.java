package com.mts.AviaParser.controller;

import com.mts.AviaParser.model.Category;
import com.mts.AviaParser.model.Product;
import com.mts.AviaParser.model.SubCategory;
import com.mts.AviaParser.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Типы товаров", description = "API для типов товаров")
public class CategoryController {
    private final CategoryService carService;

    @Operation(
            summary = "Получить категории товаров",
            description = "Позволяет получить все категории товаров"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список категорий",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Category.class))
            )
    )
    @GetMapping("/category")
    public ResponseEntity<?> findCategories() {
        List<Category> categories = carService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(
            summary = "Получить подкатегории товаров",
            description = "Позволяет получить все подкатегории товаров"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список подкатегорий",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SubCategory.class))
            )
    )
    @GetMapping("/sub")
    public ResponseEntity<?> findSubCategories(@Parameter(description = "Название категории") @RequestParam(value = "name") @NotBlank String name) {
        List<SubCategory> subCategories = carService.getSubCategories(name);
        return ResponseEntity.ok(subCategories);
    }
}
