# USE CASE: 5 Organize top 10 populated countries in a continent of the world.
## CHARACTERISTIC INFORMATION
 
### Goal in Context
 
As a forecaster, I want to organize top 10 populated countries in a continent where 10 is provided by the forecaster so that I can view all top 10 populated countries in the continent.
 
### Scope
 
Organizational Black-box
 
### Level
 
Primary
 
### Preconditions
 
System has access to world database and take the countries populations in a continent of the world.
 
### Success End Condition
 
A forecaster can view the countries populations in a continent which is top 10 populated countries. 10 is provided by the forecaster.
 
### Failed End Condition
 
The forecaster is unable to view the desired information or report.
 
### Primary Actor
 
A forecaster
 
### Trigger
 
A forecaster has been requested to fulfill the task by the client.
 
## MAIN SUCCESS SCENARIO
 
1. A forecaster request to organize top 10 populated countries in a continent.
2. System takes the population of the countries in a continent from the database and organize the top 10 populated countries in a continent.
3. Then system output the result for organization.
 
## SUB-VARIATIONS
 
2. System can’t take the data from the database.

​    > Then the system output the empty list.
 
## SCHEDULE
 
Due Date: 2/2/2024
