package com.descentrilizedsynergy.supdem.consumer.model;

import java.util.UUID;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ConsumerProfileResponse {
    private UUID id;
    private String name;
    private String lastName;
    private String email;
    private String description;
    private Double desiredHourlyRate;
    private Polygon coveredArea;
    private Point exactLocation;
}
