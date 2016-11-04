Feature: Deleting cats
  Scenario: Deleting existing cat
    Given application is running
    When existing cat is deleted
    Then it should not exist in registry anymore

  Scenario: Deleting non-existing cat
    Given application is running
    When request of deleting cat with non-existing id is sent
    Then deleting error about non-existing cat should be returned
