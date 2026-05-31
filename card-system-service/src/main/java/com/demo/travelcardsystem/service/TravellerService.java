package com.demo.travelcardsystem.service;

import com.demo.travelcardsystem.entity.Journey;
import com.demo.travelcardsystem.entity.Station;
import com.demo.travelcardsystem.entity.TravelCard;
import com.demo.travelcardsystem.exception.InvalidCardException;
import com.demo.travelcardsystem.exception.InvalidDataProvidedException;
import com.demo.travelcardsystem.exception.InvalidRechargeAmount;
import com.demo.travelcardsystem.model.request.CardRegistrationRequest;
import com.demo.travelcardsystem.model.request.SwipeRequest;
import com.demo.travelcardsystem.model.response.TravelCardResponse;
import com.demo.travelcardsystem.repository.InMemoryCardTransactionRepository;
import com.demo.travelcardsystem.service.util.TravelCardConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class TravellerService {

    private final InMemoryCardTransactionRepository inMemoryCardTransactionRepository;
    private final TravelCardConverter travelCardConverter;

    public void registerNewCard(CardRegistrationRequest cardRegistrationRequest) {
        if (cardRegistrationRequest == null
                || !StringUtils.hasText(cardRegistrationRequest.getCardNumber())) {
            throw new InvalidCardException("This card is Invalid. Please use a valid card");
        }

        if (cardRegistrationRequest.getBalance() < 0) {
            throw new InvalidRechargeAmount("Recharge amount must not be negative");
        }

        TravelCard travelCard = new TravelCard();
        travelCard.setCardNumber(cardRegistrationRequest.getCardNumber());
        travelCard.setBalance(cardRegistrationRequest.getBalance());

        inMemoryCardTransactionRepository.registerNewCard(travelCard);
    }

    public void rechargeTheCard(String cardNumber, double rechargeAmount) {
        if (!StringUtils.hasText(cardNumber)) {
            throw new InvalidCardException("This card is Invalid. Please use a valid card");
        }

        if (rechargeAmount < 0) {
            throw new InvalidRechargeAmount("Recharge amount must not be negative");
        }

        TravelCard travelCard = inMemoryCardTransactionRepository.findCardByCardNumber(cardNumber);
        travelCard.addCredit(rechargeAmount);
    }

    public TravelCardResponse swipeCard(SwipeRequest swipeRequest) {
        if (swipeRequest.getTransportType() == null) {
            throw new InvalidDataProvidedException();
        }

        TravelCard travelCard = inMemoryCardTransactionRepository.findCardByCardNumber(swipeRequest.getCardNumber());
        Station station = inMemoryCardTransactionRepository.findStationByName(swipeRequest.getStationName());

        if (travelCard.getCurrentJourney() != null) {
            completeJourney(travelCard, station);
        } else {
            startJourney(travelCard, station, swipeRequest);
        }

        return travelCardConverter.toResponse(travelCard);
    }

    private void completeJourney(TravelCard travelCard, Station station) {
        travelCard.getCurrentJourney().setEndStation(station);
        travelCard.getCurrentJourney().setJourneyCompleted(true);
        travelCard.notifyAllObservers();
        travelCard.setCurrentJourney(null);
    }

    private void startJourney(TravelCard travelCard, Station station, SwipeRequest swipeRequest) {
        Journey journey = Journey.builder()
                .startStation(station)
                .transportType(swipeRequest.getTransportType())
                .journeyCompleted(false)
                .build();

        travelCard.setCurrentJourney(journey);
        travelCard.notifyAllObservers();
    }

    public TravelCardResponse checkCardDetail(String cardNumber) {
        TravelCard travelCard = inMemoryCardTransactionRepository.findCardByCardNumber(cardNumber);
        return travelCardConverter.toResponse(travelCard);
    }

    public List<String> fetchAllCard() {
        return inMemoryCardTransactionRepository.fetchAllCardNumber();
    }
}
