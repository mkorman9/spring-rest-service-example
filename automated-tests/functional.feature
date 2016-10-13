Feature: Adding cats
  Scenario: Adding new cat
    Given: application is running
    when new cat is added
    then it should be remembered

  Scenario: Adding new cat with invalid data
    Given: application is running
    when new cat request is sent with invalid data
    then new validation error should be returned

Feature: Deleting cats
  Scenario: Deleting existing cat
    Given: application is running
    when existing cat is deleted
    then it should not exist in registry anymore

  Scenario: Deleting non-existing cat
    Given: application is running
    when request of deleting cat with non-existing id is sent
    then deleting error about non-existing cat should be returned

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
    then updating error about invalid data should be returned
