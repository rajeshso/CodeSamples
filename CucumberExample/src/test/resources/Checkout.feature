Feature: This is a Checkout
Scenario: Checkout a banana
	Given the price of a "banana" is 40p 
	When I checkout 1 "banana"
	Then the total price should be 40p

