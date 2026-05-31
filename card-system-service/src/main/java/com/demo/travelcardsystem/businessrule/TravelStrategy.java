package com.demo.travelcardsystem.businessrule;

import com.demo.travelcardsystem.config.FareConfig;
import com.demo.travelcardsystem.constant.TransportType;
import com.demo.travelcardsystem.constant.Zone;
import com.demo.travelcardsystem.entity.ZonePair;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Data
@Component
@RequiredArgsConstructor
public class TravelStrategy {

    @NonNull
    private RuleCollection ruleCollection;

    @NonNull
    private FareConfig fareConfig;

    public Consumer<Double> anyWhereInZoneOneStrategy = chargeableAmount -> {
        Rule rule = new Rule();
        rule.setChargeableFare(chargeableAmount);

        //Create all possible ZonePair for Zone 1
        ZonePair zonePair = new ZonePair(Zone.ONE, Zone.ONE);
        rule.addZonePair(zonePair);

        ruleCollection.addRules(rule);

    };

    public Consumer<Double> anyOneZoneOutsideZoneOneStrategy = chargeableAmount -> {
        Rule rule = new Rule();
        rule.setChargeableFare(chargeableAmount);

        //create all possible pair of any zone outside zone one.
        rule.addZonePair(new ZonePair(Zone.TWO, Zone.TWO));
        rule.addZonePair(new ZonePair(Zone.THREE, Zone.THREE));

        ruleCollection.addRules(rule);
    };

    public Consumer<Double> anyTwoZoneIncludingZoneOneStrategy = chargeableAmount -> {
        Rule rule = new Rule();
        rule.setChargeableFare(chargeableAmount);

        //create all possible pair of any zone outside zone one.
        rule.addZonePair(new ZonePair(Zone.ONE, Zone.TWO));
        rule.addZonePair(new ZonePair(Zone.TWO, Zone.ONE));
        rule.addZonePair(new ZonePair(Zone.ONE, Zone.THREE));
        rule.addZonePair(new ZonePair(Zone.THREE, Zone.ONE));

        ruleCollection.addRules(rule);
    };

    public  Consumer<Double> anyTwoZoneExcludingZoneOneStrategy = chargeableAmount -> {
        Rule rule = new Rule();
        rule.setChargeableFare(chargeableAmount);

        //create all possible pair of any two zone excluding zone one.
        rule.addZonePair(new ZonePair(Zone.TWO, Zone.THREE));
        rule.addZonePair(new ZonePair(Zone.THREE, Zone.TWO));

        ruleCollection.addRules(rule);
    };

    public Consumer<Double> anyThreeZoneStrategy = chargeableAmount -> {
        Rule rule = new Rule();
        rule.setChargeableFare(chargeableAmount);



        ruleCollection.addRules(rule);
    };

    public BiConsumer<Double, TransportType> anyJourneyByBus = (chargeableAmount, transType) -> {
        Rule rule = new Rule();
        rule.setChargeableFare(chargeableAmount);
        rule.setTransportType(transType);

        ruleCollection.addRules(rule);


    };

    public RuleCollection loadAllBusinessRules() {
        anyWhereInZoneOneStrategy.accept(fareConfig.getZone1());
        anyOneZoneOutsideZoneOneStrategy.accept(fareConfig.getOneZoneOutside());
        anyTwoZoneIncludingZoneOneStrategy.accept(fareConfig.getTwoZonesIncludingZone1());
        anyTwoZoneExcludingZoneOneStrategy.accept(fareConfig.getTwoZonesExcludingZone1());
        anyThreeZoneStrategy.accept(fareConfig.getThreeZones());
        anyJourneyByBus.accept(fareConfig.getBus(), TransportType.BUS);

        this.ruleCollection.setMaxFare(fareConfig.getMaximum());

        return this.ruleCollection;
    }

}
