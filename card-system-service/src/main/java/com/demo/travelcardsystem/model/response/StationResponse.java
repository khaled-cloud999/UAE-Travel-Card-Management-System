package com.demo.travelcardsystem.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Station with associated fare zones")
public class StationResponse {

    @Schema(example = "Algubaiba")
    private String name;

    @Schema(example = "[\"Zone 1\", \"Zone 2\"]")
    private List<String> zones;
}
