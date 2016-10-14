Feature: Deleting cats
  Scenario: Deleting existing cat
    Given: application is running
    when existing cat is deleted
    then it should not exist in registry anymore

  Scenario: Deleting non-existing cat
    Given: application is running
    when request of deleting cat with non-existing id is sent
    then deleting error about non-existing cat should be returned
