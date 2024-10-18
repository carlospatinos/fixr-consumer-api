package com.descentrilizedsynergy.supdem.consumer.steps;

import static org.hamcrest.Matchers.is;

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
import net.minidev.json.JSONArray;
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

    @When("I access the get profileCloseToLocation endpoint with latitude,longitude: {double},{double}")
    public void iAccessGetProfileEndpointWithMyLocation(double latitude, double longitude) {
        RequestSpecification rq = restClient.getRequestSpecification();
        response = rq.queryParam("latitude", latitude).queryParam("longitude", longitude)
                .get("/api/consumer/profileCloseToLocation");
        // response.then().log().all();
    }

    @Then("I get a successful response with http code {int}")
    public void iGetResponse(int expectedHttpResponseCode) {
        response.then().statusCode(expectedHttpResponseCode);
    }

    @And("An empty list of profiles is returned")
    public void responseHasExpectedFieldsForGetUsersEndpoint() {
        response.as(ConsumerProfileResponse[].class);
    }

    @Given("I add one profile with location {double},{double} and covered area of {double}")
    public void iAddOneProfileUsingTheEndpoint(double latitude, double longitude, double distanceInMeeters) {
        JSONArray coordinatesArray = new JSONArray();
        coordinatesArray.add(-7.943368603631512);
        coordinatesArray.add(53.41752495789626);
        JSONObject exactLocationJson = new JSONObject();
        exactLocationJson.put("coordinates", coordinatesArray);
        exactLocationJson.put("type", "Point");
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Felipe");
        requestParams.put("lastName", "Plumber D");
        requestParams.put("email", "lpluma@tets.com");
        requestParams.put("description", "An amazing person to work with. ");
        requestParams.put("desiredHourlyRate", 18.20);
        requestParams.put("exactLocation", exactLocationJson);
        requestParams.put("travelDistanceInMeters", 4000);

        RequestSpecification rq = restClient.getRequestSpecification();
        response = rq.header("Content-Type",
                "application/json").body(requestParams.toJSONString()).post("/api/consumer/profile");
    }

    @When("I access the get profileCloseToLocation with matching location with latitude,longitude: {double},{double}")
    public void iAccessGetProfileEndpointWithMatchingLocation(double latitude, double longitude) {
        RequestSpecification rq = restClient.getRequestSpecification();
        response = rq.queryParam("latitude", latitude).queryParam("longitude", longitude)
                .get("/api/consumer/profileCloseToLocation");
        response.then().log().all();
    }

    @And("The response returns {int} profile added")
    public void iGetResponseWithOneProfile(int numberOfProfiles) {
        response.as(ConsumerProfileResponse[].class);
        response.then().assertThat().body("size()", is(numberOfProfiles));
    }

}
