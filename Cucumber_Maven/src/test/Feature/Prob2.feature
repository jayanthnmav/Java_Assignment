Feature: Validate UserPage Scenarios
  @postCall
  Scenario Outline: Do Mobile Page POST REST Request validation
    Given user name for restful "<name>"
    Then user creates request body for restful postcall
    When user send request body post call with "<url>"
    Then user see status code for restful post "<status_code>"
    And user validates the restful response year as "<responseYear>"
    And user validates the restful response price as "<responsePrice>"

    Examples:
      |name|url|status_code|responseYear|responsePrice|
      |  Apple MacBook Pro 16  |https://api.restful-api.dev/objects|200|2019|1849.99|