package com.descentrilizedsynergy.supdem.consumer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.descentrilizedsynergy.supdem.consumer.model.LoginRequest;
import com.descentrilizedsynergy.supdem.consumer.model.LoginResponse;
import com.descentrilizedsynergy.supdem.consumer.service.AuthenticationService;
import com.descentrilizedsynergy.supdem.consumer.service.JwtService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicApiController {

    @Value(value = "${application.version}")
    private String APPLICATION_VERSION;

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public PublicApiController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    // // TODO replaced by the profile creation url?
    // @PostMapping("/signup")
    // public ResponseEntity<ProducerProfile> register(@RequestBody RegisterUserTO
    // registerUserDto) {
    // log.info("Creating a new profile");
    // ResponseEntity<ProducerProfile> response = null;
    // ProducerProfile registeredUser = null;
    // try {
    // registeredUser = authenticationService.signup(registerUserDto);
    // response = ResponseEntity.ok(registeredUser);
    // } catch (ProfileAlreadyExistException e) {
    // response = ResponseEntity.badRequest().build();
    // }
    // return response;
    // }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginUserDto) {
        UserDetails authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/version")
    public String getVersion() {
        return Optional.ofNullable(APPLICATION_VERSION).orElse("N/A");
    }
}