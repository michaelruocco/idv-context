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
          "aliasType": "#(aliasType)",
          "activityName": "state-activity",
          "channelId": "state-channel"
        }
      }
      """

  Scenario: Get lockout state - Success - Lockout state returned
    * def aliasType = "idv-id"
    * def aliasValue = "2db586e0-7dfc-4ddf-99c2-d0481a5c540b"
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
    * def policyId = "89c2c644-23b2-48d7-85e1-7d78d98492d3"
    * def channelId = "state-channel-1"
    And request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 1,
          "channelId": "#(channelId)",
          "type": "channel"
        },
        "stateCalculator": {
          "type": "hard-lockout",
          "maxNumberOfAttempts": 2
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """
    And url baseUrl + "/lockout-policies"
    And method POST
    And status 201
    And param channelId = channelId
    And param activityName = "state-activity"
    And param aliasType = aliasType
    And param aliasValue = aliasValue
    And url baseUrl + "/lockout-states"
    When method GET
    Then status 200
    And match response ==
      """
      {
        "id":"#uuid",
        "idvId": {
          "type": "idv-id",
          "value":"#(aliasValue)"
        },
        "numberOfAttemptsRemaining": 2,
        "maxNumberOfAttempts": 2,
        "message": "2 attempts remaining",
        "locked": false,
        "attempts": []
      }
      """

