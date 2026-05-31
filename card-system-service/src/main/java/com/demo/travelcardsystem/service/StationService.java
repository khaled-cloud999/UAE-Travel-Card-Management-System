package com.demo.travelcardsystem.service;

import com.demo.travelcardsystem.constant.Zone;
import com.demo.travelcardsystem.entity.Station;
import com.demo.travelcardsystem.model.response.StationResponse;
import com.demo.travelcardsystem.repository.InMemoryCardTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StationService {

    private InMemoryCardTransactionRepository inMemoryCardTransactionRepository;

    public List<StationResponse> getAllStations() {
        return inMemoryCardTransactionRepository.findAllStations().stream()
                .sorted(Comparator.comparing(Station::getName))
                .map(this::toStationResponse)
                .collect(Collectors.toList());
    }

    private StationResponse toStationResponse(Station station) {
        List<String> zones = station.getZones().stream()
                .sorted(Comparator.comparing(Zone::ordinal))
                .map(this::toZoneLabel)
                .collect(Collectors.toList());
        return new StationResponse(station.getName(), zones);
    }

    private String toZoneLabel(Zone zone) {
        switch (zone) {
            case ONE:
                return "Zone 1";
            case TWO:
                return "Zone 2";
            case THREE:
                return "Zone 3";
            default:
                return zone.name();
        }
    }
}
