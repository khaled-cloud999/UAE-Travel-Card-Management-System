package com.demo.travelcardsystem.service;

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

    private final InMemoryCardTransactionRepository inMemoryCardTransactionRepository;

    public List<StationResponse> getAllStations() {
        return inMemoryCardTransactionRepository.findAllStations().stream()
                .sorted(Comparator.comparing(Station::getName))
                .map(this::toStationResponse)
                .collect(Collectors.toList());
    }

    private StationResponse toStationResponse(Station station) {
        List<String> zones = station.getZones().stream()
                .sorted(Comparator.comparing(Enum::ordinal))
                .map(zone -> zone.getDisplayLabel())
                .collect(Collectors.toList());
        return new StationResponse(station.getName(), zones);
    }
}
