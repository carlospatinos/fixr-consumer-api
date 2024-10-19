package com.descentrilizedsynergy.supdem.consumer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        return ResponseEntity.ok("{status: \"Service working\"}");
    }

    @Value(value = "${application.version}")
    private String APPLICATION_VERSION;

    @PostMapping("/profile")
    public ResponseEntity<ConsumerProfileResponse> createProfile(@RequestBody ConsumerProfileRequest requestBody) {
        log.info("Creating profile as follows: {}", requestBody);
        ConsumerProfileResponse newProfile = service.createProfile(requestBody);
        return new ResponseEntity<>(newProfile, HttpStatus.OK);
    }

    // TODO consider pagination and sorting
    @GetMapping("/profiles")
    public ResponseEntity<ConsumerProfileResponse> listProfiles() {
        ConsumerProfileResponse response = service.getProfiles();
        log.info("Returning [{}] profiles", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/version")
    public String getVersion() {
        return Optional.ofNullable(APPLICATION_VERSION).orElse("N/A");
    }
}