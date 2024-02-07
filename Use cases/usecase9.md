# USE CASE: 9 Organize all the cities in a region organized 
## CHARACTERISTIC INFORMATION

### Goal in Context
As a forecaster I want to organize all the cities in a region organized by largest population to smallest so that I can view all the cities in a region. 
### Scope

Organizational Black-box

### Level

Primary

### Preconditions

System has access to world database and take the cities populations in a region of the world.

### Success End Condition

A forecaster can organize all the cities in the region organized by largest population to smallest.

### Failed End Condition

The forecaster is unable to view the desired information or report.

### Primary Actor

A forecaster

### Trigger

A forecaster request to view all the cities in a region.


## MAIN SUCCESS SCENARIO

1.      A forecaster request to organize all the cities in a region organized
by largest population to smallest.
2.      System takes the cities in a region of the world from the database and organize by largest population to smallest.
3.      Then system output the result for organization.


## EXTENSIONS

2. System can’t take the data from the database.

   ​> Then the system output the empty list.

## SUB-VARIATIONS

None

## SCHEDULE

Due Date : 2/2/2024
