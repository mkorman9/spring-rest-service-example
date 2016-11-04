Feature: REST API exposing
    Scenario: Testing valid API endpoint
      Given application is running
      When sending request to valid endpoint
      Then status 200 should be returned with valid json response

    Scenario: Testing invalid API endpoint
      Given application is running
      When sending request to not-existing endpoint
      Then status 404 should be returned with valid json response
