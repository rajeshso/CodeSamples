Feature: Checkout
Scenario Outline: Checkout bananas
	Given the price of a "banana" is 40p
	When I checkout <count> "banana"
	Then the total price should be <totalprice>p
	Examples:
	|count|totalprice|
	|1|40|
	|2|80|
	|10|400|
