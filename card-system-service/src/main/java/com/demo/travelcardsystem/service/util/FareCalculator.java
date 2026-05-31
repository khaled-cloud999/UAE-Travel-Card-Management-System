package com.demo.travelcardsystem.service.util;

import com.demo.travelcardsystem.businessrule.Rule;
import com.demo.travelcardsystem.businessrule.TravelStrategy;
import com.demo.travelcardsystem.entity.Journey;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@RequiredArgsConstructor
public class FareCalculator {

    @NonNull
    private final TravelStrategy travelStrategy;

    private final Comparator<Rule> ruleComparator = Comparator.comparing(Rule::getChargeableFare);

    public Double calculate(Journey journey) {
        return travelStrategy.getRuleCollection().getRules().stream()
                .filter(rule -> rule.isRuleSatisfied(journey))
                .min(ruleComparator)
                .map(Rule::getChargeableFare)
                .orElse(getMaxFare());
    }

    public double getMaxFare() {
        return travelStrategy.getRuleCollection().getMaxFare();
    }
}
