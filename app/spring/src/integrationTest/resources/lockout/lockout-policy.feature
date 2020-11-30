Feature: Lockout Policy Maintenance

  Background:
    * url baseUrl + "/v1/lockout-policies"

  Scenario: Get policy - Error - Policy not found
    * def policyId = "fcdb814c-dba4-4b2f-a84e-a97d3593b97b"
    Given path "/" + policyId
    And header correlation-id = "cdc408c3-bc12-438c-9d14-f9286a99696b"
    When method GET
    Then status 404
    And match response ==
      """
      {
        "status": 404,
        "title": "Policy not found",
        "message": "#(policyId)"
      }
      """

  Scenario: Create policy - Success - Create channel policy
    * def policyId = "89c2c644-23b2-48d7-85e1-7d78d98492d3"
    Given request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 1,
          "channelId": "default-channel",
          "type": "channel"
        },
        "stateCalculator": {
          "maxNumberOfAttempts": 5,
          "type": "hard-lockout"
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """
    And header correlation-id = "ca28fda0-cfca-4624-b927-93eee65557c2"
    When method POST
    Then status 201
    And match response ==
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 1,
          "channelId": "default-channel",
          "type": "channel"
        },
        "stateCalculator": {
          "maxNumberOfAttempts": 5,
          "type": "hard-lockout"
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """
    And match responseHeaders.Location contains baseUrl + "/v1/lockout-policies/" + policyId

  Scenario: Create + Get policy - Success - Create channel/activity policy, get by id
    * def policyId = "796910ee-692d-4610-91b8-7bcdd34f9e22"
    Given request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 99,
          "channelId": "default-channel",
          "activityNames": [
            "default-activity"
          ],
          "type": "channel-activity"
        },
        "stateCalculator": {
          "maxNumberOfAttempts": 5,
          "type": "hard-lockout"
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """
    And header correlation-id = "4642e333-8543-4b3a-ac35-4a5362aa101d"
    And method POST
    And status 201
    And path "/" + policyId
    And header correlation-id = "a88eb266-75ae-4b02-a72d-2e5fed45fc5a"
    When method GET
    Then status 200
    And match response ==
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 99,
          "channelId": "default-channel",
          "activityNames": [
            "default-activity"
          ],
          "type": "channel-activity"
        },
        "stateCalculator": {
          "maxNumberOfAttempts": 5,
          "type": "hard-lockout"
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """

  Scenario: Create + Get multiple policies - Success - Create and get multiple policies
    * def policyId1 = "b7504d4e-c0b7-4785-8d05-cd852ff5329b"
    Given request
      """
      {
        "key": {
          "id": "#(policyId1)",
          "priority": 99,
          "channelId": "my-channel",
          "activityNames": [
            "my-activity"
          ],
          "type": "channel-activity"
        },
        "stateCalculator": {
          "type": "non-locking"
        },
        "recordAttemptPolicy": {
          "type": "never-record"
        }
      }
      """
    And header correlation-id = "2f2e2e01-3c35-4388-a4d0-7a2f96cfcad9"
    And method POST
    And status 201
    * def policyId2 = "4382f604-bdc7-48b9-b229-44350c5fa27c"
    And request
      """
      {
        "key": {
          "id": "#(policyId2)",
          "priority": 1,
          "channelId": "my-channel",
          "type": "channel"
        },
        "stateCalculator": {
          "type": "recurring-soft-lockout",
          "interval": {
            "numberOfAttempts": 1,
            "duration": 60000
          }
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """
    And header correlation-id = "aa7478de-2a0e-44f1-bf6a-e5732cc682d2"
    And method POST
    And status 201
    And param channelId = "my-channel"
    And param activityName = "my-activity"
    And header correlation-id = "d7234b1a-9afc-4045-9b94-21108fc78fbf"
    When method GET
    Then status 200
    And match response ==
      """
      [
        {
          "key": {
            "id": "#(policyId1)",
            "priority": 99,
            "channelId": "my-channel",
            "activityNames": [
              "my-activity"
            ],
            "type": "channel-activity"
          },
          "stateCalculator": {
            "type": "non-locking"
          },
          "recordAttemptPolicy": {
            "type": "never-record"
          }
        },
        {
          "key": {
            "id": "#(policyId2)",
            "priority": 1,
            "channelId": "my-channel",
            "type": "channel"
          },
          "stateCalculator": {
            "type": "recurring-soft-lockout",
            "interval": {
              "numberOfAttempts": 1,
              "duration": 60000
            }
          },
          "recordAttemptPolicy": {
            "type": "always-record"
          }
        }
      ]
      """

  Scenario: Update policy - Success - Create policy then update
    * def policyId = "be9f971a-92ff-4730-8cda-b298609af0d0"
    Given request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 1,
          "channelId": "default-channel",
          "type": "channel"
        },
        "stateCalculator": {
          "maxNumberOfAttempts": 5,
          "type": "hard-lockout"
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """
    And header correlation-id = "4a08c1e6-61d5-43c8-9088-e401eb4a552a"
    And method POST
    And status 201
    And request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 20,
          "channelId": "default-channel",
          "activityNames": [ "default-activity" ],
          "aliasTypes": [ "default-alias" ],
          "type": "channel-activity-alias"
        },
        "stateCalculator": {
          "type": "soft-lockout",
          "intervals": [
            {
              "numberOfAttempts": 1,
              "duration": 60000
            },
            {
              "numberOfAttempts": 2,
              "duration": 120000
            }
          ]
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """
    And header correlation-id = "6e778783-56d9-41b7-91a1-01de3f7776ce"
    When method PUT
    And status 200
    And match response ==
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 20,
          "channelId": "default-channel",
          "activityNames": [ "default-activity" ],
          "aliasTypes": [ "default-alias" ],
          "type": "channel-activity-alias"
        },
        "stateCalculator": {
          "type": "soft-lockout",
          "intervals": [
            {
              "numberOfAttempts": 1,
              "duration": 60000
            },
            {
              "numberOfAttempts": 2,
              "duration": 120000
            }
          ]
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """

  Scenario: Delete policy - Success - Create policy then delete
    * def policyId = "e0ec06c9-dd62-43bd-991f-663b562404e1"
    Given request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 1,
          "channelId": "default-channel",
          "type": "channel"
        },
        "stateCalculator": {
          "maxNumberOfAttempts": 5,
          "type": "hard-lockout"
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """
    And header correlation-id = "000dced3-a929-4b65-b25a-ac350bef27da"
    And method POST
    And status 201
    And path "/" + policyId
    And header correlation-id = "36491e7f-adca-4939-8975-4cbba8f8d936"
    When method DELETE
    Then status 204
    And path "/" + policyId
    And header correlation-id = "7ed8193e-6d4e-4127-85ac-9555d103f6f3"
    And method GET
    And status 404