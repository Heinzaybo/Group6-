# USE CASE: 13 Organize the top 10 populated cities in a continent
## CHARACTERISTIC INFORMATION

### Goal in Context
As a forecaster I want to organize the top N populated cities in a continent where N is provided by the user so that I can view the top N populated cities in a continent.

### Scope
Organizational Black-box

### Level

Primary

### Preconditions

System has access to world database and take the cities populations in a continent of the world.

### Success End Condition

A forecaster can organize the top 10 populated cities in the continent where 10 is provided by the forecaster.

### Failed End Condition

The forecaster is unable to view the desired information or report.

### Primary Actor

A forecaster

### Trigger

A forecaster request to organize the top 10 populated cities in a continent.

## MAIN SUCCESS SCENARIO

1.      A forecaster request to organize top 10 populated cities in a continent of the world.
2.      System takes the cities population in a continent of the world from the database and organize the top 10 populated cities in a continent of the world.
3.      Then system output the result for organization.

## EXTENSIONS

2. System can’t take the data from the database.

   ​> Then the system output the empty list.

## SUB-VARIATIONS

None

## SCHEDULE

Due Date : 2/2/2024

