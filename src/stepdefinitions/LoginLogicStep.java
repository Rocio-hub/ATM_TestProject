package stepdefinitions;

import Core.Entities.Customer;
import Core.Services.ConsoleManager;
import Core.Services.DataAccess;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginLogicStep {

	private final DataAccess mockDataAccess = mock(DataAccess.class);
	ConsoleManager consoleManager = new ConsoleManager(mockDataAccess);

	@Given("I have a login in console")
	public void i_have_a_login_in_console() {
	    System.out.println("Step 1: See the login");
	}

	@When("I attempt to login with {string} as a valid username and {int} as a valid pin")
	public void i_attempt_to_login_with_as_a_valid_username_and_as_a_valid_pin(String customerName, Integer pin) throws SQLException, IOException {
		System.out.println("Step 2: Insert valid credentials");
		Customer c = new Customer(1, "Remi", 1234, 3000.0, 2000.0, 80);
		List<Customer> customerList = new ArrayList<Customer>();
		customerList.add(c);

		when(mockDataAccess.getAllUsers()).thenReturn(customerList);
		consoleManager.setLocalCustName(customerName);
		consoleManager.setLocalPinNumber(pin);
		boolean result = consoleManager.loginLogic();

		assertEquals(true, result);
	}

	@Then("I should be logged in to my account")
	public void i_should_be_logged_in_to_my_account() {
		System.out.println("Step 3: Login successful");
	}
}
