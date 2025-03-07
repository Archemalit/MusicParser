package com.mts.AviaParser;

import com.mts.AviaParser.controller.ProductController;
import com.mts.AviaParser.model.Product;
import com.mts.AviaParser.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ProductController.class})
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void testParseCategories() throws Exception {
        // GIVEN
        List<Product> categories = List.of(
                new Product("123ABC", "YAMAHA 23H", "Музтог", "Бас-гитары", 13390, 5.0, 12),
                new Product("23BC11", "SONY NEW 2", "Музтог", "Бас-гитары", 33390, 4.5, 3)
        );

        // WHEN
        when(productService.findByFilters("Бас-гитары", null, null, null, null)).thenReturn(categories);

        // THEN
        mockMvc.perform(get("/api/v1/product")
                        .param("category", "Бас-гитары"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shopId").value("123ABC"));
    }
}