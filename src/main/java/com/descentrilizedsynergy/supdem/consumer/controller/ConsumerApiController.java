package com.descentrilizedsynergy.supdem.consumer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value(value = "${application.version}")
    private String APPLICATION_VERSION;

    // TODO remove
    @PostMapping("/profileWithPolygon")
    public ResponseEntity<ConsumerProfileResponse> create(@RequestBody ConsumerProfileRequest requestBody) {
        log.info("Creating profile as follows: {}", requestBody);
        ConsumerProfileResponse newProfile = service.createProfileWithPolygon(requestBody);
        return new ResponseEntity<>(newProfile, HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<ConsumerProfileResponse> createProfile(@RequestBody ConsumerProfileRequest requestBody) {
        log.info("Creating profile as follows: {}", requestBody);
        ConsumerProfileResponse newProfile = service.createProfile(requestBody);
        return new ResponseEntity<>(newProfile, HttpStatus.OK);
    }

    // TODO consider pagination and sorting
    @GetMapping("/profiles")
    public ResponseEntity<List<ConsumerProfileResponse>> listProfiles() {
        List<ConsumerProfileResponse> profiles = service.getProfiles();
        log.info("Returning [{}] profiles", profiles.size());
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @GetMapping("/profileCloseToLocation")
    public ResponseEntity<List<ConsumerProfileResponse>> listByProximityToLocation(@RequestParam Double latitude,
            @RequestParam Double longitude) {
        log.info("Returning profiles close to (lat,long): ({}, {})", latitude, longitude);
        List<ConsumerProfileResponse> profiles = service.getProfileByProximityToLocation(latitude, longitude);
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @GetMapping("/version")
    public String getVersion() {
        return Optional.ofNullable(APPLICATION_VERSION).orElse("N/A");
    }
}