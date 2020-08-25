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
    Then status 422
    And match response ==
      """
      {
        "status": 422,
        "title": "Lockout policy not configured",
        "message": "Lockout policy not configured for channel, activity and alias combination",
        "meta": {
          "aliasType": "idv-id",
          "activityName": "state-activity",
          "channelId": "state-channel"
        }
      }
      """

