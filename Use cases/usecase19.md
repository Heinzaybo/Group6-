# USE CASE: 19 Organize all the capital cities in a region
## CHARACTERISTIC INFORMATION

### Goal in Context

As a forecaster, I want to organize all the capital cities in a region by largest to smallest so that I can view all the capital cities in a region.
### Scope

Organizational Black-box

### Level

Primary

### Preconditions

System has access to world database and take the capital cities in a region.
### Success End Condition

A forecaster can organize the capital cities in a region by largest population to smallest.

### Failed End Condition

The forecaster is unable to view the desired information or report.
### Primary Actor

A forecaster

### Trigger
 
A forecaster has been requested to fulfill the task by the client.

## MAIN SUCCESS SCENARIO

1.  A forecaster request to organize by largest population to smallest the capital cities population in a region.
2.  System takes the all the capital cities in a region from the database and organize the largest to smallest population.
3.  Then system output the result for organization.

## EXTENSIONS
 
2. System can’t take the data from the database.

    > Then the system output the empty list.
## SUB-VARIATIONS

None

## SCHEDULE

Due Date : 2/2/2024
