package com.mts.AviaParser.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "Сущность подкатегории")
public class SubCategory {
    @Schema(description = "Название подкатегории", example = "Электрогитары")
    private String name;

    @Schema(description = "Ссылка подкатегории", example = "elektrogitary")
    private String url;

    @Schema(description = "Количество товаров в подкатегории", example = "883")
    private int count;
}