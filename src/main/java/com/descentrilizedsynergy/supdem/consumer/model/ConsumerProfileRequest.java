package com.descentrilizedsynergy.supdem.consumer.model;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsumerProfileRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("description")
    private String description;

    @JsonProperty("desiredHourlyRate")
    private Double desiredHourlyRate;

    @JsonProperty("exactLocation")
    private Point exactLocation;

    @JsonProperty("currency")
    private String currency;

    // TODO remove this as we will be generating the polygon dynamically.
    @JsonProperty("coveredArea")
    private Polygon coveredArea;

    @JsonProperty("travelDistanceInMeters")
    private Double travelDistanceInMeters;
}
