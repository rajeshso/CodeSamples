Feature: A system provided by a company allows customers to sign in using their username and password.
  There is a requirement for an automated system to be developed to help identify attempts to hack the system and 
  compromise accounts. Activity log files are recorded and the new system will need to process these logs to identify 
  suspicious activity. 
  
  Write a Java (or Groovy or Scala) program implementing the HackerDetector interface (outlined below) which defines 
  a single method 'parseLine'. The method should take one line of the log file at a time and return the IP address 
  if any suspicious activity is identified or null if the activity appears to be normal.

  Scenario: The parseline method will be called each time a new log line is produced.
    Given the Log Monitoring System configuration is "lms.properties"
    When the customer invokes parseline with a line of log value "80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning"
    Then The result is null

  Scenario: The first detection method will be to identify a single IP address that has attempted a failed login 5 or more
    times within a 5 minute period. On detection you should return the suspicious IP.

    Given the Log Monitoring System configuration is "lms.properties"
    When the customer invokes parseline with a line of log value "80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning"
    And the customer invokes parseline with a line of log value "80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning"
    And the customer invokes parseline with a line of log value "80.238.9.179,133612949,SIGNIN_FAILURE,Dave.Branning"
    And the customer invokes parseline with a line of log value "80.238.9.179,133612950,SIGNIN_FAILURE,Dave.Branning"
    And the customer invokes parseline with a line of log value "99.999.9.999,133612951,SIGNIN_FAILURE,Andy.Murray"
    And the customer invokes parseline with a line of log value "80.238.9.179,133612952,SIGNIN_FAILURE,Dave.Branning"
    Then The result is "80.238.9.179"
