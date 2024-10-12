package com.piensoluegoexisto.supdem.consumer.db.model;

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
public class GeofenceResponse {
    private UUID id;
    private String name;
    private Polygon geometryPolygon;
    private Point geometryPoint;
}
