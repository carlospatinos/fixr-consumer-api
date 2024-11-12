package com.descentrilizedsynergy.supdem.consumer.steps;

import static org.junit.Assert.assertEquals;

import com.descentrilizedsynergy.supdem.consumer.client.RestClient;
import com.descentrilizedsynergy.supdem.consumer.model.ConsumerProfileResponse;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;

// import static io.restassured.RestAssured.*;
// import static io.restassured.matcher.RestAssuredMatchers.*;
// import static org.hamcrest.Matchers.*;

@Data
@RequiredArgsConstructor
public class ProfileSteps {

    private final RestClient restClient;
    private Response response;

    @Given("There is no profiles available in the system")
    public void noProfilesAvailableInTheSystem() {
    }

    @When("I create my profile with latitude,longitude: {double},{double}")
    public void iAccessGetProfileEndpointWithMyLocation(double latitude, double longitude) {
        // TODO from here;
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Felipe");
        requestParams.put("lastName", "Plumber D");
        requestParams.put("email", "customer1@tets.com");
        requestParams.put("password", "password123");
        requestParams.put("description", "An amazing person to work with. ");
        requestParams.put("address", "An amazing person to work with. ");
        requestParams.put("latitude", latitude);
        requestParams.put("longitude", longitude);

        RequestSpecification rq = restClient.getRequestSpecification();
        response = rq.header("Content-Type",
                "application/json").body(requestParams.toJSONString()).post("/api/consumer/profile");
    }

    @Then("I get a successful response with http code {int}")
    public void iGetResponse(int expectedHttpResponseCode) {
        response.then().statusCode(expectedHttpResponseCode);
    }

    @And("One profile is returned")
    public void responseHasExpectedFieldsForGetUsersEndpoint() {
        response.as(ConsumerProfileResponse.class);
    }

    @Given("I add one profile with location {double},{double} and covered area of {double}")
    public void iAddOneProfileUsingTheEndpoint(double latitude, double longitude, double distanceInMeeters) {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Client");
        requestParams.put("lastName", "Two");
        requestParams.put("email", "customer2@tets.com");
        requestParams.put("password", "password123");
        requestParams.put("description", "An amazing person to work with. ");
        requestParams.put("address", "An amazing person to work with. ");
        requestParams.put("latitude", latitude);
        requestParams.put("longitude", longitude);

        RequestSpecification rq = restClient.getRequestSpecification();
        response = rq.header("Content-Type",
                "application/json").body(requestParams.toJSONString()).post("/api/consumer/profile");
    }

    @When("I list all the profiles in the system")
    public void iAccessGetProfileEndpointWithMatchingLocation() {
        RequestSpecification rq = restClient.getRequestSpecification();
        response = rq.get("/api/consumer/profiles");
        response.then().log().all();
    }

    @And("The response returns {int} profile added")
    public void iGetResponseWithOneProfile(int numberOfProfiles) {
        response.as(ConsumerProfileResponse.class);
        ConsumerProfileResponse realResponse = response.then().assertThat().extract().as(ConsumerProfileResponse.class);
        assertEquals(numberOfProfiles, realResponse.getProfiles().size());
    }

}
