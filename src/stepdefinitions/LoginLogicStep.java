package stepdefinitions;

import Core.Entities.Customer;
import Core.Services.ConsoleManager;
import Core.Services.DataAccess;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginLogicStep {

	private final DataAccess mockDataAccess = mock(DataAccess.class);
	ConsoleManager consoleManager = new ConsoleManager(mockDataAccess);
	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	String a;

	@Before
	public void setUp(){
		System.setOut(new PrintStream(outContent));
	}

	 @After
	 public void restoreStream(){
		 System.setOut(System.out);
	 }

	@Given("I have a login in console")
	public void i_have_a_login_in_console() {
	    System.setOut(new PrintStream(outContent));
	}

	@When("I attempt to login with {string} as a valid username and {int} as a valid pin")
	public void i_attempt_to_login_with_as_a_valid_username_and_as_a_valid_pin(String customerName, Integer pin) throws SQLException, IOException {
		Customer c = new Customer(1, "Remi", 1234, 3000.0, 2000.0, 80);
		List<Customer> customerList = new ArrayList<Customer>();
		customerList.add(c);

		when(mockDataAccess.getAllUsers()).thenReturn(customerList);
		consoleManager.setLocalCustName(customerName);
		consoleManager.setLocalPinNumber(pin);
		consoleManager.loginLogic();
	}

	@Then("The last line printed should be {string}")
	public void the_last_line_printed_should_be(String expectedOutput) {
			String output = outContent.toString();
			String[] lines = output.split("\\r?\\n");
			String lastLine = lines[lines.length - 1];
			assertEquals(expectedOutput, lastLine);
	}
}
