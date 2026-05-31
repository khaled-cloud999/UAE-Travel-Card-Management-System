package com.demo.travelcardsystem.model.request;

import com.demo.travelcardsystem.constant.TransportType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties
@Schema(description = "Request to swipe a card at a station (tap-in or tap-out)")
public class SwipeRequest implements Serializable {

    @Schema(description = "Travel card number", example = "A101", required = true)
    private String cardNumber;

    @Schema(description = "Station name", example = "Algubaiba", required = true)
    private String stationName;

    @Schema(description = "Mode of transport", example = "TRAIN", required = true)
    private TransportType transportType;
}
