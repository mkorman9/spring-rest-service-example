Feature: Adding cats
  Scenario: Adding new cat
    Given valid cat data
    When new cat endpoint is invoked
    Then new cat should be saved

  Scenario: Adding new cat with invalid data
    Given invalid cat data
    When new cat endpoint is invoked
    Then new cat validation error should be returned
