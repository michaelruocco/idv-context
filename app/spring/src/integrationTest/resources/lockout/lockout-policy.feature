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