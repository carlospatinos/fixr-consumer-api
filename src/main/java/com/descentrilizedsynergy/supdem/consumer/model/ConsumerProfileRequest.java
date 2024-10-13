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

    @JsonProperty("geometry_polygon")
    private Polygon geometryPolygon;

    @JsonProperty("geometry_point")
    private Point geometryPoint;

    @JsonProperty("diameter")
    private Double diameterInMeters;
}
