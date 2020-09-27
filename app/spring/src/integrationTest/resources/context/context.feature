Feature: Lockout Policy Maintenance

  Background:
    * url baseUrl + "/contexts"

  Scenario: Create Context - Error - Context policy not configured
    Given request
      """
      {
        "channel": {
          "id": "context-test-channel1",
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
        "message": "channel: context-test-channel1, activity: default-activity, alias types: [credit-card-number]"
      }
      """

  Scenario: Create context - Error - Identity not found
    * def policyId = "18044838-364b-484c-85ab-033e27a4d002"
    Given request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 1,
          "channelId": "context-test-channel2",
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
          "id": "context-test-channel2",
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

  Scenario: Create context - Error - Lockout policy not configured
    * def policyId = "c05920f8-5ebe-469e-a0a3-c7e30cc8b7f1"
    Given request
      """
      {
        "key": {
          "id": "#(policyId)",
          "priority": 1,
          "channelId": "context-test-channel3",
          "type": "channel"
        },
        "sequencePolicies": []
      }
      """
    And url baseUrl + "/context-policies"
    And method POST
    And status 201
    And request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4927111111111112" }
        ]
      }
      """
    And url baseUrl + "/identities"
    And method POST
    And status 201
    And url baseUrl + "/contexts"
    And request
      """
      {
        "channel": {
          "id": "context-test-channel3",
          "country": "GB"
        },
        "activity": {
          "name": "default-activity",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4927111111111112"
          }
        ]
      }
      """
    When method POST
    Then status 422
    And match response ==
      """
      {
        "status": 422,
        "title": "Lockout policy not configured",
        "message": "Lockout policy not configured for channel, activity and alias combination",
        "meta": {
          "activityName":"default-activity",
          "aliasTypes":["credit-card-number"],
          "channelId":"context-test-channel3"
        }
      }
      """

  Scenario: Create context - Success - Otp method returned
    Given url baseUrl + "/context-policies"
    * def contextPolicyId = "be2c6c3b-5347-4337-9194-cc5c803112a6"
    And request
      """
      {
        "key": {
          "id": "#(contextPolicyId)",
          "priority": 1,
          "channelId": "context-test-channel4",
          "type": "channel"
        },
        "sequencePolicies": [
        {
            "name": "one-time-passcode",
            "methodPolicies": [
              {
                "name": "one-time-passcode",
                "methodConfig": {
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
                        "minDaysSinceSwap": 6,
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
    And url baseUrl + "/identities"
    And request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4927111111111113" }
        ],
        "phoneNumbers": [
          { "value": "+4407808247749" },
          { "value": "+4407808247748" },
          { "value": "+4407808247747" },
          { "value": "+4407808247746" },
          { "value": "+4407808247745" },
          { "value": "+4407808247744" },
          { "value": "+4407808247743" }
        ]
      }
      """
    And method POST
    And status 201
    And url baseUrl + "/lockout-policies"
    * def lockoutPolicyId = "9ddbd67e-6310-42aa-abf4-007eb09e8398"
    And request
      """
      {
        "key": {
          "id": "#(lockoutPolicyId)",
          "priority": 1,
          "channelId": "context-test-channel4",
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
    And url baseUrl + "/contexts"
    And request
      """
      {
        "channel": {
          "id": "context-test-channel4",
          "country": "GB"
        },
        "activity": {
          "name": "default-activity",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4927111111111113"
          }
        ]
      }
      """
    When method POST
    Then status 201
    And match response ==
      """
      {
        "id": "#uuid",
        "created": "#notnull",
        "expiry": "#notnull",
        "request": {
          "initial": {
            "channel": {
              "id": "context-test-channel4",
              "country": "GB"
            },
            "aliases": [
              {
                "type": "credit-card-number",
                "value": "4927111111111113"
              }
            ],
            "activity": {
              "name": "default-activity",
              "timestamp": "2020-09-27T06:56:47.522Z"
            }
          },
          "policy": {
            "key": {
              "id": "#(contextPolicyId)",
              "priority": 1,
              "channelId": "context-test-channel4",
              "type": "channel"
            },
            "sequencePolicies": [
              {
                "name": "one-time-passcode",
                "methodPolicies": [
                  {
                    "methodConfig": {
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
                            "minDaysSinceSwap": 6,
                            "async": false
                          }
                        }
                      }
                    ],
                    "name": "one-time-passcode"
                  }
                ]
              }
            ]
          },
          "identity": {
            "idvId": "#uuid",
            "country": "GB",
            "aliases": [
              {
                "type": "credit-card-number",
                "value": "4927111111111113"
              },
              {
                "type": "idv-id",
                "value": "#uuid"
              }
            ],
            "phoneNumbers": [
              { "value": "+4407808247749" },
              { "value": "+4407808247748" },
              { "value": "+4407808247747" },
              { "value": "+4407808247746" },
              { "value": "+4407808247745" },
              { "value": "+4407808247744" },
              { "value": "+4407808247743" }
            ]
          }
        },
        "sequences": [
          {
            "name": "one-time-passcode",
            "methods": [
              {
                "name": "one-time-passcode",
                "deliveryMethods": [
                  {
                    "id": "#uuid",
                    "type": "sms",
                    "value": "+447808247749",
                    "eligibility": {
                      "reason": "sim swap status failure is not allowed",
                      "eligible": false,
                      "complete": true
                    }
                  },
                  {
                    "id": "#uuid",
                    "type": "sms",
                    "value": "+447808247748",
                    "eligibility": {
                      "reason": "sim swap status unknown is not allowed",
                      "eligible": false,
                      "complete": true
                    }
                  },
                  {
                    "id": "#uuid",
                    "type": "sms",
                    "value": "+447808247747",
                    "eligibility": {
                      "reason": "sim swap status timeout is not allowed",
                      "eligible": false,
                      "complete": true
                    }
                  },
                  {
                    "id": "#uuid",
                    "type": "sms",
                    "value": "+447808247746",
                    "eligibility": {
                      "reason": "#notnull",
                      "eligible": false,
                      "complete": true
                    }
                  },
                  {
                    "id": "#uuid",
                    "type": "sms",
                    "value": "+447808247745",
                    "eligibility": {
                      "reason": "sim swap status timeout not acceptable",
                      "eligible": false,
                      "complete": false
                    }
                  },
                  {
                    "id": "#uuid",
                    "type": "sms",
                    "value": "+447808247744",
                    "eligibility": {
                      "reason": "sim swap status unknown not acceptable",
                      "eligible": false,
                      "complete": true
                    }
                  },
                  {
                    "id": "#uuid",
                    "type": "sms",
                    "value": "+447808247743",
                    "eligibility": {
                      "eligible": true,
                      "complete": true
                    }
                  }
                ],
                "config": {
                  "maxNumberOfAttempts": 3,
                  "duration": 300000,
                  "passcodeConfig": {
                    "length": 8,
                    "duration": 120000,
                    "maxNumberOfDeliveries": 2
                  }
                },
                "successful": false,
                "complete": false,
                "eligible": true
              }
            ],
            "duration": 300000,
            "eligible": true,
            "successful": false,
            "complete": false
          }
        ],
        "eligible": true,
        "successful": false,
        "complete": false
      }
      """