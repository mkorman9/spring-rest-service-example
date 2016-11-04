Feature: Adding cats
  Scenario: Adding new cat
    Given: valid cat data
    when new cat endpoint is invoked
    then new cat should be saved

  Scenario: Adding new cat with invalid data
    Given: invalid cat data
    when new cat endpoint is invoked
    then new cat validation error should be returned
