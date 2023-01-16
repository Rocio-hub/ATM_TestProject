
@tag
Feature: Login
  As a user I want to be able to login to the ATM application
  so that I can perform some transactions

  Scenario: Valid username and Valid PIN number: Successful login
    Given a user with a username "Remi" and a PIN number "1234"
    When the user attempts to login into the ATM using the console
    Then the login should be "Login successful!"

  Scenario: Invalid username and Valid PIN number: Unsuccessful login
    Given a user with a username "Bob" and a PIN number "1234"
    When the user attempts to login into the ATM using the console
    Then the login should be "Wrong Customer Number or Pin Number."

  Scenario: Valid username and invalid PIN number: Unsuccessful login
    Given a user with a username "Remi" and a PIN number "2222"
    When the user attempts to login into the ATM using the console
    Then the login should be "Wrong Customer Number or Pin Number."

  Scenario: Valid username and invalid PIN format: Unsuccessful login
    Given a user with a username "Remi" and a PIN number "abcd"
    When the user attempts to login into the ATM using the console
    Then the login should be "Invalid character(s). Only numbers."

   
