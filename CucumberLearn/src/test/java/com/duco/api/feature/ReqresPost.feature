
@API-112
@ERPFI1
Feature: Resource Creation
Scenario Outline: <TCNO> Create a new user with valid input for <scenario>
Given user have providing valid input request payload "<payload>" for resource creation
When user request Resource creation endpoint valid request Payload "<payload>"
Then Resource creation service return the expected success "<responsePayload>" as response
 Examples: 
 |TCNO                 | payload           |responsePayload   |scenario                                    |
 |API_TS_112_TC_01     | automationPayload |automationPayload | Resource creation for Automation Tester    |
 |API_TS_112_TC_02     | functionalPayload |functionalPayload | Resource creation for Functional  Tester   |
 #|API_TS_112_TC_02     | PTPayload         |functionalPayload | Resource creation for Functional  Tester   |

      
