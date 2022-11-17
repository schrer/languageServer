Feature: Run requests with missing fields in the body
  Background:
    * url baseUrl

  Scenario: Post empty
    Given path '/compute'
    And request {}
    When method POST
    Then status 500

  Scenario: Post missing program
    Given path '/compute'
    And request {'sourceLang':'text','targetLang':'text'}
    When method POST
    Then status 500

  Scenario: Post missing sourceLang
    Given path '/compute'
    And request {'targetLang':'text',program:'blabla'}
    When method POST
    Then status 500

  Scenario: Post missing targetLang
    Given path '/compute'
    And request {'sourceLang':'text',program:'blabla'}
    When method POST
    Then status 500