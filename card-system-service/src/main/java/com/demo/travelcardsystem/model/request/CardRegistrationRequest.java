package com.demo.travelcardsystem.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonIgnoreProperties
@Schema(description = "Request to register a new travel card")
public class CardRegistrationRequest {

    @Schema(description = "Unique card number", example = "A101", required = true)
    private String cardNumber;

    @Schema(description = "Initial balance in AED", example = "30.0", required = true)
    private Double balance;
}
