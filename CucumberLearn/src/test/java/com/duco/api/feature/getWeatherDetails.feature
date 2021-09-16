
@API-111
@ERPFI
Feature: Weather view feature
Scenario Outline: <TCNO> Contact Us scenario with different set of data <scenario>
Given user Pass the Page number which required to view the details <number>
When user request the service with pagenumber as <number>
Then service return the response as expected with status code

 Examples: 
 |TCNO                 | number     |scenario                      |
 |API_TS_111_TC_01     | 2          |to view the country code is 2 |
 |API_TS_111_TC_02     | 1          |to view the country code is 1 |

      
