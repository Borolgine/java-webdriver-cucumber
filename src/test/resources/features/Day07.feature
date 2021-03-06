@java
Feature: Interview questions

  @java1 #colump print - ok
  Scenario: Interview coding task 1-2
    Given Swap array elements

  @java2 #line print ok
  Scenario: Interview coding task 1-2
    Given Swap array elements two

  @java3 #ok
  Scenario: Interview coding task 2
    Given Divisible by

  @usps1 #USPS Scenarios - 01
  Scenario: Validate ZIP code for Portnov Computer School
    Given I navigate to "usps" page

    When I run to Lookup ZIP page by address
    And I wait for 4 sec
    And I fill out "4970 El Camino Real" street, "Los Altos" city, "CA" state
    And I wait for 4 sec
    Then I validate "94022" zip code exists in the result
    And I wait for 4 sec

