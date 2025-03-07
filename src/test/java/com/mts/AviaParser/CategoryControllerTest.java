package com.mts.AviaParser;

import com.mts.AviaParser.controller.CategoryController;
import com.mts.AviaParser.model.Category;
import com.mts.AviaParser.model.SubCategory;
import com.mts.AviaParser.service.CategoryService;
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

@WebMvcTest(controllers = {CategoryController.class})
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @Test
    void testParseCategories() throws Exception {
        // GIVEN
        List<Category> categories = List.of(new Category("Гитары", "gitary"), new Category("Обучение", "course"));

        // WHEN
        when(categoryService.getCategories()).thenReturn(categories);

        // THEN
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Гитары"))
                .andExpect(jsonPath("$[1].name").value("Обучение"))
                .andExpect(jsonPath("$[0].url").value("gitary"))
                .andExpect(jsonPath("$[1].url").value("course"));
    }

    @Test
    void testParseSubCategories() throws Exception {
        // GIVEN
        List<SubCategory> subCategories = List.of(new SubCategory().setName("Акустические гитары").setUrl("akusticheskie-gitary").setCount(883));

        // WHEN
        when(categoryService.getSubCategories("gitary")).thenReturn(subCategories);

        // THEN
        mockMvc.perform(get("/api/v1/sub")
                        .param("name", "gitary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Акустические гитары"))
                .andExpect(jsonPath("$[0].url").value("akusticheskie-gitary"))
                .andExpect(jsonPath("$[0].count").value(883));
    }
}