Feature: Validating place API
@addPlace
Scenario Outline: Verify if place is being Successfully added using AddPlaceAPI
    Given Add place Payload with "<name>","<language>","<address>"
    When user calls "AddPlaceAPI" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify Place_Id created maps to "<name>" using "getPlaceAPI"
    
Examples:
         |name      |language|address       |
         |Bhupe     |English |Purvarang Pune|
     #    |Deepak   |Hindi   |Housing board |
     #    |Rahul    |Marathi |Tumsar        |
     

@DeletePlace 
 Scenario: Verify if Delete Place functionality is working fine
 
     Given Delete PlacePay load
     When user calls "deletePlaceAPI" with "POST" http request
     Then the API call got success with status code 200
     And  "status" in response body is "OK"
