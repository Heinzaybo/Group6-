# USE CASE: 24 Organize the population of people in cities in each region.
## CHARACTERISTIC INFORMATION

### Goal in Context

As a forecaster, I want to organize the population of people, people living in cities, and people not living in cities in each region so that I can view detail population.
### Scope

Organizational Black-box

### Level

Primary

### Preconditions

System has access to each region database and take the population of people, people living in cities, and people not living in cities in each region.

### Success End Condition

A forecaster can organize the population of people, people living in cities, and people not living in cities in each region.
### Failed End Condition

The forecaster is unable to view the desired information or report.
### Primary Actor

A forecaster

### Trigger

A forecaster request to view the population of people in cities in each region.

## MAIN SUCCESS SCENARIO

1.  A forecaster request to organize the population of people in cities in each region.
2.  System takes the population of people in cities in each region.
3.  Then system output the result for organization.

## EXTENSIONS
 
2. System can’t take the data from the database.

   ​> Then the system output the empty list.

1. In the database, population category is not included.
  > Find the database containing information on population categories.

## SUB-VARIATIONS

None

## SCHEDULE

Due Date : 2/2/2024
