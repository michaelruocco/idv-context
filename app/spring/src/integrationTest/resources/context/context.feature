Feature: Lockout Policy Maintenance

  Background:
    * url baseUrl + "/contexts"

  Scenario: Create Context - Error - Context policy not found
    Given request
      """
      {
        "channel": {
          "id": "default-channel1",
          "country": "GB"
        },
        "activity": {
          "name": "default-activity",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4927111111111111"
          }
        ]
      }
      """
    When method POST
    Then status 500
    And match response ==
      """
      {
        "status": 500,
        "title": "Internal server error",
        "message": "channel: default-channel1, activity: default-activity, alias types: [credit-card-number]"
      }
      """

  Scenario: Create context - Error - Identity not found
    * def policyId = "19e07fd9-236d-4418-ae40-19a53a3c19a3"
    Given request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 1,
          "channelId": "default-channel",
          "type": "channel"
        },
        "sequencePolicies": []
      }
      """
    And url baseUrl + "/context-policies"
    And method POST
    And status 201
    And url baseUrl + "/contexts"
    And request
      """
      {
        "channel": {
          "id": "default-channel",
          "country": "GB"
        },
        "activity": {
          "name": "default-activity",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4927111111111111"
          }
        ]
      }
      """
    When method POST
    Then status 404
    And match response ==
      """
      {
        "status": 404,
        "title": "Identity not found",
        "message": "credit-card-number|4927111111111111"
      }
      """