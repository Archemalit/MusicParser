package com.mts.AviaParser.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
@Schema(description = "Сущность товара")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    @Schema(description = "Уникальный идентификатор товара", example = "1")
    private Long id;

    @Column(name = "shop_id", nullable = false, length = 50)
    @Schema(description = "ID товара в магазине", example = "123ABC")
    private String shopId;

    @Column(name = "name", nullable = false, length = 255)
    @Schema(description = "Название товара", example = "YAMAHA ME55BK")
    private String name;

    @Column(name = "seller", length = 255)
    @Schema(description = "Продавец товара", example = "Музторг")
    private String seller;

    @Column(name = "category", length = 100)
    @Schema(description = "Категория товара", example = "Бас-гитара")
    private String category;

    @Column(name = "price", nullable = false)
    @Schema(description = "Цена товара в рублях", example = "37990")
    private Integer price;

    @Column(name = "rating_value")
    @Schema(description = "Средний рейтинг товара", example = "4.5")
    private Double ratingValue;

    @Column(name = "rating_count")
    @Schema(description = "Количество отзывов", example = "256")
    private Integer ratingCount;

    public Product() {}

    public Product(String shopId, String name, String seller, String category, Integer price, Double ratingValue, Integer ratingCount) {
        this.shopId = shopId;
        this.name = name;
        this.seller = seller;
        this.category = category;
        this.price = price;
        this.ratingValue = ratingValue;
        this.ratingCount = ratingCount;
    }
    // TODO: мб эти конструкторы можно убрать
}