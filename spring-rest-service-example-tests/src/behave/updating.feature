Feature: Updating cats
  Scenario: Updating existing cat
    Given application is running
    When existing cat is updated with new data
    Then cat should be updated in registry

  Scenario: Updating non-existing cat
    Given application is running
    When request of updating data of non-existing cat is sent
    Then updating error about non-existing cat should be returned

  Scenario: Updating cat with invalid data
    Given application is running
    When request of updating data in invalid format is sent
    Then updating error about invalid data should be returned and data should not be modified
