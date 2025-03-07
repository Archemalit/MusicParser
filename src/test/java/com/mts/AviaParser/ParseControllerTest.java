package com.mts.AviaParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mts.AviaParser.controller.ParseController;
import com.mts.AviaParser.dto.ParseRequest;
import com.mts.AviaParser.service.ParseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ParseController.class})
public class ParseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ParseService parseService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testParseProductsSuccess() throws Exception {
        // GIVEN
        String category = "electronics";
        ParseRequest parseRequest = new ParseRequest().setOnlyFresh(true);

        // WHEN
        doNothing().when(parseService).parseProducts(category, parseRequest);

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parse/{category}", category)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parseRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Products successfully parsed"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }
}