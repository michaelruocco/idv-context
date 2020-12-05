Feature: Context Policy Maintenance

  Background:
    * url baseUrl + "/v1/context-policies"

  Scenario: Get policy - Error - Policy not found
    * def policyId = "09ff6196-dde0-4b8d-a526-f7c5b646bf9a"
    Given path "/" + policyId
    And header correlation-id = "3a5020ef-2388-47b9-a97b-1f5eedef299f"
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
        ],
        "maskSensitiveData": false
      }
      """
    And header correlation-id = "d4dfde15-363b-4284-b247-2108e9eab90e"
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
        ],
        "maskSensitiveData": false
      }
      """
    And match responseHeaders.Location contains baseUrl + "/v1/context-policies/" + policyId

  Scenario: Create + Get policy - Success - Create channel/activity policy, get by id
    * def policyId = "60f82e7a-cead-4129-934f-57c8f36db48f"
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
        ],
        "maskSensitiveData": false
      }
      """
    And header correlation-id = "a1a2f81a-1a56-4b58-8a63-409653c0ae04"
    And method POST
    And status 201
    And path "/" + policyId
    And header correlation-id = "5f9ac453-53ce-4fd4-9ea8-99bf8b0292fc"
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
        ],
        "maskSensitiveData": false
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
        "sequencePolicies": [],
        "maskSensitiveData": false
      }
      """
    And header correlation-id = "97438084-d6e5-4f97-a270-81445644aae3"
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
        ],
        "maskSensitiveData": false
      }
      """
    And header correlation-id = "104b4922-50b4-4c73-8118-be495f5f3409"
    And method POST
    And status 201
    And param channelId = "default-channel3"
    And param activityName = "default-activity"
    And header correlation-id = "792e8ed3-4448-46c2-b1d4-c1185cca1fe8"
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
          "sequencePolicies": [],
          "maskSensitiveData": false
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
          ],
          "maskSensitiveData": false
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
        "sequencePolicies": [],
        "maskSensitiveData": false
      }
      """
    And header correlation-id = "006667cc-7afd-4b91-b62d-5c8de7b8244c"
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
        ],
        "maskSensitiveData": false
      }
      """
    And header correlation-id = "00745985-7bae-487c-996f-3a4ee5563cf2"
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
        ],
        "maskSensitiveData": false
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
        "sequencePolicies": [],
        "maskSensitiveData": false
      }
      """
    And header correlation-id = "98901873-6018-4dde-b4fa-3e839555a479"
    And method POST
    And status 201
    And path "/" + policyId
    And header correlation-id = "f9298cc6-30e5-4e6b-81fd-c2a0e738f321"
    When method DELETE
    Then status 204
    And path "/" + policyId
    And header correlation-id = "5bef4a50-829b-4477-88ae-59e2cc4a98e8"
    And method GET
    And status 404