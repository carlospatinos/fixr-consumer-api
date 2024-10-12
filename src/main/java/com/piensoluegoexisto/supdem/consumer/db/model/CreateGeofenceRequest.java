package com.piensoluegoexisto.supdem.consumer.db.model;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGeofenceRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("geometry_polygon")
    private Polygon geometryPolygon;

    @JsonProperty("geometry_point")
    private Point geometryPoint;
}
