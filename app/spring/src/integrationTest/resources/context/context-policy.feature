Feature: Context Policy Maintenance

  Background:
    * url baseUrl + "/context-policies"

  Scenario: Get policy - Error - Policy not found
    * def policyId = "09ff6196-dde0-4b8d-a526-f7c5b646bf9a"
    Given path "/" + policyId
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
    * def policyId = "19e07fd9-236d-4418-ae40-19a53a3c19a3"
    Given request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 1,
          "channelId": "default-channel1",
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
                    "type": "sms",
                    "phoneNumberConfig": {
                      "country": "GB",
                      "allowInternational": false,
                      "lastUpdatedConfig": {
                        "allowUnknown": true,
                        "minDaysSinceUpdate": 5
                      },
                      "simSwapConfig": {
                        "acceptableStatuses": [
                          "success"
                        ],
                        "timeout": 2000,
                        "minDaysSinceSwap": 5,
                        "async": false
                      }
                    }
                  }
                ]
              }
            ]
          }
        ]
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
          "channelId": "default-channel1",
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
                    "type": "sms",
                    "phoneNumberConfig": {
                      "country": "GB",
                      "allowInternational": false,
                      "lastUpdatedConfig": {
                        "allowUnknown": true,
                        "minDaysSinceUpdate": 5
                      },
                      "simSwapConfig": {
                        "acceptableStatuses": [
                          "success"
                        ],
                        "timeout": 2000,
                        "minDaysSinceSwap": 5,
                        "async": false
                      }
                    }
                  }
                ]
              }
            ]
          }
        ]
      }
      """
    And match responseHeaders.Location contains baseUrl + "/context-policies/" + policyId

  Scenario: Create + Get policy - Success - Create channel/activity policy, get by id
    * def policyId = "a347cb39-6ef8-4c80-9b6d-31d6b5f1953b"
    Given request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 99,
          "channelId": "default-channel2",
          "activityNames": [
            "default-activity"
          ],
          "type": "channel-activity"
        },
        "sequencePolicies": [
          {
            "name": "one-time-passcode",
            "methodPolicies": [
              {
                "name": "one-time-passcode",
                "config": {
                  "maxNumberOfAttempts": 2,
                  "duration": 300000,
                  "passcodeConfig": {
                    "length": 6,
                    "duration": 100000,
                    "maxNumberOfDeliveries": 3
                  }
                },
                "deliveryMethodConfigs": [
                  {
                    "type": "voice",
                    "phoneNumberConfig": {
                      "country": "GB",
                      "allowInternational": false,
                      "lastUpdatedConfig": {
                        "allowUnknown": false,
                        "minDaysSinceUpdate": 3
                      },
                      "simSwapConfig": {
                        "acceptableStatuses": [
                          "success",
                          "timeout"
                        ],
                        "timeout": 1500,
                        "minDaysSinceSwap": 4,
                        "async": false
                      }
                    }
                  }
                ]
              }
            ]
          }
        ]
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
          "channelId": "default-channel2",
          "activityNames": [
            "default-activity"
          ],
          "type": "channel-activity"
        },
        "sequencePolicies": [
          {
            "name": "one-time-passcode",
            "methodPolicies": [
              {
                "name": "one-time-passcode",
                "config": {
                  "maxNumberOfAttempts": 2,
                  "duration": 300000,
                  "passcodeConfig": {
                    "length": 6,
                    "duration": 100000,
                    "maxNumberOfDeliveries": 3
                  }
                },
                "deliveryMethodConfigs": [
                  {
                    "type": "voice",
                    "phoneNumberConfig": {
                      "country": "GB",
                      "allowInternational": false,
                      "lastUpdatedConfig": {
                        "allowUnknown": false,
                        "minDaysSinceUpdate": 3
                      },
                      "simSwapConfig": {
                        "acceptableStatuses": [
                          "success",
                          "timeout"
                        ],
                        "timeout": 1500,
                        "minDaysSinceSwap": 4,
                        "async": false
                      }
                    }
                  }
                ]
              }
            ]
          }
        ]
      }
      """

  Scenario: Create + Get multiple policies - Success - Create and get multiple policies
    * def policyId1 = "49817c14-4f46-4dc8-b43b-8752ff25282f"
    Given request
      """
      {
        "key": {
          "id": "#(policyId1)",
          "priority": 99,
          "channelId": "default-channel3",
          "activityNames": [
            "default-activity"
          ],
          "type": "channel-activity"
        },
        "sequencePolicies": []
      }
      """
    And method POST
    And status 201
    * def policyId2 = "b65157d0-21c5-4fd4-975a-bb3279775be1"
    And request
      """
      {
        "key": {
          "id": "#(policyId2)",
          "priority": 1,
          "channelId": "default-channel3",
          "type": "channel"
        },
        "sequencePolicies": [
          {
            "name": "one-time-passcode",
            "methodPolicies": [
              {
                "name": "one-time-passcode",
                "config": {
                  "maxNumberOfAttempts": 2,
                  "duration": 300000,
                  "passcodeConfig": {
                    "length": 6,
                    "duration": 100000,
                    "maxNumberOfDeliveries": 3
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
    And method POST
    And status 201
    And param channelId = "default-channel3"
    And param activityName = "default-activity"
    When method GET
    Then status 200
    And match response ==
      """
      [
        {
          "key": {
            "id": "#(policyId1)",
            "priority": 99,
            "channelId": "default-channel3",
            "activityNames": [
              "default-activity"
            ],
            "type": "channel-activity"
          },
          "sequencePolicies": []
        },
        {
          "key": {
            "id": "#(policyId2)",
            "priority": 1,
            "channelId": "default-channel3",
            "type": "channel"
          },
          "sequencePolicies": [
            {
              "name": "one-time-passcode",
              "methodPolicies": [
                {
                  "name": "one-time-passcode",
                  "config": {
                    "maxNumberOfAttempts": 2,
                    "duration": 300000,
                    "passcodeConfig": {
                      "length": 6,
                      "duration": 100000,
                      "maxNumberOfDeliveries": 3
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
      ]
      """

  Scenario: Update policy - Success - Create policy then update
    * def policyId = "14cf933e-cd6e-407c-aad2-d8b108e86dbf"
    Given request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 1,
          "channelId": "default-channel4",
          "type": "channel"
        },
        "sequencePolicies": []
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
          "channelId": "default-channel4",
          "activityNames": [ "default-activity" ],
          "aliasTypes": [ "default-alias" ],
          "type": "channel-activity-alias"
        },
        "sequencePolicies": [
          {
            "name": "one-time-passcode",
            "methodPolicies": [
              {
                "name": "one-time-passcode",
                "config": {
                  "maxNumberOfAttempts": 2,
                  "duration": 300000,
                  "passcodeConfig": {
                    "length": 6,
                    "duration": 100000,
                    "maxNumberOfDeliveries": 3
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
    When method PUT
    And status 200
    And match response ==
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 20,
          "channelId": "default-channel4",
          "activityNames": [ "default-activity" ],
          "aliasTypes": [ "default-alias" ],
          "type": "channel-activity-alias"
        },
        "sequencePolicies": [
          {
            "name": "one-time-passcode",
            "methodPolicies": [
              {
                "name": "one-time-passcode",
                "config": {
                  "maxNumberOfAttempts": 2,
                  "duration": 300000,
                  "passcodeConfig": {
                    "length": 6,
                    "duration": 100000,
                    "maxNumberOfDeliveries": 3
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

  Scenario: Delete policy - Success - Create policy then delete
    * def policyId = "1de27134-c20b-4bf2-b365-c0b445a7a40b"
    Given request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 1,
          "channelId": "default-channel5",
          "type": "channel"
        },
        "sequencePolicies": []
      }
      """
    And method POST
    And status 201
    And path "/" + policyId
    When method DELETE
    Then status 204
    And path "/" + policyId
    And method GET
    And status 404