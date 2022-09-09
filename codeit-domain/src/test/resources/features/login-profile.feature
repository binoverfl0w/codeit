Feature: Login
  Scenario: A user with valid credentials attempts to authenticate
    Given an unauthenticated user
    And a profile for him exists
    When username and password are valid
    Then profile context will hold that user

  Scenario: A user with invalid credentials attempts to authenticate
    Given an unauthenticated user
    And a profile for him exists
    When password is not valid
    Then bad credentials exception is thrown