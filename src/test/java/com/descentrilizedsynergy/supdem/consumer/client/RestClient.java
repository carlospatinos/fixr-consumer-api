package com.descentrilizedsynergy.supdem.consumer.client;

import org.springframework.boot.test.context.TestComponent;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;

@TestComponent
@RequiredArgsConstructor
public class RestClient {

    public RequestSpecification getRequestSpecification() {
        int port = Integer.parseInt(System.getProperty("port"));
        return RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .urlEncodingEnabled(false)
                .when()
                .log()
                .everything();
    }
}