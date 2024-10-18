package com.descentrilizedsynergy.supdem.consumer.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;

import com.descentrilizedsynergy.supdem.consumer.client.RestClient;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApplicationVersion {
    private final RestClient restClient;
    private Response response;

    @Autowired
    private TestRestTemplate restTemplate;

    @When("I request the version endpoint {string}")
    public void iRequestTheEndpoint(String path) {
        RequestSpecification rq = restClient.getRequestSpecification();
        response = rq.get("/api/consumer/version");
    }

    @Then("the response should be {string}")
    public void theResponseShouldBe(String version) {
        assertEquals(version, response.asString());
    }

    @And("the status code is {int}")
    public void theStatusCodeIs(int statusCode) {
        assertEquals(HttpStatusCode.valueOf(statusCode), HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
