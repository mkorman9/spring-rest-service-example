Feature: REST API exposing
    Scenario: Testing valid API endpoint
      Given: application is running
      when sending request to valid endpoint
      then status 200 should be returned with valid json response

    Scenario: Testing invalid API endpoint
      Given: application is running
      when sending request to not-existing endpoint
      then status 404 should be returned with valid json response
