Feature: Create a Java library, suitable for use in a server-side application, that can take a Java int in the 
			range 0 to 999,999,999 inclusive and returns the equivalent number,as a String,in British English words.

  Scenario: The Output must be correct. The program should pay attention to the specific conventions of British English, 
               particularly concerning the use of ‘and’ between certain phrases. 
    Given the input is 1999
    Then the output should be "One Thousand , Nine Hundred and Ninety-Nine"
    
  Scenario: The program can take a Java int in the range 0 to 999,999,999 inclusive. Use 1,000,000,000 
    Given the input is 1000000000
    Then the exception should have a message "1000000000 out of range."

  Scenario: Return a equivalent number for sample data 0
    Given the input is 0
    Then the output should be "Zero"

  Scenario: Return a equivalent number for sample data 1
    Given the input is 1
    Then the output should be "One"
    
  Scenario: Return a equivalent number for sample data 21
    Given the input is 21
    Then the output should be "Twenty-One"
    
  Scenario: Return a equivalent number for sample data 105
    Given the input is 105
    Then the output should be "One Hundred and Five"
    
  Scenario: Return a equivalent number for sample data 123
    Given the input is 123
    Then the output should be "One Hundred and Twenty-Three"
    
  Scenario: Return a equivalent number for sample data 1005
    Given the input is 1005
    Then the output should be "One Thousand , Five"

  Scenario: Return a equivalent number for sample data 1042
    Given the input is 1042
    Then the output should be "One Thousand , Forty-Two"
    
  Scenario: Return a equivalent number for sample data 1105
    Given the input is 1105
    Then the output should be "One Thousand , One Hundred and Five"
    
  Scenario: Return a equivalent number for sample data 56945781
    Given the input is 56945781
    Then the output should be "Fifty-Six Million , Nine Hundred and Forty-Five Thousand , Seven Hundred and Eighty-One"
    
  Scenario: Return a equivalent number for sample data 999999999
    Given the input is 999999999
    Then the output should be "Nine Hundred and Ninety-Nine Million , Nine Hundred and Ninety-Nine Thousand , Nine Hundred and Ninety-Nine"    
       
