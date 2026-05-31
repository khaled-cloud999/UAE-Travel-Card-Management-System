package com.demo.travelcardsystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "fare")
public class FareConfig {

    private double maximum;
    private double zone1;
    private double oneZoneOutside;
    private double twoZonesIncludingZone1;
    private double twoZonesExcludingZone1;
    private double threeZones;
    private double bus;
}
