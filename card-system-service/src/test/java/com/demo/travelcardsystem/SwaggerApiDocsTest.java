package com.demo.travelcardsystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SwaggerApiDocsTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("OpenAPI JSON documentation is exposed")
    @Test
    void open_api_docs_are_available() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("Al-Naqel Fare Card System API"))
                .andExpect(jsonPath("$.paths['/api/card/ping']").exists());
    }

    @DisplayName("Swagger UI page is accessible")
    @Test
    void swagger_ui_is_available() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());
    }
}
