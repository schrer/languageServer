Feature: Run text to text evaluation with the identity option
  Background:
    * url baseUrl

  Scenario: Post empty
    Given path '/compute'
    And request {}
    When method POST
    Then status 500