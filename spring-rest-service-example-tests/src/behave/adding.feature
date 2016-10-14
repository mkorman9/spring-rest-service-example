Feature: Adding cats
  Scenario: Adding new cat
    Given: application is running
    when new cat is added
    then it should be remembered

  Scenario: Adding new cat with invalid data
    Given: application is running
    when new cat request is sent with invalid data
    then new validation error should be returned
