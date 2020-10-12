Feature: Lockout State Requests

  Background:
    * def policyId = "7226cc2e-5b56-4405-a525-2111a7676c57"
    * def channelId = "lockout-state-channel"
    * def activityName = "lockout-state-activity"
    Given request
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

    * url baseUrl + "/lockout-states"

  Scenario: Get state - Error - Identity not found
    Given param channelId = channelId
    And param activityName = activityName
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
    And param channelId = "no-lockout-policy-channel"
    And param activityName = "no-lockout-policy-activity"
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
          "aliasTypes": [
            "#(aliasType)"
          ],
          "activityName": "no-lockout-policy-activity",
          "channelId": "no-lockout-policy-channel"
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
    And param channelId = channelId
    And param activityName = activityName
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

  Scenario: Record attempt - Success - Unsuccessful attempt recorded
    * def aliasType = "idv-id"
    * def aliasValue = "21aec930-49e3-42fc-918f-8f98ba3333d2"
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
    * def contextId = "92cc7f98-8927-4222-9601-d7a166095061"
    * def verificationId = "b316d7b8-68e5-4f9c-8251-a37520237f82"
    And request
      """
      {
        "sequenceComplete": false,
        "methodComplete": false,
        "attempt": {
          "channelId": "#(channelId)",
          "activityName": "#(activityName)",
          "aliases": [
            {
              "type": "#(aliasType)",
              "value": "#(aliasValue)"
            }
          ],
          "idvId": {
            "type": "idv-id",
            "value": "#(aliasValue)"
          },
          "contextId": "#(contextId)",
          "methodName": "default-method",
          "verificationId": "#(verificationId)",
          "timestamp":"2020-08-27T08:15:58.977Z",
          "successful": false
        }
      }
      """
    And url baseUrl + "/lockout-states"
    When method PATCH
    Then status 200
    And match response ==
      """
      {
        "id":"#uuid",
        "idvId": {
          "type": "idv-id",
          "value":"#(aliasValue)"
        },
        "numberOfAttemptsRemaining": 1,
        "maxNumberOfAttempts": 2,
        "message": "1 attempts remaining",
        "locked": false,
        "attempts": [
          {
            "idvId": {
              "type": "idv-id",
              "value": "#(aliasValue)"
            },
            "activityName":"#(activityName)",
            "aliases": [
              {
                "type":"idv-id",
                "value":"#(aliasValue)"
              }
            ],
            "methodName": "default-method",
            "contextId": "#(contextId)",
            "channelId": "#(channelId)",
            "verificationId": "#(verificationId)",
            "timestamp": "2020-08-27T08:15:58.977Z",
            "successful": false
          }
        ]
      }
      """

  Scenario: Reset state - Success - Lockout state reset after failure
    * def aliasType = "idv-id"
    * def aliasValue = "431f45ed-aa8a-4732-a0ff-fee517fde7f6"
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
    * def contextId = "a1918808-4d17-42b7-9b20-8dddeed07f13"
    * def verificationId = "1b299b59-d846-4607-babf-fa26b8a0b44c"
    And request
      """
      {
        "sequenceComplete": false,
        "methodComplete": false,
        "attempt": {
          "channelId": "#(channelId)",
          "activityName": "#(activityName)",
          "aliases": [
            {
              "type": "#(aliasType)",
              "value": "#(aliasValue)"
            }
          ],
          "idvId": {
            "type": "idv-id",
            "value": "#(aliasValue)"
          },
          "contextId": "#(contextId)",
          "methodName": "default-method",
          "verificationId": "#(verificationId)",
          "timestamp":"2020-08-27T08:15:58.977Z",
          "successful": false
        }
      }
      """
    And url baseUrl + "/lockout-states"
    And method PATCH
    And status 200
    And request
      """
      {
        "channelId": "#(channelId)",
        "activityName": "#(activityName)",
        "aliases": [
          {
            "type": "idv-id",
            "value": "#(aliasValue)"
          }
        ]
      }
      """
    When method PUT
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