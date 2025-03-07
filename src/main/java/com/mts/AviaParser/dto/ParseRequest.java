package com.mts.AviaParser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "Фильтры для парсинга")
public class ParseRequest {
    @JsonProperty("start_price")
    @Schema(description = "Минимальная цена", example = "12990")
    private int startPrice = 1;

    @JsonProperty("end_price")
    @Schema(description = "Максимальная цена", example = "48190")
    private int endPrice = 10_000_000;

    @JsonProperty("new")
    @Schema(description = "Только новые товары", example = "true")
    private boolean onlyFresh = false;
}