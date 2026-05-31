package com.demo.travelcardsystem.businessrule;

import com.demo.travelcardsystem.config.FareConfig;
import com.demo.travelcardsystem.constant.TransportType;
import com.demo.travelcardsystem.constant.Zone;
import com.demo.travelcardsystem.entity.ZonePair;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class TravelStrategy {

    @NonNull
    private final RuleCollection ruleCollection;

    @NonNull
    private final FareConfig fareConfig;

    public RuleCollection loadAllBusinessRules() {
        addZoneFareRule(fareConfig.getZone1(), new ZonePair(Zone.ONE, Zone.ONE));

        addZoneFareRule(fareConfig.getOneZoneOutside(),
                new ZonePair(Zone.TWO, Zone.TWO),
                new ZonePair(Zone.THREE, Zone.THREE));

        addZoneFareRule(fareConfig.getTwoZonesIncludingZone1(),
                new ZonePair(Zone.ONE, Zone.TWO),
                new ZonePair(Zone.TWO, Zone.ONE),
                new ZonePair(Zone.ONE, Zone.THREE),
                new ZonePair(Zone.THREE, Zone.ONE));

        addZoneFareRule(fareConfig.getTwoZonesExcludingZone1(),
                new ZonePair(Zone.TWO, Zone.THREE),
                new ZonePair(Zone.THREE, Zone.TWO));

        addZoneFareRule(fareConfig.getThreeZones());

        addBusFareRule(fareConfig.getBus(), TransportType.BUS);

        ruleCollection.setMaxFare(fareConfig.getMaximum());
        return ruleCollection;
    }

    private void addZoneFareRule(double fare, ZonePair... zonePairs) {
        Rule rule = new Rule();
        rule.setChargeableFare(fare);
        for (ZonePair zonePair : zonePairs) {
            rule.addZonePair(zonePair);
        }
        ruleCollection.addRules(rule);
    }

    private void addBusFareRule(double fare, TransportType transportType) {
        Rule rule = new Rule();
        rule.setChargeableFare(fare);
        rule.setTransportType(transportType);
        ruleCollection.addRules(rule);
    }
}
