Feature: As a customer I don’t want my account to be able to access movies that have a higher
  parental control level than my current preference setting.

  Scenario: The service should accept as input the customer’s parental control level U preference
    and a movie Casino Royale. The customer is able to watch the movie and ParentalControlService 
    should indicate this to the calling client

    Given the parenta level configuration is "/src/main/resources/parentalControlLevel.properties" and the movie meta data configuration is "/src/main/resources/movieMetaData.properties"
    When the customer’s parental1 control level is "U" and the movie is "Casino Royale"
    Then The customer is able to1 watch the movie
    
