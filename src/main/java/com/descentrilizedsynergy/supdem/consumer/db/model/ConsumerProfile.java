package com.descentrilizedsynergy.supdem.consumer.db.model;

import java.util.UUID;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "consumer_profile")
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsumerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "last_name", nullable = false)
    @NonNull
    private String lastName;

    @Column(name = "email", nullable = false)
    @NonNull
    private String email;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "desired_hourly_price", nullable = true)
    private Double desiredHourlyRate;

    @Column(name = "travel_distance", nullable = true)
    @NonNull
    private Double travelDistance;

    @Column(name = "exact_location", columnDefinition = "geometry(POINT, 4326)", nullable = true)
    @NonNull
    private Point exactLocation;

    @Column(name = "currency", nullable = true)
    private String currency;

    @Column(name = "covered_area", columnDefinition = "geometry(POLYGON, 4326)", nullable = true)
    @NonNull
    private Polygon coveredArea;

}
