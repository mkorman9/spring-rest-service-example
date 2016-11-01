Feature: Updating cats
  Scenario: Updating existing cat
    Given: application is running
    when existing cat is updated with new data
    then cat should be updated in registry

  Scenario: Updating non-existing cat
    Given: application is running
    when request of updating data of non-existing cat is sent
    then updating error about non-existing cat should be returned

  Scenario: Updating cat with invalid data
    Given: application is running
    when request of updating data in invalid format is sent
    then updating error about invalid data should be returned and data should not be modified
