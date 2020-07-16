Feature: Identity Maintenance

  Background:
    * url baseUrl + "/identities"

  Scenario: Get identity - Error - unsupported alias type
    Given param aliasType = "ABC"
    And param aliasValue = "123"
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

  Scenario: Get identity - Error - identity not found
    Given param aliasType = "credit-card-number"
    And param aliasValue = "4929111111111199"
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
    And match responseHeaders.Location contains baseUrl + "/identities/" + response.idvId

  Scenario: Create + Get identity - Success - Create with one alias, Get by alias
    Given request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111111" }
        ]
      }
      """
    And method POST
    And status 201
    And param aliasType = "credit-card-number"
    And param aliasValue = "4929111111111111"
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

  Scenario: Create + Get identity - Success - Create with one alias, Get by idv id
    Given request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111119" }
        ]
      }
      """
    And method POST
    And status 201
    * def idvId = response.idvId
    And url baseUrl + "/identities/" + idvId
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

  Scenario: Create + Update identity - Error - idv id cannot be updated
    Given request
      """
      {
        "country": "GB",
        "aliases": [
          { "type": "credit-card-number", "value": "4929111111111112" }
        ]
      }
      """
    And method POST
    And status 201
    * def existingIdvId = response.idvId
    * def newIdvId = "dec2f278-b7e0-44fa-ab1f-f93f942bdf4d"
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
          { "type": "mobile", "value": "+4407808247742" }
        ]
      }
      """
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
          { "type": "mobile", "value": "+4407808247742" }
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
          { "type": "mobile", "value": "+4407808111111" }
        ]
      }
      """
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
          { "type": "mobile", "value": "+4407808222222" }
        ]
      }
      """
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
          { "type": "other", "value": "+441604333333" }
        ]
      }
      """
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
          "merge2@two.com",
          "merge1@one.com"
        ],
        "phoneNumbers": [
          { "type": "other", "value": "+441604333333" },
          { "type": "mobile", "value": "+4407808222222" },
          { "type": "mobile", "value": "+4407808111111" }
        ]
      }
      """