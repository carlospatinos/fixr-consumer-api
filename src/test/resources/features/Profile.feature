Feature: Profile
    As a user
    I want to find profiles close to my location
    So that I see which options are available

  Scenario: No profiles available in my location
    Given There is no profiles available in the system
    When I access the get profileCloseToLocation endpoint with latitude,longitude: -7.95678205,53.42347264
    Then I get a successful response with http code 200
    And An empty list of profiles is returned

  Scenario: One profile available in my location
    Given I add one profile with location -7.95678205,53.42347264 and covered area of 1000
    When I access the get profileCloseToLocation with matching location with latitude,longitude: -7.95678205,53.42347264
    Then I get a successful response with http code 200
    And The response returns 1 profile added

  Scenario: Three profiles available in my location. One pre existing and two added.
    Given I add one profile with location -7.95678205,53.42347264 and covered area of 2000
    And I add one profile with location -7.95678205,53.42347264 and covered area of 3000
    When I access the get profileCloseToLocation with matching location with latitude,longitude: -7.95678205,53.42347264
    Then I get a successful response with http code 200
    And The response returns 3 profile added
