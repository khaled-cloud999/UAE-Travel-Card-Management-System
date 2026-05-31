package com.demo.travelcardsystem.controller;

import com.demo.travelcardsystem.model.response.StationResponse;
import com.demo.travelcardsystem.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@AllArgsConstructor
@CrossOrigin
@Tag(name = "Stations", description = "Station and zone information for journey planning")
public class StationController {

    private StationService stationService;

    @Operation(summary = "List all stations and zones",
            description = "Returns every station in the network with its associated fare zones.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of stations",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = StationResponse.class))))
    })
    @GetMapping
    public List<StationResponse> getAllStations() {
        return stationService.getAllStations();
    }
}
