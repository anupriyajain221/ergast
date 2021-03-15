Feature: Test drivers data of all F1 drivers

  Scenario: Record DriverIds returned in the response
    Given I get all the drivers data from the api
    Then response should have status code "200"
    And that the total number of drivers should be "30"
 
  Scenario: Record Nationality of driver
    Given I get all the drivers data from the api
    Then response should have status code "200"
    And that the nationality of driver name "adams" is "Belgian"   
    
  Scenario Outline: Record All Drivers With specific Nationality
    Given I get all the drivers data from the api
    Then response should have status code "200"
    And that the total number of drivers with nationality "British" should be "5" and their driverIds are "<data>"
    Examples:
    | data |
    | abecassis,acheson,aitken,allison,anderson |
     
  Scenario Outline: Record drivers with GivenNames born between specific years
    Given I get all the drivers data from the api
    Then response should have status code "200"
    And that the total number of drivers born between "1920" and "1925" should be "3" and their givenNames are "<data>"
    Examples:
    | data |
    | Kurt,George,Keith |