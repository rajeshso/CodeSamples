Feature: Two bananas scanned separately

  Scenario: Two bananas scanned separately
    Given the price of a "banana" is 40p
    When I checkout 1 "banana"
    And I checkout 1 "banana"
    Then the runningtotal price should be 80p

  Scenario: Three bananas scanned separately
    Given the price of a "banana" is 20p
    When I checkout 1 "banana"
    And I checkout 1 "banana"
    And I checkout 1 "banana"
    Then the runningtotal price should be 60p
