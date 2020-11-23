Feature: Create Requests

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
    Then status 422
    And match response ==
      """
      {
        "status": 422,
        "title": "Context policy not configured",
        "message": "Context policy not configured for channel, activity and alias combination",
        "meta": {
          "aliasTypes": [
            "credit-card-number"
          ],
          "activityName": "default-activity",
          "channelId": "context-test-channel1"
        }
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
            "value": "4927111111111112"
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
        "message": "credit-card-number|4927111111111112"
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
          { "type": "credit-card-number", "value": "4927111111111113" }
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
            "value": "4927111111111113"
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
                    },
                    "maskNumbers": false
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
          { "type": "credit-card-number", "value": "4927111111111114" }
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
            "value": "4927111111111114"
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
                "value": "4927111111111114"
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
                          },
                          "maskNumbers": false
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
                "value": "4927111111111114"
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
                    "value": "+447808247743",
                    "eligibility": {
                      "eligible": true,
                      "complete": true
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
                    "value": "+447808247749",
                    "eligibility": {
                      "reason": "sim swap status failure is not allowed",
                      "eligible": false,
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
                "eligibility": {
                  "eligible": true
                }
              }
            ],
            "duration": 300000,
            "eligibility": {
              "eligible": true
            },
            "successful": false,
            "complete": false
          }
        ],
        "eligible": true,
        "successful": false,
        "complete": false
      }
      """

  Scenario: Create context - Success - Otp method returned - Masked Phone Numbers
    Given url baseUrl + "/context-policies"
    * def contextPolicyId = "8d6322ff-b8c8-4e2a-95b4-207fe5939f65"
    And request
      """
      {
        "key": {
          "id": "#(contextPolicyId)",
          "priority": 1,
          "channelId": "context-test-channel5",
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
                      "minDaysSinceSwap": 6,
                      "async": false
                    },
                    "maskNumbers": true
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
          { "type": "credit-card-number", "value": "4927111111111115" }
        ],
        "phoneNumbers": [
          { "value": "+4407808247744" },
          { "value": "+4407808247743" }
        ]
      }
      """
    And method POST
    And status 201
    And url baseUrl + "/lockout-policies"
    * def lockoutPolicyId = "2f96109d-2a8c-4f91-a18e-ca7b3863b4bb"
    And request
      """
      {
        "key": {
          "id": "#(lockoutPolicyId)",
          "priority": 1,
          "channelId": "context-test-channel5",
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
          "id": "context-test-channel5",
          "country": "GB"
        },
        "activity": {
          "name": "default-activity",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4927111111111115"
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
              "id": "context-test-channel5",
              "country": "GB"
            },
            "aliases": [
              {
                "type": "credit-card-number",
                "value": "4927111111111115"
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
              "channelId": "context-test-channel5",
              "type": "channel"
            },
            "sequencePolicies": [
              {
                "name": "one-time-passcode",
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
                          },
                          "maskNumbers": true
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
                "value": "4927111111111115"
              },
              {
                "type": "idv-id",
                "value": "#uuid"
              }
            ],
            "phoneNumbers": [
              { "value": "***********744" },
              { "value": "***********743" }
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
                    "value": "**********743",
                    "eligibility": {
                      "eligible": true,
                      "complete": true
                    }
                  },
                  {
                    "id": "#uuid",
                    "type": "sms",
                    "value": "**********744",
                    "eligibility": {
                      "reason": "sim swap status unknown not acceptable",
                      "eligible": false,
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
                "eligibility": {
                  "eligible": true
                }
              }
            ],
            "duration": 300000,
            "eligibility": {
              "eligible": true
            },
            "successful": false,
            "complete": false
          }
        ],
        "eligible": true,
        "successful": false,
        "complete": false
      }
      """

  Scenario: Post result - Success - Result Added to context
    Given url baseUrl + "/context-policies"
    * def contextPolicyId = "03ac7483-0006-4d99-b38f-dd33d3004e0a"
    And request
      """
      {
        "key": {
          "id": "#(contextPolicyId)",
          "priority": 1,
          "channelId": "context-test-channel6",
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
                        "minDaysSinceSwap": 6,
                        "async": false
                      },
                      "maskNumbers": false
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
          { "type": "credit-card-number", "value": "4927111111111116" }
        ],
        "phoneNumbers": [
          { "value": "+4407808247743" }
        ]
      }
      """
    And method POST
    And status 201
    And url baseUrl + "/lockout-policies"
    * def lockoutPolicyId = "c5a7a9d5-8c9d-4ddd-bd01-727d3075d147"
    And request
      """
      {
        "key": {
          "id": "#(lockoutPolicyId)",
          "priority": 1,
          "channelId": "context-test-channel6",
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
          "id": "context-test-channel6",
          "country": "GB"
        },
        "activity": {
          "name": "default-activity",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4927111111111116"
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
              "id": "context-test-channel6",
              "country": "GB"
            },
            "aliases": [
              {
                "type": "credit-card-number",
                "value": "4927111111111116"
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
              "channelId": "context-test-channel6",
              "type": "channel"
            },
            "sequencePolicies": [
              {
                "name": "one-time-passcode",
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
                          },
                          "maskNumbers": false
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
                "value": "4927111111111116"
              },
              {
                "type": "idv-id",
                "value": "#uuid"
              }
            ],
            "phoneNumbers": [
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
                "eligibility": {
                  "eligible": true
                }
              }
            ],
            "duration": 300000,
            "eligibility": {
              "eligible": true
            },
            "successful": false,
            "complete": false
          }
        ],
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
        "result": {
          "methodName": "one-time-passcode",
          "verificationId": "a098310e-feb3-435d-bf69-663417af08e3",
          "timestamp": "2020-09-27T06:57:47.522Z",
          "successful": false
        }
      }
      """
    And url baseUrl + "/contexts/results"
    And method PATCH
    And status 200
    And match response ==
      """
      {
        "id": "#uuid",
        "created": "#notnull",
        "expiry": "#notnull",
        "request": {
          "initial": {
            "channel": {
              "id": "context-test-channel6",
              "country": "GB"
            },
            "aliases": [
              {
                "type": "credit-card-number",
                "value": "4927111111111116"
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
              "channelId": "context-test-channel6",
              "type": "channel"
            },
            "sequencePolicies": [
              {
                "name": "one-time-passcode",
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
                          },
                          "maskNumbers": false
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
                "value": "4927111111111116"
              },
              {
                "type": "idv-id",
                "value": "#uuid"
              }
            ],
            "phoneNumbers": [
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
                "eligibility": {
                  "eligible": true
                },
                "results": [
                  {
                    "methodName": "one-time-passcode",
                    "verificationId": "a098310e-feb3-435d-bf69-663417af08e3",
                    "timestamp": "2020-09-27T06:57:47.522Z",
                    "successful": false
                  }
                ]
              }
            ],
            "duration": 300000,
            "eligibility": {
              "eligible": true
            },
            "successful": false,
            "complete": false
          }
        ],
        "eligible": true,
        "successful": false,
        "complete": false
      }
      """

  Scenario: Create and get context - Success - context returned
    Given url baseUrl + "/identities"
    And request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4927111111111117" }
        ],
        "phoneNumbers": [
          { "value": "+4407808247743" }
        ]
      }
      """
    And method POST
    And status 201
    And url baseUrl + "/contexts"
    And request
      """
      {
        "channel": {
          "id": "abc",
          "country": "GB"
        },
        "activity": {
          "name": "login",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4927111111111117"
          }
        ]
      }
      """
    And method POST
    And status 201
    * def contextId = response.id
    And url baseUrl + "/contexts/" + contextId
    And method GET
    And status 200

  Scenario: Get context - Error - context not found
    * def contextId = "bacf1ac9-d33e-4e2f-9fff-a01bfab45bbd"
    Given url baseUrl + "/contexts/" + contextId
    When method GET
    Then status 404
    And match response ==
      """
      {
        "status": 404,
        "title": "Context not found",
        "message": "#(contextId)"
      }
      """

  @sequential
  Scenario: Create and get context - Error - context expired
    Given url baseUrl + "/identities"
    And request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4927111111111118" }
        ],
        "phoneNumbers": [
          { "value": "+4407808247746" }
        ]
      }
      """
    And method POST
    And status 201
    And url baseUrl + "/contexts"
    And request
      """
      {
        "channel": {
          "id": "abc",
          "country": "GB"
        },
        "activity": {
          "name": "login",
          "timestamp": "2020-09-27T06:56:47.522Z"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4927111111111118"
          }
        ]
      }
      """
    And method POST
    And status 201
    * def contextId = response.id
    And url baseUrl + "/time-offset"
    And request
      """
      { "offset": 360000 }
      """
    And method PUT
    And status 200
    And url baseUrl + "/contexts/" + contextId
    And method GET
    And status 410
    And match response ==
      """
      {
        "title": "Context expired",
        "message": "#notnull",
        "status": 410,
        "meta": {
          "id":"#uuid",
          "expiry":"#notnull"
        }
      }
      """
    And url baseUrl + "/time-offset"
    And method DELETE
    And status 200