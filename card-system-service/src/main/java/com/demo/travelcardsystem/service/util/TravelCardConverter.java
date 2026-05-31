package com.demo.travelcardsystem.service.util;

import com.demo.travelcardsystem.entity.Journey;
import com.demo.travelcardsystem.entity.TravelCard;
import com.demo.travelcardsystem.model.response.TravelCardResponse;
import org.springframework.stereotype.Component;

@Component
public class TravelCardConverter {

    public TravelCardResponse toResponse(TravelCard travelCard) {
        TravelCardResponse travelCardResponse = new TravelCardResponse();
        travelCardResponse.setCardNumber(travelCard.getCardNumber());
        travelCardResponse.setBalance(travelCard.getBalance());

        Journey currentJourney = travelCard.getCurrentJourney();
        if (currentJourney != null) {
            travelCardResponse.setTransportType(currentJourney.getTransportType());
        }

        travelCardResponse.setInTransit(currentJourney != null);
        return travelCardResponse;
    }
}
