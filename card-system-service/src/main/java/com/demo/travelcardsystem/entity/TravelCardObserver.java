package com.demo.travelcardsystem.entity;

import com.demo.travelcardsystem.service.util.FareCalculator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TravelCardObserver implements Observer<TravelCard> {

    private final FareCalculator fareCalculator;

    @Override
    public void reactOnChange(TravelCard travelCard) {
        Journey journey = travelCard.getCurrentJourney();

        if (journey.isJourneyCompleted()) {
            travelCard.addCredit(fareCalculator.getMaxFare());
            debitChargeableFare(travelCard);
        } else {
            travelCard.debitAmount(fareCalculator.getMaxFare());
        }
    }

    private void debitChargeableFare(TravelCard travelCard) {
        double maxFare = fareCalculator.getMaxFare();
        Double calculatedFare = fareCalculator.calculate(travelCard.getCurrentJourney());
        travelCard.debitAmount(calculatedFare != null ? calculatedFare : maxFare);
    }
}
