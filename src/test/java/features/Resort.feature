Feature: CRUD operations for Resort entity

  Scenario: List all resorts
    Given Api url is set
    And a GET request is done against the "resorts" endpoint
    Then a valid status code 200 is received
    And the response list is not empty