package com.descentrilizedsynergy.supdem.consumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.descentrilizedsynergy.supdem.consumer.model.ConsumerProfileRequest;
import com.descentrilizedsynergy.supdem.consumer.model.ConsumerProfileResponse;
import com.descentrilizedsynergy.supdem.consumer.service.ConsumerProfileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ConsumerApiController {
    @Autowired
    private ConsumerProfileService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() throws InterruptedException {
        log.info("Service working");
        return ResponseEntity.ok("Service working");
    }

    @PostMapping("/geofences")
    public ResponseEntity<ConsumerProfileResponse> create(@RequestBody ConsumerProfileRequest requestBody) {
        ConsumerProfileResponse geofenceResponse = service.createGeofenceUseCase(requestBody);
        return new ResponseEntity<>(geofenceResponse, HttpStatus.OK);
    }

    @PostMapping("/geofencesLatLong")
    public ResponseEntity<ConsumerProfileResponse> createByLatLong(@RequestBody ConsumerProfileRequest requestBody) {
        log.info("requestBody: {}", requestBody);
        ConsumerProfileResponse geofenceResponse = service.createGeofenceUseCaseLatLongDiam(requestBody);
        return new ResponseEntity<>(geofenceResponse, HttpStatus.OK);
    }

    @GetMapping("/geofences")
    public ResponseEntity<List<ConsumerProfileResponse>> list() {
        List<ConsumerProfileResponse> geofenceResponse = service.listGeofenceUseCase();
        return new ResponseEntity<>(geofenceResponse, HttpStatus.OK);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<ConsumerProfileResponse>> listNearby(@RequestParam Double latitude,
            @RequestParam Double longitude) {
        log.info("Coordinates (lat,long): ({}, {})", latitude, longitude);
        List<ConsumerProfileResponse> geofenceResponse = service.getNearbyService(latitude, longitude);
        return new ResponseEntity<>(geofenceResponse, HttpStatus.OK);
    }
}