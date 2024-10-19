Feature: Profile
    As a consumer
    I want to be able to create my profiles with my location information
    So that I find service providers close to me

  Scenario: No profiles available in the system
    Given There is no profiles available in the system
    When I create my profile with latitude,longitude: -7.95678205,53.42347264
    Then I get a successful response with http code 200
    And One profile is returned

  Scenario: One profile already created
    Given I add one profile with location -7.95678205,53.42347264 and covered area of 1000
    When I list all the profiles in the system
    Then I get a successful response with http code 200
    And The response returns 2 profile added
