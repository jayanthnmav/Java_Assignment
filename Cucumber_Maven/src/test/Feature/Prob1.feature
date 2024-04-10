Feature: Validate UserPage Scenarios

  @userPage
  Scenario Outline: Do User Page GET REST Request validation
    Given the value of Celsius "<degree>"
    Then user create conversion request body
    When user send a postcall with "<url>"
    Then user see status code as "<status_code>"
    And user validates the response Farenheit as "<responseFaren>"
    And user compares Celsius with Farenheit





    Examples:
      | degree | url | status_code | responseFaren |
      |    10    | https://www.w3schools.com/xml/tempconvert.asmx | 200  | 50 |