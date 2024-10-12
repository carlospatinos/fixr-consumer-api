package com.piensoluegoexisto.supdem.consumer.db.model;

import org.springframework.data.geo.Point;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Location {
    @Id
    private Long id;

    // @Type(value = "jts_geometry")
    private Point location; // JTS geometry type for storing location coordinates

    // Standard getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
