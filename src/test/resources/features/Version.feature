Feature: Retrieve API Version
  As a user
  I want to request app version
  So that I know which features are available

  Scenario: Get the API version
    When I request the version endpoint "/version"
    Then the response should be "v0.0.1-RELEASE"
    And the status code is 200
