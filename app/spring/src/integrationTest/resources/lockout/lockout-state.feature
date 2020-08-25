Feature: Lockout Policy Maintenance

  Background:
    * url baseUrl + "/lockout-states"

  Scenario: Get state - Error - Identity not found
    Given param channelId = "state-channel"
    And param activityName = "state-activity"
    And param aliasType = "idv-id"
    And param aliasValue = "9427672c-1536-44c1-9044-6db6ee1fdcb7"
    When method GET
    Then status 404
    And match response ==
      """
      {
        "status": 404,
        "title": "Identity not found",
        "message": "idv-id|9427672c-1536-44c1-9044-6db6ee1fdcb7"
      }
      """

  Scenario: Get state - Error - Policy not configured
    * def aliasType = "idv-id"
    * def aliasValue = "e400150e-7073-43d3-b662-e3bc761aa18d"
    Given request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "#(aliasType)", "value": "#(aliasValue)" }
        ]
      }
      """
    And url baseUrl + "/identities"
    And method POST
    And status 201
    And param channelId = "state-channel"
    And param activityName = "state-activity"
    And param aliasType = aliasType
    And param aliasValue = aliasValue
    And url baseUrl + "/lockout-states"
    When method GET
    Then status 500
    And match response ==
      """
      {
        "status": 500,
        "title": "Internal server error",
        "message": "channel: state-channel, activity: state-activity, alias type: idv-id"
      }
      """

