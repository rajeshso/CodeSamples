Feature: Pre-interview Exercise for Developer Candidates - Cucumber is used as a quality communication to bridge between specification and code. Hence, the candidate has used the TDD for tht detailed tests.

  Scenario: First Step is Shopping cart - Scenario1
    Given the fruit cost in "\\src\\test\\resources\\item.properties"
    When the customer scans "Apple,Apple,Orange,Apple"
    Then The total cost should be 2.05

  Scenario: First Step is Shopping cart - Scenario2
    Given the fruit cost in "\\src\\test\\resources\\item.properties"
    When the customer scans "Apple, Apple,Apple"
    Then The total cost should be 1.80

  Scenario: Second Step is Simple Offers - Apples - Scenario3
    Given the fruit cost in "\\src\\test\\resources\\item.properties" and offers in "\\src\\test\\resources\\offer.properties"
    When the customer scans for "Apple,Apple,Apple"
    Then The discounted cost should be 1.2

  Scenario: Second Step is Simple Offers - Oranges - Scenario3
    Given the fruit cost in "\\src\\test\\resources\\item.properties" and offers in "\\src\\test\\resources\\offer.properties"
    When the customer scans for "Orange, Orange, Orange, Orange"
    Then The discounted cost should be 0.75

  Scenario: Second Step is Simple Offers - Apples and Oranges - Scenario3
    Given the fruit cost in "\\src\\test\\resources\\item.properties" and offers in "\\src\\test\\resources\\offer.properties"
    When the customer scans for "Apple, Apple, Apple, Orange, Orange, Orange, Orange"
    Then The discounted cost should be 1.95
