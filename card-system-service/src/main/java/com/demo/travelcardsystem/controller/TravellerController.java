package com.demo.travelcardsystem.controller;

import com.demo.travelcardsystem.model.request.CardRegistrationRequest;
import com.demo.travelcardsystem.model.request.SwipeRequest;
import com.demo.travelcardsystem.model.response.TravelCardResponse;
import com.demo.travelcardsystem.service.TravellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/card")
@AllArgsConstructor
@CrossOrigin
@Tag(name = "Travel Card", description = "Register, recharge, and swipe Al-Naqel travel cards")
public class TravellerController {

    private TravellerService travellerService;

    @Operation(summary = "Health check", description = "Returns a message confirming the service is running.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Service is up",
                    content = @Content(schema = @Schema(type = "string", example = "Service is UP and Running")))
    })
    @GetMapping(value = "/ping")
    public String pingMe() {
        return "Service is UP and Running";
    }

    @Operation(summary = "Register a new travel card",
            description = "Creates a new card with the given card number and starting balance.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Card registered successfully"),
            @ApiResponse(responseCode = "406", description = "Invalid card number or negative balance",
                    content = @Content(schema = @Schema(type = "string", example = "This card is Invalid. Please use a valid card")))
    })
    @PostMapping(value = "/register")
    public void registerNewUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Card number and initial balance",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CardRegistrationRequest.class)))
            @RequestBody CardRegistrationRequest cardRegistrationRequest) {
        travellerService.registerNewCard(cardRegistrationRequest);
    }

    @Operation(summary = "Recharge a travel card",
            description = "Adds credit to an existing card. Request body is the raw card number string.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Card recharged successfully"),
            @ApiResponse(responseCode = "406", description = "Invalid card or negative recharge amount",
                    content = @Content(schema = @Schema(type = "string")))
    })
    @PostMapping(value = "/recharge/{rechargeAmount}")
    public void rechargeTheCard(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Existing card number",
                    required = true,
                    content = @Content(schema = @Schema(type = "string", example = "A101")))
            @RequestBody String cardNumber,
            @Parameter(description = "Amount to add in AED", required = true, example = "50.0")
            @PathVariable double rechargeAmount) {
        travellerService.rechargeTheCard(cardNumber, rechargeAmount);
    }

    @Operation(summary = "Swipe card at a station",
            description = "Tap-in starts a journey and holds the maximum fare. Tap-out completes the journey and charges the actual fare.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Swipe processed",
                    content = @Content(schema = @Schema(implementation = TravelCardResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing transport type or invalid input",
                    content = @Content(schema = @Schema(type = "string", example = "Invalid request! Please check input")))
    })
    @PostMapping(value = "/swipe")
    public TravelCardResponse swipeCard(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Card number, station name, and transport type (TRAIN or BUS)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SwipeRequest.class)))
            @RequestBody SwipeRequest swipeRequest) {
        return travellerService.swipeCard(swipeRequest);
    }

    @Operation(summary = "Get card details", description = "Returns balance and in-transit status for a card.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Card found",
                    content = @Content(schema = @Schema(implementation = TravelCardResponse.class))),
            @ApiResponse(responseCode = "406", description = "Card not found",
                    content = @Content(schema = @Schema(type = "string")))
    })
    @GetMapping(value = "/{cardNumber}")
    public TravelCardResponse checkCardDetail(
            @Parameter(description = "Travel card number", required = true, example = "A101")
            @PathVariable String cardNumber) {
        return travellerService.checkCardDetail(cardNumber);
    }

    @Operation(summary = "List all card numbers", description = "Returns every registered card number in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of card numbers",
                    content = @Content(schema = @Schema(type = "array", example = "[\"A101\", \"B201\"]")))
    })
    @GetMapping
    public List<String> fetchAllCard() {
        return travellerService.fetchAllCard();
    }
}
