Feature: Eligibility Requests

  Background:
    * url baseUrl + "/v1/eligibility"

  Scenario: Create eligibility - Error - Identity not found for internal data lookup
    Given request
      """
      {
        "channel": {
          "id": "default-channel",
          "country": "GB"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4928111111111111"
          }
        ],
        "requestedData": [
          "phone-numbers",
          "email-addresses"
        ]
      }
      """
    And header correlation-id = "99f1209d-d553-4c04-b45d-e70db3367ac9"
    When method POST
    Then status 404
    And match response ==
      """
      {
        status: 404,
        title: "Identity not found",
        message: "credit-card-number|4928111111111111"
      }
      """

  Scenario: Create eligibility - Success - Identity created for internal data lookup
    Given url baseUrl + "/v1/identities"
    And request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4928111111111112" }
        ]
      }
      """
    And header correlation-id = "94b49f61-8dfe-4edf-a14b-3e2ddc482818"
    And method POST
    And status 201
    And url baseUrl + "/v1/eligibility"
    And request
      """
      {
        "channel": {
          "id": "default-channel",
          "country": "GB"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4928111111111112"
          }
        ],
        "requestedData": [
          "phone-numbers",
          "email-addresses"
        ]
      }
      """
    And header correlation-id = "6c3ccc07-29ca-4af4-8011-15b5662891ef"
    When method POST
    Then status 201
    And match response ==
      """
      {
        "channel": {
          "id": "default-channel",
          "country": "GB"
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4928111111111112"
          }
        ],
        "requestedData": [
          "phone-numbers",
          "email-addresses"
        ],
        identity: {
          "idvId": "#uuid",
          "country": "GB",
          "aliases": [
            {
              "type": "credit-card-number",
              "value": "4928111111111112"
            },
            {
                "type": "idv-id",
                "value": "#uuid"
            }
          ]
        }
      }
      """

  Scenario: Create eligibility - Success - Create with external data lookup
    Given request
      """
      {
        "channel": {
          "id": "abc",
          "country": "GB",
          "validCookie": true
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4928111111111113"
          }
        ],
        "requestedData": [
          "phone-numbers",
          "email-addresses"
        ]
      }
      """
    And header correlation-id = "a417402b-4188-431b-b713-8f65c40ab84d"
    When method POST
    Then status 201
    And match response ==
      """
      {
        "channel": {
          "id": "abc",
          "country": "GB",
          "validCookie": true
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4928111111111113"
          }
        ],
        "requestedData": [
          "phone-numbers",
          "email-addresses"
        ],
        identity: {
          "idvId": "#uuid",
          "country": "GB",
          "aliases": [
            {
              "type": "credit-card-number",
              "value": "4928111111111113"
            },
            {
              "type": "debit-card-number",
              "value": "5928111111111113"
            }
            {
              "type": "idv-id",
              "value": "#uuid"
            }
          ],
          "phoneNumbers":[
            { "lastUpdated":"2020-08-29T20:55:12.825Z","value":"+447089111111"},
            { "value": "+447089121212" }
          ],
          "emailAddresses": [
            "joe.bloggs@hotmail.co.uk",
            "joebloggs@yahoo.co.uk"
          ]
        }
      }
      """

  Scenario: Create eligibility - Success - Create with provided channel data
    Given request
      """
      {
        "channel": {
          "id": "abc",
          "country": "GB",
          "validCookie": true,
          "phoneNumbers": [
            { "value": "07809123456" }
          ],
          "emailAddresses": [
            "bugs.bunny@sky.co.uk"
          ]
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4928111111111114"
          }
        ],
        "requestedData": [
          "phone-numbers",
          "email-addresses"
        ]
      }
      """
    And header correlation-id = "8062f857-193c-4a83-bf47-fdd5a55bd857"
    When method POST
    Then status 201
    And match response ==
      """
      {
        "channel": {
          "id": "abc",
          "country": "GB",
          "validCookie": true,
          "phoneNumbers": [
            { "value": "07809123456" }
          ],
          "emailAddresses": [
            "bugs.bunny@sky.co.uk"
          ]
        },
        "aliases": [
          {
            "type": "credit-card-number",
            "value": "4928111111111114"
          }
        ],
        "requestedData": [
          "phone-numbers",
          "email-addresses"
        ],
        identity: {
          "idvId": "#uuid",
          "country": "GB",
          "aliases": [
            {
              "type": "credit-card-number",
              "value": "4928111111111114"
            },
            {
              "type": "debit-card-number",
              "value": "5928111111111114"
            }
            {
              "type": "idv-id",
              "value": "#uuid"
            }
          ],
          "phoneNumbers":[
            { "lastUpdated":"2020-08-29T20:55:12.825Z","value":"+447089111111"},
            { "value": "+447089121212" },
            { "value": "07809123456" }
          ],
          "emailAddresses": [
            "joe.bloggs@hotmail.co.uk",
            "joebloggs@yahoo.co.uk",
            "bugs.bunny@sky.co.uk"
          ]
        }
      }
      """