
@tag
Feature: Login
  As a user
  I want to be able to login to the ATM

  Scenario: Successful login
    Given I have a login in console
    When I attempt to login with "Remi" as a valid username and 1234 as a valid pin
    Then The last line printed should be "Login successful!"
   
