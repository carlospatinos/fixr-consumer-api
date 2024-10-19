package com.descentrilizedsynergy.supdem.consumer.db.model;

import java.util.UUID;

import org.locationtech.jts.geom.Point;

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

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "latitude", nullable = true)
    private Double latitude;

    @Column(name = "longitude", nullable = true)
    private Double longitude;

    @Column(name = "exact_location", columnDefinition = "geometry(POINT, 4326)", nullable = true)
    @NonNull
    private Point exactLocation;

}
