package com.mts.AviaParser.controller;

import com.mts.AviaParser.model.Product;
import com.mts.AviaParser.repository.ProductRepository;
import com.mts.AviaParser.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Товары", description = "API для получения товаров")
public class ProductController {
    private final ProductService productService;

    @Operation(
            summary = "Получить список товаров",
            description = "Позволяет получить товары с возможностью фильтрации по категории, цене, рейтингу и продавцу"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список товаров",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Product.class))
            )
    )
    @GetMapping("/product")
    public ResponseEntity<?> getProducts(
            @Parameter(description = "Категория товара")
            @RequestParam(value = "category", required = false) String category,

            @Parameter(description = "Минимальная цена")
            @RequestParam(value = "start_price", required = false) Integer startPrice,

            @Parameter(description = "Максимальная цена")
            @RequestParam(value = "end_price", required = false) Integer end_price,

            @Parameter(description = "Минимальный рейтинг")
            @RequestParam(value = "min_rate", required = false) Double minimalRating,

            @Parameter(description = "Название продавца")
            @RequestParam(value = "seller", required = false) String seller) {
        List<Product> products = productService.findByFilters(category, startPrice, end_price, minimalRating, seller);
        return ResponseEntity.ok(products);
    }
}
