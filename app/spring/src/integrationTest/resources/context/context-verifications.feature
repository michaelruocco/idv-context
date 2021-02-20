Feature: Context Verifications

  Scenario: Create Verification - Success - Verification created and retrieved
    * def channelId = "verification-test-channel1"
    * def contextPolicyId = "1aad8338-73af-4663-9325-a9f219b4c4d4"
    Given url baseUrl + "/v1/context-policies"
    And header correlation-id = "133d6a44-2a10-4f85-8fe0-cdbb726d9c40"
    And request
      """
      {
        "key": {
          "id": "#(contextPolicyId)",
          "priority": 1,
          "channelId": "#(channelId)",
          "type": "channel"
        },
        "sequencePolicies": [
        {
            "name": "one-time-passcode",
            "stagePolicies": [
              {
                "type": "all-methods",
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
        ],
        "protectSensitiveData": false
      }
      """
    And method POST
    And status 201
    And url baseUrl + "/v1/identities"
    And header correlation-id = "5d4788d3-a532-4e7c-8292-d82245445849"
    And request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4927111111111131" }
        ],
        "phoneNumbers": [
          { "value": "+4407808247731" }
        ]
      }
      """
    And method POST
    And status 201
    And url baseUrl + "/v1/lockout-policies"
    And header correlation-id = "5483de00-1e7c-42cb-80ed-bcb9c7cd4f40"
    * def lockoutPolicyId = "225aba85-a236-497a-a343-720a9ac392de"
    And request
      """
      {
        "key": {
          "id": "#(lockoutPolicyId)",
          "priority": 1,
          "channelId": "#(channelId)",
          "type": "channel"
        },
        "stateCalculator": {
          "maxNumberOfAttempts": 5,
          "includeAttemptsPolicy": {
            "type": "all-attempts"
          },
          "type": "hard-lockout"
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """
    And method POST
    And status 201
    And header channel-id = channelId
    And header correlation-id = "0b0c4200-a2c7-45a2-ac9a-e31458d1ef8e"
    And url baseUrl + "/v1/contexts"
    And request
      """
      {
        "channel": {
          "id": "#(channelId)",
          "country": "GB"
        },
        "activity": {
          "name": "default-activity",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4927111111111131"
          }
        ]
      }
      """
    And method POST
    And status 201
    And match response ==
      """
      {
        "id": "#uuid",
        "created": "#notnull",
        "expiry": "#notnull",
        "request": {
          "initial": {
            "channel": {
              "id": "#(channelId)",
              "country": "GB"
            },
            "aliases": [
              {
                "type": "credit-card-number",
                "value": "4927111111111131"
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
              "channelId": "#(channelId)",
              "type": "channel"
            },
            "sequencePolicies": [
              {
                "name": "one-time-passcode",
                "stagePolicies": [
                  {
                    "type": "all-methods",
                    "methodPolicies": [
                      {
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
              }
            ],
            "protectSensitiveData": false
          },
          "identity": {
            "idvId": "#uuid",
            "country": "GB",
            "aliases": [
              {
                "type": "credit-card-number",
                "value": "4927111111111131"
              },
              {
                "type": "idv-id",
                "value": "#uuid"
              }
            ],
            "phoneNumbers": [
              { "value": "+4407808247731" }
            ]
          }
        },
        "sequences": [
          {
            "name": "one-time-passcode",
            "stages": [
              {
                "type": "all-methods",
                "methods": [
                  {
                    "name": "one-time-passcode",
                    "deliveryMethods": [
                      {
                        "id": "#uuid",
                        "type": "sms",
                        "value": "+447808247731",
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
                    "eligibility": {
                      "eligible": true
                    }
                  }
                ]
              }
            ],
            "duration": 300000,
            "eligibility": {
              "eligible": true
            }
          }
        ],
        "verifications": [],
        "eligible": true,
        "successful": false,
        "complete": false
      }
      """
    * def contextId = response.id
    And request
      """
      {
        "contextId": "#(contextId)",
        "methodName": "one-time-passcode"
      }
      """
    And header channel-id = channelId
    And header correlation-id = "8b6ce5a9-f960-4919-b0a7-e98a38be6b01"
    And url baseUrl + "/v1/contexts/verifications"
    And method POST
    And status 201
    * def verificationId = response.id
    And url baseUrl + "/v1/contexts/" + contextId + "/verifications/" + verificationId
    And header channel-id = channelId
    And header correlation-id = "021cbad0-9274-4b27-af3a-1632f8b3e0a1"
    And method GET
    And status 200
    And match response ==
      """
      {
        "id": "#(verificationId)",
        "contextId": "#(contextId)",
        "activity": {
          "name": "default-activity",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "methodName": "one-time-passcode",
        "methods": [
          {
            "name": "one-time-passcode",
            "deliveryMethods": [
              {
                "id": "#uuid",
                "type": "sms",
                "value": "+447808247731",
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
            "eligibility": {
              "eligible": true
            }
          }
        ],
        "protectSensitiveData": false,
        "created": "#notnull",
        "expiry": "#notnull",
        "successful": false,
        "complete": false
      }
      """

  Scenario: Complete Verification - Success - Verification created and completed
    * def channelId = "verification-test-channel2"
    * def contextPolicyId = "03ac7483-0006-4d99-b38f-dd33d3004e0a"
    Given url baseUrl + "/v1/context-policies"
    And header correlation-id = "9c5fba22-c930-4195-9def-0b1b86670856"
    And request
      """
      {
        "key": {
          "id": "#(contextPolicyId)",
          "priority": 1,
          "channelId": "#(channelId)",
          "type": "channel"
        },
        "sequencePolicies": [
          {
            "name": "one-time-passcode",
            "stagePolicies": [
              {
                "type": "all-methods",
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
        ],
        "protectSensitiveData": false
      }
      """
    And method POST
    And status 201
    And url baseUrl + "/v1/identities"
    And header correlation-id = "c604f2ae-d229-461c-b7c4-98c5fbd42a83"
    And request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4927111111111132" }
        ],
        "phoneNumbers": [
          { "value": "+4407808247732" }
        ]
      }
      """
    And method POST
    And status 201
    And url baseUrl + "/v1/lockout-policies"
    And header correlation-id = "5aeafa7f-28fb-4120-8f0b-e9e731bfd847"
    * def lockoutPolicyId = "c5a7a9d5-8c9d-4ddd-bd01-727d3075d147"
    And request
      """
      {
        "key": {
          "id": "#(lockoutPolicyId)",
          "priority": 1,
          "channelId": "#(channelId)",
          "type": "channel"
        },
        "stateCalculator": {
          "maxNumberOfAttempts": 5,
          "includeAttemptsPolicy": {
            "type": "all-attempts"
          },
          "type": "hard-lockout"
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """
    And method POST
    And status 201
    And header channel-id = channelId
    And header correlation-id = "5faed8ef-6e1a-492e-af01-7e249c1a3513"
    And url baseUrl + "/v1/contexts"
    And request
      """
      {
        "channel": {
          "id": "#(channelId)",
          "country": "GB"
        },
        "activity": {
          "name": "default-activity",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4927111111111132"
          }
        ]
      }
      """
    And method POST
    And status 201
    And match response ==
      """
      {
        "id": "#uuid",
        "created": "#notnull",
        "expiry": "#notnull",
        "request": {
          "initial": {
            "channel": {
              "id": "#(channelId)",
              "country": "GB"
            },
            "aliases": [
              {
                "type": "credit-card-number",
                "value": "4927111111111132"
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
              "channelId": "#(channelId)",
              "type": "channel"
            },
            "sequencePolicies": [
              {
                "name": "one-time-passcode",
                "stagePolicies": [
                  {
                    "type": "all-methods",
                    "methodPolicies": [
                      {
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
              }
            ],
            "protectSensitiveData": false
          },
          "identity": {
            "idvId": "#uuid",
            "country": "GB",
            "aliases": [
              {
                "type": "credit-card-number",
                "value": "4927111111111132"
              },
              {
                "type": "idv-id",
                "value": "#uuid"
              }
            ],
            "phoneNumbers": [
              { "value": "+4407808247732" }
            ]
          }
        },
        "sequences": [
          {
            "name": "one-time-passcode",
            "stages": [
              {
                "type": "all-methods",
                "methods": [
                  {
                    "name": "one-time-passcode",
                    "deliveryMethods": [
                      {
                        "id": "#uuid",
                        "type": "sms",
                        "value": "+447808247732",
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
                    "eligibility": {
                      "eligible": true
                    }
                  }
                ]
              }
            ],
            "duration": 300000,
            "eligibility": {
              "eligible": true
            }
          }
        ],
        "verifications": [],
        "eligible": true,
        "successful": false,
        "complete": false
      }
      """
    * def contextId = response.id
    And request
      """
      {
        "contextId": "#(contextId)",
        "methodName": "one-time-passcode"
      }
      """
    And header channel-id = channelId
    And header correlation-id = "0509859d-2480-4456-8752-0768e181148c"
    And url baseUrl + "/v1/contexts/verifications"
    And method POST
    And status 201
    * def verificationId = response.id
    And request
      """
      {
        "id": "#(verificationId)",
        "contextId": "#(contextId)",
        "successful": true
      }
      """
    And url baseUrl + "/v1/contexts/verifications"
    And header channel-id = channelId
    And header correlation-id = "192ebba6-c930-4ecd-b91d-e99ecbcbbac8"
    And method PATCH
    And status 200
    And match response ==
      """
      {
        "id": "#(verificationId)",
        "contextId": "#(contextId)",
        "activity": {
          "name": "default-activity",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "methodName": "one-time-passcode",
        "methods": [
          {
            "name": "one-time-passcode",
            "deliveryMethods": [
              {
                "id": "#uuid",
                "type": "sms",
                "value": "+447808247732",
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
            "eligibility": {
              "eligible": true
            }
          }
        ],
        "protectSensitiveData": false,
        "created": "#notnull",
        "expiry": "#notnull",
        "completed": "#notnull",
        "successful": true,
        "complete": true
      }
      """

  Scenario: Create Verification - Error - Should return error if verification method is not next method
    * def channelId = "verification-test-channel3"
    * def contextPolicyId = "2570c9d8-884f-43f9-9fcc-d63e084360c1"
    Given url baseUrl + "/v1/context-policies"
    And header correlation-id = "0f804850-3681-403a-82b7-b0bf52113451"
    And request
      """
      {
        "key": {
          "id": "#(contextPolicyId)",
          "priority": 1,
          "channelId": "#(channelId)",
          "type": "channel"
        },
        "sequencePolicies": [
          {
            "name": "one-time-passcode",
            "stagePolicies": [
              {
                "type": "all-methods",
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
        ],
        "protectSensitiveData": false
      }
      """
    And method POST
    And status 201
    And url baseUrl + "/v1/identities"
    And header correlation-id = "af50579c-16a7-4b19-9399-dcb2b42f2c35"
    And request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4927111111111133" }
        ],
        "phoneNumbers": [
          { "value": "+4407808247733" }
        ]
      }
      """
    And method POST
    And status 201
    And url baseUrl + "/v1/lockout-policies"
    And header correlation-id = "c077f408-29b2-49fa-946e-1c83890d5127"
    * def lockoutPolicyId = "f1d9b537-be1a-4ade-9100-38758d181212"
    And request
      """
      {
        "key": {
          "id": "#(lockoutPolicyId)",
          "priority": 1,
          "channelId": "#(channelId)",
          "type": "channel"
        },
        "stateCalculator": {
          "maxNumberOfAttempts": 5,
          "includeAttemptsPolicy": {
            "type": "all-attempts"
          },
          "type": "hard-lockout"
        },
        "recordAttemptPolicy": {
          "type": "always-record"
        }
      }
      """
    And method POST
    And status 201
    And header channel-id = channelId
    And header correlation-id = "6adbeaae-fed8-455a-af44-4bc6ac10e11e"
    And url baseUrl + "/v1/contexts"
    And request
      """
      {
        "channel": {
          "id": "#(channelId)",
          "country": "GB"
        },
        "activity": {
          "name": "default-activity",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4927111111111133"
          }
        ]
      }
      """
    And method POST
    And status 201
    And match response ==
      """
      {
        "id": "#uuid",
        "created": "#notnull",
        "expiry": "#notnull",
        "request": {
          "initial": {
            "channel": {
              "id": "#(channelId)",
              "country": "GB"
            },
            "aliases": [
              {
                "type": "credit-card-number",
                "value": "4927111111111133"
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
              "channelId": "#(channelId)",
              "type": "channel"
            },
            "sequencePolicies": [
              {
                "name": "one-time-passcode",
                "stagePolicies": [
                  {
                    "type": "all-methods",
                    "methodPolicies": [
                      {
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
              }
            ],
            "protectSensitiveData": false
          },
          "identity": {
            "idvId": "#uuid",
            "country": "GB",
            "aliases": [
              {
                "type": "credit-card-number",
                "value": "4927111111111133"
              },
              {
                "type": "idv-id",
                "value": "#uuid"
              }
            ],
            "phoneNumbers": [
              { "value": "+4407808247733" }
            ]
          }
        },
        "sequences": [
          {
            "name": "one-time-passcode",
            "stages": [
              {
                "type": "all-methods",
                "methods": [
                  {
                    "name": "one-time-passcode",
                    "deliveryMethods": [
                      {
                        "id": "#uuid",
                        "type": "sms",
                        "value": "+447808247733",
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
                    "eligibility": {
                      "eligible": true
                    }
                  }
                ]
              }
            ],
            "duration": 300000,
            "eligibility": {
              "eligible": true
            }
          }
        ],
        "verifications": [],
        "eligible": true,
        "successful": false,
        "complete": false
      }
      """
    * def contextId = response.id
    And request
      """
      {
        "contextId": "#(contextId)",
        "methodName": "default-method"
      }
      """
    And header channel-id = channelId
    And header correlation-id = "c7dc1d6a-66d3-4771-9a01-b5825cf77e32"
    And url baseUrl + "/v1/contexts/verifications"
    And method POST
    And status 422
    And match response ==
      """
      {
        "title":"Not next method",
        "message":"default-method",
        "status":422
      }
      """