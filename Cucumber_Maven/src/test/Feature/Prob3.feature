Feature: Validate UserPage Scenarios
  @getCall
  Scenario Outline: Do Mobile Page GET REST Request validation
    Given user does get call with "<url>"
    When user see status code for restful get "<status_code>"
    And user validates the restful response "<id>"
    Then user name for restful "<name>"

    Examples:
      |url|status_code|id|name|
      |https://api.restful-api.dev/objects|200|8|Apple Watch Series 8|
#      |https://api.restful-api.dev/objects|200|11|Apple iPad Mini 5th Gen|
#      |https://api.restful-api.dev/objects|200|2|Apple iPhone 12 Mini, 256GB, Blue|