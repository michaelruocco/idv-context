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
        ],
        "emailAddresses": [
          "joe.bloggs@hotmail.co.uk"
        ]
      }
      """
    And url baseUrl + "/identities"
    And method POST
    And status 201
    * def policyId = "9c69b143-299e-486d-89b8-0ac6ff91b0b8"
    And request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 1,
          "channelId": "#(channelId)",
          "type": "channel"
        },
        "sequencePolicies": [
          {
            "name": "one-time-passcode",
            "methodPolicies": [
              {
                "name": "one-time-passcode",
                "config": {
                  "maxNumberOfAttempts": 3,
                  "duration": 300000,
                  "passcodeConfig": {
                    "length": 8,
                    "duration": 120000,
                    "maxNumberOfDeliveries": 2
                  }
                },
                "deliveryMethodConfigs": [
                  {
                    "type": "email"
                  }
                ]
              }
            ]
          }
        ]
      }
      """
    And url baseUrl + "/context-policies"
    And method POST
    And status 201
    And request
      """
      {
        "channel": {
          "id": "#(channelId)",
          "country": "GB"
        },
        "activity": {
          "name": "#(activityName)",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "aliases": [
          {
            "type": "#(aliasType)",
            "value": "#(aliasValue)"
          }
        ]
      }
      """
    And url baseUrl + "/contexts"
    And method POST
    And status 201
    * def contextId = response.id
    And request
      """
      {
        "contextId": "#(contextId)",
        "result": {
          "methodName": "one-time-passcode",
          "verificationId": "b9808fe7-26ec-415e-88db-e1bfb5d1380e",
          "timestamp": "2020-09-27T06:57:47.522Z",
          "successful": false
        }
      }
      """
    And url baseUrl + "/contexts/results"
    And method PATCH
    And status 200
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
            "aliases": [
              {
                "type": "#(aliasType)",
                "value": "#(aliasValue)"
              }
            ],
            "activityName": "lockout-state-activity",
            "methodName": "one-time-passcode",
            "contextId": "#uuid",
            "channelId": "#(channelId)",
            "verificationId": "b9808fe7-26ec-415e-88db-e1bfb5d1380e",
            "timestamp": "2020-09-27T06:57:47.522Z",
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
        ],
        "emailAddresses": [
          "joe.bloggs@hotmail.co.uk"
        ]
      }
      """
    And url baseUrl + "/identities"
    And method POST
    And status 201
    * def policyId = "9c69b143-299e-486d-89b8-0ac6ff91b0b8"
    And request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 1,
          "channelId": "#(channelId)",
          "type": "channel"
        },
        "sequencePolicies": [
          {
            "name": "one-time-passcode",
            "methodPolicies": [
              {
                "name": "one-time-passcode",
                "config": {
                  "maxNumberOfAttempts": 3,
                  "duration": 300000,
                  "passcodeConfig": {
                    "length": 8,
                    "duration": 120000,
                    "maxNumberOfDeliveries": 2
                  }
                },
                "deliveryMethodConfigs": [
                  {
                    "type": "email"
                  }
                ]
              }
            ]
          }
        ]
      }
      """
    And url baseUrl + "/context-policies"
    And method POST
    And status 201
    And request
      """
      {
        "channel": {
          "id": "#(channelId)",
          "country": "GB"
        },
        "activity": {
          "name": "#(activityName)",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "aliases": [
          {
            "type": "#(aliasType)",
            "value": "#(aliasValue)"
          }
        ]
      }
      """
    And url baseUrl + "/contexts"
    And method POST
    And status 201
    * def contextId = response.id
    And request
      """
      {
        "contextId": "#(contextId)",
        "result": {
          "methodName": "one-time-passcode",
          "verificationId": "b9808fe7-26ec-415e-88db-e1bfb5d1380e",
          "timestamp": "2020-09-27T06:57:47.522Z",
          "successful": false
        }
      }
      """
    And url baseUrl + "/contexts/results"
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
    And url baseUrl + "/lockout-states"
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