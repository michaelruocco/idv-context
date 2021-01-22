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
          "includeAttemptsPolicy": {
            "type": "all-attempts"
          },
          "maxNumberOfAttempts": 2
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """
    And url baseUrl + "/v1/lockout-policies"
    And header correlation-id = "b2656064-281e-471d-9144-aac0a376c4ee"
    And method POST
    And status 201

    * url baseUrl + "/v1/lockout-states"

  Scenario: Get state - Error - Identity not found
    Given param channelId = channelId
    And param activityName = activityName
    And param aliasType = "idv-id"
    And param aliasValue = "9427672c-1536-44c1-9044-6db6ee1fdcb7"
    And header correlation-id = "e5b46092-795f-4567-8fce-dfc91cabb27a"
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
    And url baseUrl + "/v1/identities"
    And header correlation-id = "7fde50fc-3173-4f73-9eab-ed5d1138fe3b"
    And method POST
    And status 201
    And param channelId = "no-lockout-policy-channel"
    And param activityName = "no-lockout-policy-activity"
    And param aliasType = aliasType
    And param aliasValue = aliasValue
    And url baseUrl + "/v1/lockout-states"
    And header correlation-id = "2e0aa059-8fb9-44f7-a5c1-851ea1b3ebae"
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
    And url baseUrl + "/v1/identities"
    And header correlation-id = "a06f3329-ce24-4cec-bf22-8c90566ddfa9"
    And method POST
    And status 201
    And param channelId = channelId
    And param activityName = activityName
    And param aliasType = aliasType
    And param aliasValue = aliasValue
    And url baseUrl + "/v1/lockout-states"
    And header correlation-id = "f168caa9-c0d3-4e6b-a5e2-7abe4204ffb3"
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
    And url baseUrl + "/v1/identities"
    And header correlation-id = "ac5071b5-752b-4e25-a5a3-765f13e6f7e9"
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
        ],
        "protectSensitiveData": false
      }
      """
    And url baseUrl + "/v1/context-policies"
    And header correlation-id = "88e7aca3-d00b-4999-b233-b307718b5b51"
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
    And url baseUrl + "/v1/contexts"
    And header channel-id = channelId
    And header correlation-id = "4c6db37e-010c-46ec-b9e5-1e6348822af1"
    And method POST
    And status 201
    * def contextId = response.id
    And request
      """
      {
        "contextId": "#(contextId)",
        "methodName": "one-time-passcode"
      }
      """
    And url baseUrl + "/v1/contexts/verifications"
    And header channel-id = channelId
    And header correlation-id = "66f03c6b-73f5-4342-ac23-330851265ce5"
    And method POST
    And status 201
    * def verificationId = response.id
    And request
      """
      {
        "id": "#(verificationId)",
        "contextId": "#(contextId)",
        "successful": false
      }
      """
    And url baseUrl + "/v1/contexts/verifications"
    And header channel-id = channelId
    And header correlation-id = "f6855884-ffc2-4966-944d-1debc7a1cc0a"
    And method PATCH
    And status 200
    And param channelId = channelId
    And param activityName = activityName
    And param aliasType = aliasType
    And param aliasValue = aliasValue
    And url baseUrl + "/v1/lockout-states"
    And header correlation-id = "34f1f806-ec0a-4ee2-b43d-1ca3fc26f6ea"
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
            "contextId": "#(contextId)",
            "channelId": "#(channelId)",
            "verificationId": "#(verificationId)",
            "timestamp": "#notnull",
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
    And url baseUrl + "/v1/identities"
    And header correlation-id = "96d39cda-7956-4974-9824-218903ad2dc9"
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
        ],
        "protectSensitiveData": false
      }
      """
    And url baseUrl + "/v1/context-policies"
    And header correlation-id = "d42635a2-4f04-468f-9977-14500385b2eb"
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
    And url baseUrl + "/v1/contexts"
    And header channel-id = channelId
    And header correlation-id = "7be2c128-90a2-42c7-bf49-587ab6561611"
    And method POST
    And status 201
    * def contextId = response.id
    And request
      """
      {
        "contextId": "#(contextId)",
        "methodName": "one-time-passcode"
      }
      """
    And url baseUrl + "/v1/contexts/verifications"
    And header channel-id = channelId
    And header correlation-id = "41b9ed5d-ecd7-4b2a-8652-69471728f3b6"
    And method POST
    And status 201
    * def verificationId = response.id
    And request
      """
      {
        "id": "#(verificationId)",
        "contextId": "#(contextId)",
        "successful": false
      }
      """
    And url baseUrl + "/v1/contexts/verifications"
    And header channel-id = channelId
    And header correlation-id = "bf5e1e70-1aa8-4245-9fe4-2e86ae5d55d0"
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
    And url baseUrl + "/v1/lockout-states"
    And header correlation-id = "15629410-86a3-4eef-9fde-8608df45700c"
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