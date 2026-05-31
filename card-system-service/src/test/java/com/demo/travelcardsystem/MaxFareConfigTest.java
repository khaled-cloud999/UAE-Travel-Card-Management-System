package com.demo.travelcardsystem;

import com.demo.travelcardsystem.businessrule.TravelStrategy;
import com.demo.travelcardsystem.config.FareConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxFareConfigTest extends IntegrationTest {

    @Autowired
    private FareConfig fareConfig;

    @Autowired
    private TravelStrategy travelStrategy;

    @DisplayName("Maximum fare is loaded from application.properties")
    @Test
    void maxFareIsLoadedFromConfiguration() {
        assertEquals(7.00, fareConfig.getMaximum());
        assertEquals(7.00, travelStrategy.getRuleCollection().getMaxFare());
    }
}
