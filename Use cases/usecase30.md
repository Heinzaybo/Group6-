# USE CASE: 30 See the population of a district
## CHARACTERISTIC INFORMATION

### Goal in Context

As a forecaster, I want to see the population of a district so that I can easily access the population information.

### Scope

Organizational Black-box

### Level

Primary

### Preconditions

System has access to world database and take the population of a district.

### Success End Condition

A forecaster can view the population of a district.

### Failed End Condition

The forecaster is unable to view the desired information or report.

### Primary Actor

A forecaster

### Trigger

A forecaster request to view the population of a district and also want to access the information of population easily.

## MAIN SUCCESS SCENARIO

*put here the steps of the scenario from trigger to goal delivery, and any cleanup after*

1.  A forecaster request to view the population of a district.
2.  System takes the population of the district from the database.
3.  Then system output the result for organization.

## EXTENSIONS
 
2. System can’t take the data from the database.

    > Then the system output the empty list.

## SUB-VARIATIONS

None

## SCHEDULE

Due Date: 2/2/2024
