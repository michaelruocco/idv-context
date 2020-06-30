Feature: Identity Maintenance

  Scenario: Get identity - Error - unsupported alias type
    Given url 'http://localhost:8081/identities?aliasType=ABC&aliasValue=123'
    When method GET
    Then status 422
    And match response ==
      """
      {
        status: 422,
        title: 'Unsupported alias type',
        message: 'ABC'
      }
      """

  Scenario: Get identity - Error - identity not found
    Given url 'http://localhost:8081/identities?aliasType=credit-card-number&aliasValue=4929111111111111'
    When method GET
    Then status 500
    And match response ==
      """
      {
        status: 500,
        title: 'Internal server error',
        message: 'credit-card-number|4929111111111111'
      }
      """

  Scenario: Update identity - Error - Identity does not belong to country
    Given url 'http://localhost:8081/identities'
    And request
      """
      {
        aliases: [
          { type: 'credit-card-number', value: '4929111111111111' }
        ]
      }
      """
    When method POST
    Then status 500
    And match response ==
      """
      {
        status: 500,
        title: 'Internal server error',
        message: 'identity must belong to country'
      }
      """

  Scenario: Create + Get identity - Success - Create with one alias, Get with IDV ID allocated
    Given url 'http://localhost:8081/identities'
    And request
      """
      {
        country: 'GB',
        aliases: [
          { type: 'credit-card-number', value: '4929111111111111' }
        ]
      }
      """
    And method POST
    And status 200
    And url 'http://localhost:8081/identities?aliasType=credit-card-number&aliasValue=4929111111111111'
    When method GET
    Then status 200
    And match response ==
      """
      {
        idvId: '#uuid',
        country: 'GB',
        aliases: [
          { type: 'credit-card-number', value: '4929111111111111' },
          { type: 'idv-id', value: '#uuid' }
        ]
      }
      """

  Scenario: Create + Update identity - Error - idv id cannot be updated
    Given url 'http://localhost:8081/identities'
    And request
      """
      {
        country: 'GB',
        aliases: [
          { type: 'credit-card-number', value: '4929111111111112' }
        ]
      }
      """
    And method POST
    And status 200
    And request
      """
      {
        country: 'GB',
        aliases: [
          { type: 'idv-id', value: 'dec2f278-b7e0-44fa-ab1f-f93f942bdf4d' },
          { type: 'credit-card-number', value: '4929111111111112' }
        ]
      }
      """
    When method POST
    Then status 422
    And match response ==
      """
      {
        status: 422,
        title: 'Cannot update idv id',
        message: '#ignore',
        meta: {
          new: '#uuid',
          existing: '#uuid'
        }
      }
      """

  Scenario: Create + Update identity - Success - Add new alias, phone number and email
    Given url 'http://localhost:8081/identities'
    And request
      """
      {
        country: 'GB',
        aliases: [
          { type: 'credit-card-number', value: '4929111111111113' }
        ]
      }
      """
    And method POST
    And status 200
    And request
      """
      {
        country: 'GB',
        aliases: [
          { type: 'credit-card-number', value: '4929111111111113' },
          { type: 'debit-card-number', value: '4929111111111114' }
        ],
        emailAddresses: [
          'joe.bloggs@hotmail.com'
        ],
        phoneNumbers: [
          { type: 'mobile', value: '+4407808247742' }
        ]
      }
      """
    When method POST
    Then status 200
    And match response ==
      """
      {
        idvId: '#uuid',
        country: 'GB',
        aliases: [
          { type: 'credit-card-number', value: '4929111111111113' },
          { type: 'debit-card-number', value: '4929111111111114' },
          { type: 'idv-id', value: '#uuid' }
        ],
        emailAddresses: [
          'joe.bloggs@hotmail.com'
        ],
        phoneNumbers: [
          { type: 'mobile', value: '+4407808247742' }
        ]
      }
      """

  Scenario: Merge identities - Error - Cannot merge identities with different countries
    Given url 'http://localhost:8081/identities'
    And request
      """
      {
        country: 'GB',
        aliases: [
          { type: 'credit-card-number', value: '4929111111111115' }
        ]
      }
      """
    And method POST
    And status 200
    And request
      """
      {
        country: 'DE',
        aliases: [
          { type: 'debit-card-number', value: '4929111111111116' }
        ]
      }
      """
    And method POST
    And status 200
    And request
      """
      {
        country: 'GB',
        aliases: [
          { type: 'credit-card-number', value: '4929111111111115' },
          { type: 'debit-card-number', value: '4929111111111116' }
        ]
      }
      """
    When method POST
    Then status 500
    And match response ==
      """
      {
        status: 500,
        title: 'Internal server error',
        message: 'countries do not match'
      }
      """

  Scenario: Merge identities - Success - Two existing identities merged under new idv id with all aliases and data combined
    Given url 'http://localhost:8081/identities'
    And request
      """
      {
        country: 'GB',
        aliases: [
          { type: 'credit-card-number', value: '4929111111111117' }
        ],
        emailAddresses: [
          'merge1@one.com'
        ],
        phoneNumbers: [
          { type: 'mobile', value: '+4407808111111' }
        ]
      }
      """
    And method POST
    And status 200
    And request
      """
      {
        country: 'GB',
        aliases: [
          { type: 'debit-card-number', value: '4929111111111118' }
        ],
        emailAddresses: [
          'merge2@two.com'
        ],
        phoneNumbers: [
          { type: 'mobile', value: '+4407808222222' }
        ]
      }
      """
    And method POST
    And status 200
    And request
      """
      {
        country: 'GB',
        aliases: [
          { type: 'credit-card-number', value: '4929111111111117' },
          { type: 'debit-card-number', value: '4929111111111118' }
        ],
        emailAddresses: [
          'merge3@three.com'
        ],
        phoneNumbers: [
          { type: 'other', value: '+441604333333' }
        ]
      }
      """
    When method POST
    Then status 200
    And match response ==
      """
      {
        idvId: '#uuid',
        country: 'GB',
        aliases: [
          { type: 'credit-card-number', value: '4929111111111117' },
          { type: 'debit-card-number', value: '4929111111111118' },
          { type: 'idv-id', value: '#uuid' }
        ],
        emailAddresses: [
          'merge3@three.com',
          'merge1@one.com',
          'merge2@two.com'
        ],
        phoneNumbers: [
          { type: 'other', value: '+441604333333' },
          { type: 'mobile', value: '+4407808111111' },
          { type: 'mobile', value: '+4407808222222' }
        ]
      }
      """