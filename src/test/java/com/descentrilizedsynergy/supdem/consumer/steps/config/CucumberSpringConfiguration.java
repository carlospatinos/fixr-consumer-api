package com.descentrilizedsynergy.supdem.consumer.steps.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import io.cucumber.spring.CucumberContextConfiguration;
import jakarta.annotation.PostConstruct;

@ActiveProfiles("test")
@CucumberContextConfiguration
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSpringConfiguration { // extends BasePostgresConfig {

    @LocalServerPort
    private int port;

    @PostConstruct
    public void setup() {
        System.setProperty("port", String.valueOf(port));
    }
}
