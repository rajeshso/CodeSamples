Feature: As a customer I don’t want my account to be able to access movies that have a higher
  parental control level than my current preference setting.

  Scenario: The service should accept as input the customer’s parental control level U preference
    and a movie Casino Royale. The customer is able to watch the movie and ParentalControlService 
    should indicate this to the calling client

    Given the parent level configuration is "parentalControlLevel.properties" and the movie meta data configuration is "movieMetaData.properties"
    When the customer’s parental control level is "U" and the movie is "Casino Royale"
    Then The customer is able to watch the movie

  Scenario: If the parental control level of the movie is greater than the customer’s preference, indicate to
    the caller that the customer cannot watch the movie

    Given the parent level configuration is "parentalControlLevel.properties" and the movie meta data configuration is "movieMetaData.properties"
    When the customer’s parental control level is "15" and the movie is "The Spy Who Loved Me"
    Then The customer is NOT able to watch the movie

  Scenario: The movie service could not find the given movie
    Given the parent level configuration is "parentalControlLevel.properties" and the movie meta data configuration is "movieMetaData.properties"
    When the customer’s parental control level is "U" and the movie is "ABCD"
    Then the exception should be "TitleNotFoundException"
