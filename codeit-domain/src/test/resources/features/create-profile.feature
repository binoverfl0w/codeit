Feature: Create profile

  Scenario: A user is creating a profile
    Given an unauthenticated user
    When he creates a profile
    Then a profile with role coder is stored

  Scenario: A user is updating his profile with another role
    Given a user does not have permission
    When he updates his profile with another role
    Then role update is ignored

  Scenario: An admin creates a profile
    Given a user with manage profile permission
    When he creates another profile with different role
    Then role is set successfully