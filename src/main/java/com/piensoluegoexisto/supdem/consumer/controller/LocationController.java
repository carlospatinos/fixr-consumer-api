package com.piensoluegoexisto.supdem.consumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piensoluegoexisto.supdem.consumer.db.model.CreateGeofenceRequest;
import com.piensoluegoexisto.supdem.consumer.db.model.GeofenceResponse;
import com.piensoluegoexisto.supdem.consumer.db.model.Location;
import com.piensoluegoexisto.supdem.consumer.service.CreateGeofenceUseCase;
import com.piensoluegoexisto.supdem.consumer.service.LocationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LocationController {
    @Autowired
    private CreateGeofenceUseCase service;

    @Autowired
    private LocationService locationService;

    @GetMapping("/health")
    public ResponseEntity<String> health() throws InterruptedException {
        log.info("Service working");
        return ResponseEntity.ok("Service working");
    }

    @GetMapping("/locations/near")
    public List<Location> getLocationsNear(@RequestParam double longitude, @RequestParam double latitude,
            @RequestParam double distance) {
        return locationService.getLocationsNear(longitude, latitude, distance);
    }

    @PostMapping("/geofences")
    public ResponseEntity<GeofenceResponse> create(@RequestBody CreateGeofenceRequest requestBody) {
        GeofenceResponse geofenceResponse = service.createGeofenceUseCase(requestBody);
        return new ResponseEntity<>(geofenceResponse, HttpStatus.OK);
    }

    @GetMapping("/geofences")
    public ResponseEntity<List<GeofenceResponse>> list() {
        List<GeofenceResponse> geofenceResponse = service.listGeofenceUseCase();
        return new ResponseEntity<>(geofenceResponse, HttpStatus.OK);
    }
}