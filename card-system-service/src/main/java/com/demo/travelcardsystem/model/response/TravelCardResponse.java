package com.demo.travelcardsystem.model.response;

import com.demo.travelcardsystem.constant.TransportType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Travel card status after an operation")
public class TravelCardResponse {

    @Schema(description = "Card number", example = "A101")
    private String cardNumber;

    @Schema(description = "Current balance in AED", example = "27.50")
    private double balance;

    @Schema(description = "Whether the cardholder is currently in transit", example = "false")
    private boolean inTransit;

    @Schema(description = "Transport type of the active journey, if in transit", example = "TRAIN")
    private TransportType transportType;
}
