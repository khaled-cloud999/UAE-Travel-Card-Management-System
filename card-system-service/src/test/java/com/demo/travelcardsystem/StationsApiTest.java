package com.demo.travelcardsystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StationsApiTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("GET /api/stations returns all stations with zones")
    @Test
    void get_all_stations_returns_stations_with_zones() throws Exception {
        mockMvc.perform(get("/api/stations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[?(@.name=='Algubaiba')].zones[0]").value("Zone 1"))
                .andExpect(jsonPath("$[?(@.name=='Jumeirah')].zones.length()").value(2))
                .andExpect(jsonPath("$[?(@.name=='Jumeirah')].zones[?(@=='Zone 1')]").exists())
                .andExpect(jsonPath("$[?(@.name=='Jumeirah')].zones[?(@=='Zone 2')]").exists())
                .andExpect(jsonPath("$[?(@.name=='Bur Dubai')].zones[0]").value("Zone 3"))
                .andExpect(jsonPath("$[?(@.name=='Deirah')].zones[0]").value("Zone 2"));
    }

    @DisplayName("Stations endpoint is documented in OpenAPI")
    @Test
    void stations_endpoint_is_documented_in_open_api() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/stations']").exists());
    }
}
