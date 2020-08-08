Feature: Lockout Policy Maintenance

  Background:
    * url baseUrl + "/lockout-policies"

  Scenario: Get policy - Error - Policy not found
    * def policyId = "fcdb814c-dba4-4b2f-a84e-a97d3593b97b"
    Given path "/" + policyId
    When method GET
    Then status 500
    And match response ==
      """
      {
        "status": 500,
        "title": "Internal server error",
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
    And match responseHeaders.Location contains baseUrl + "/lockout-policies/" + policyId

  Scenario: Create + Get policy - Success - Create channel/activity policy, get by id
    * def policyId = "89c2c644-23b2-48d7-85e1-7d78d98492d3"
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
    And method POST
    And status 201
    And path "/" + policyId
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
    And method POST
    And status 201
    And param channelId = "my-channel"
    And param activityName = "my-activity"
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
    And method POST
    And status 201
    And path "/" + policyId
    When method DELETE
    Then status 204
    And path "/" + policyId
    And method GET
    And status 500