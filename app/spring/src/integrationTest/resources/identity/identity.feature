Feature: Identity Maintenance

  Background:
    * url baseUrl + "/v1/identities"

  Scenario: Get identity - Error - Unsupported alias type
    Given param aliasType = "ABC"
    And param aliasValue = "123"
    And header correlation-id = "9249ff2d-0699-4445-a12d-3a29c56def39"
    When method GET
    Then status 422
    And match response ==
      """
      {
        "status": 422,
        "title": "Unsupported alias type",
        "message": "ABC"
      }
      """

  Scenario: Get identity - Error - Identity not found
    Given param aliasType = "credit-card-number"
    And param aliasValue = "4929111111111199"
    And header correlation-id = "77f7bbf4-2b1c-4b29-a7ca-af39f0488b4f"
    When method GET
    Then status 404
    And match response ==
      """
      {
        "status": 404,
        "title": "Identity not found",
        "message": "credit-card-number|4929111111111199"
      }
      """

  Scenario: Update identity - Error - Identity does not belong to country
    Given request
      """
      {
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111199" }
        ]
      }
      """
    And header correlation-id = "f44a7631-5467-475e-8784-6fa366f146ac"
    When method POST
    Then status 400
    And match response ==
      """
      {
        "status": 400,
        "title": "Cannot create an identity without a country"
      }
      """

  Scenario: Create Identity - Success - Create with one alias
    Given request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111110" }
        ]
      }
      """
    And header correlation-id = "2a3dbb1b-1987-456b-86fb-ed327fdddac6"
    When method POST
    Then status 201
    And match response ==
      """
      {
        "idvId": "#uuid",
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111110" },
          { "type": "idv-id", "value": "#uuid" }
        ]
      }
      """
    And match responseHeaders.Location contains baseUrl + "/v1/identities/" + response.idvId

  Scenario: Create + Get identity - Success - Create with one alias, get by alias
    Given request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111111" }
        ]
      }
      """
    And header correlation-id = "e825b87c-6ed6-451a-abd7-a7d2e25e110c"
    And method POST
    And status 201
    And param aliasType = "credit-card-number"
    And param aliasValue = "4929111111111111"
    And header correlation-id = "2eadbef5-90d1-4964-9b84-e23c9e6dc88f"
    When method GET
    Then status 200
    And match response ==
      """
      {
        "idvId": "#uuid",
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111111" },
          { "type": "idv-id", "value": "#uuid" }
        ]
      }
      """

  Scenario: Create + Get identity - Success - Create with one alias, get by idv id
    Given request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111119" }
        ]
      }
      """
    And header correlation-id = "a2965c49-d308-425c-ade9-461e4406a9a2"
    And method POST
    And status 201
    * def idvId = response.idvId
    And url baseUrl + "/v1/identities/" + idvId
    And header correlation-id = "c0aa4465-7405-46e1-b9e8-b2ef735db90e"
    When method GET
    Then status 200
    And match response ==
      """
      {
        "idvId": "#(idvId)",
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111119" },
          { "type": "idv-id", "value": "#(idvId)" }
        ]
      }
      """

  Scenario: Create + Update identity - Error - Idv id cannot be updated
    Given request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111112" }
        ]
      }
      """
    And header correlation-id = "b105e976-ef6d-4980-8081-210ba0d827a6"
    And method POST
    And status 201
    * def existingIdvId = response.idvId
    * def newIdvId = "6c281916-e447-4c0a-8220-aaa6a6feb3c6"
    And request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "idv-id", "value": "#(newIdvId)" },
          { "type": "credit-card-number", "value": "4929111111111112" }
        ]
      }
      """
    And header correlation-id = "697ba160-f794-40e0-8e6e-d988e9c48c9c"
    When method POST
    Then status 422
    And match response ==
      """
      {
        status: 422,
        title: "Cannot update idv id",
        message: "#ignore",
        meta: {
          new: "#uuid",
          existing: "#uuid"
        }
      }
      """
    And match response.message == "attempted to update existing value " + existingIdvId + " to " + newIdvId
    And match response.meta.new == newIdvId
    And match response.meta.existing == existingIdvId

  Scenario: Create + Update identity - Success - Add new alias, phone number and email
    Given request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111113" }
        ]
      }
      """
    And header correlation-id = "db42e0bd-4b08-4c69-9635-0183bdde0eb5"
    And method POST
    And status 201
    And request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111113" },
          { "type": "debit-card-number", "value": "4929111111111114" }
        ],
        "emailAddresses": [
          "joe.bloggs@hotmail.com"
        ],
        "phoneNumbers": [
          { "value": "+4407808247742", "lastUpdated": "2020-08-29T21:31:12.825Z" }
        ]
      }
      """
    And header correlation-id = "5bc6220f-c6d4-4f4d-b25c-a23ccff1c298"
    When method POST
    Then status 201
    And match response ==
      """
      {
        "idvId": "#uuid",
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111113" },
          { "type": "debit-card-number", "value": "4929111111111114" },
          { "type": "idv-id", "value": "#uuid" }
        ],
        "emailAddresses": [
          "joe.bloggs@hotmail.com"
        ],
        "phoneNumbers": [
          { "value": "+4407808247742", "lastUpdated": "2020-08-29T21:31:12.825Z" }
        ]
      }
      """

  Scenario: Create + Update identity - Success - Remove alias
    Given request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111120" },
          { "type": "debit-card-number", "value": "4929111111111121" }
        ],
      }
      """
    And header correlation-id = "cf37135d-160f-40c8-8ebb-5fe6a35aba7e"
    And method POST
    And status 201
    * def idvId = response.idvId
    And request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111120" }
          { "type": "idv-id", "value": "#(idvId)" }
        ]
      }
      """
    And header correlation-id = "97249df6-f42c-4a76-8d41-fe376147367b"
    When method POST
    Then status 201
    And match response ==
      """
      {
        "idvId": "#(idvId)",
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111120" },
          { "type": "idv-id", "value": "#uuid" }
        ]
      }
      """

  Scenario: Merge identities - Error - Cannot merge identities with different countries
    Given request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111115" }
        ]
      }
      """
    And header correlation-id = "0158fe36-3e1b-4e46-90e5-9091c38ed1c7"
    And method POST
    And status 201
    And request
      """
      {
        "country": "DE",
        "aliases": [
          { "type": "debit-card-number", "value": "4929111111111116" }
        ]
      }
      """
    And header correlation-id = "d2d1c687-1179-4cf7-8cbc-cf7cc55993a9"
    And method POST
    And status 201
    And request
      """
      {
        "country": "DE",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111115" },
          { "type": "debit-card-number", "value": "4929111111111116" }
        ]
      }
      """
    And header correlation-id = "fcd8207e-8cde-4855-a70a-d8e0de04edde"
    When method POST
    Then status 422
    And match response ==
      """
      {
        "status": 422,
        "title": "Cannot merge identities if countries do not match",
        "message": "attempted to merge identity from DE to GB",
        "meta": {
          "existing": "DE",
          "new": "GB"
        }
      }
      """

  Scenario: Merge identities - Success - Two existing identities merged under new idv id with all aliases and data combined
    Given request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111117" }
        ],
        "emailAddresses": [
          "merge1@one.com"
        ],
        "phoneNumbers": [
          { "value": "+4407808111111", "lastUpdated": "2020-08-29T21:31:11.111Z" }
        ]
      }
      """
    And header correlation-id = "8a7ffe6f-5516-43ec-bc5f-8a0a20aabf7e"
    And method POST
    And status 201
    And request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "debit-card-number", "value": "4929111111111118" }
        ],
        "emailAddresses": [
          "merge2@two.com"
        ],
        "phoneNumbers": [
          { "value": "+4407808222222", "lastUpdated": "2020-08-29T21:31:22.222Z" }
        ]
      }
      """
    And header correlation-id = "e7be4faa-4af6-4865-bd00-a525b0d5a1fc"
    And method POST
    And status 201
    And request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111117" },
          { "type": "debit-card-number", "value": "4929111111111118" }
        ],
        "emailAddresses": [
          "merge3@three.com"
        ],
        "phoneNumbers": [
          { "value": "+441604333333", "lastUpdated": "2020-08-29T21:31:33.333Z" }
        ]
      }
      """
    And header correlation-id = "f21f204c-1895-4d9a-9639-5bff946187f8"
    When method POST
    Then status 201
    And match response ==
      """
      {
        "idvId": "#uuid",
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111117" },
          { "type": "debit-card-number", "value": "4929111111111118" },
          { "type": "idv-id", "value": "#uuid" }
        ],
        "emailAddresses": [
          "merge3@three.com",
          "merge1@one.com"
          "merge2@two.com"
        ],
        "phoneNumbers": [
          { "value": "+441604333333", "lastUpdated": "2020-08-29T21:31:33.333Z" },
          { "value": "+4407808111111", "lastUpdated": "2020-08-29T21:31:11.111Z" },
          { "value": "+4407808222222", "lastUpdated": "2020-08-29T21:31:22.222Z" }
        ]
      }
      """