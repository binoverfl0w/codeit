Feature: Submit code

  Scenario: A user submits his code for a problem
    Given a user
    When he submits his code
    And his code is accepted
    Then he receives the problem's points
