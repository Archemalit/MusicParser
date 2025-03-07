package com.mts.AviaParser.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность категории")
public class Category {
    @Schema(description = "Название категории", example = "Гитары")
    private String name;

    @Schema(description = "Ссылка категории", example = "gitary")
    private String url;
}