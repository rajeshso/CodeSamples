Feature: Calculate the cost

  Scenario: Calculate the cost for InternalCombustionEngine with PETROL
    Given the configuration is "machine.properties"
    When machineName is "wmachine" that has an Engine "wmachineInternalCombustionEngine" filled with "100" litres of fuel "PETROL"
    And I request the cost of "8" widgets
    Then the cost should be "9"

  Scenario: Calculate the cost for InternalCombustionEngine with DIESEL
    Given the configuration is "machine.properties"
    When machineName is "wmachine" that has an Engine "wmachineInternalCombustionEngine" filled with "90" litres of fuel "DIESEL"
    And I request the cost of "10" widgets
    Then the cost should be "12"

  Scenario: Calculate the cost for SteamEngine with COAL
    Given the configuration is "machine.properties"
    When machineName is "wmachine" that has an Engine "wmachineSteamEngine" filled with "50" litres of fuel "COAL"
    And I request the cost of "2" widgets
    Then the cost should be "6"

  Scenario: Calculate the cost for SteamEngine with WOOD
    Given the configuration is "machine.properties"
    When machineName is "wmachine" that has an Engine "wmachineSteamEngine" filled with "50" litres of fuel "WOOD"
    And I request the cost of "20" widgets
    Then the cost should be "44"

  Scenario: Expect an Exception for incorrect configuration
    Given the incorrect configuration is "abcd.properties"
    Then the error message should be "org.apache.commons.configuration.ConfigurationException: Cannot locate configuration source abcd.properties"

  Scenario: Expect an Exception for an unknown Engine
    Given the configuration is "machine.properties"
    When machineName is "wmachine" that has an unconfigured Engine "HeatEngine" filled with "50" watts of unconfigured fuel "WATTS"
    Then the error message should be "Invalid fuel to initialize the WidgetMachine : "

