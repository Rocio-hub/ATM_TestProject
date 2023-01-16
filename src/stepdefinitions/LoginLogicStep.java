package stepdefinitions;

import Core.Entities.Customer;
import Core.Services.ConsoleManager;
import Core.Services.DataAccess;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginLogicStep {

    @Mock
    private final DataAccess mockDataAccess = mock(DataAccess.class);

    final ConsoleManager consoleManager = new ConsoleManager(mockDataAccess);
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    Customer customerValid;
    Customer customerTest;
    List<Customer> customerList;
    String lastLine ="";

    @Before
    public void setUp(){
        System.setOut(new PrintStream(outContent));
    }
    @After
    public void restoreStream(){
        System.setOut(System.out);
    }
    @Given("a user with a username {string} and a PIN number {string}")
    public void a_user_with_a_username_and_a_pin_number(String username, String pin) throws SQLException {
        int myPin;
        try {
            myPin = Integer.parseInt(pin);
        } catch (NumberFormatException e) {
            myPin = 0;
            lastLine = "Invalid character(s). Only numbers.";
        }
        System.setOut(new PrintStream(outContent));
        customerValid = new Customer(1, "Remi", 1234, 3000.0, 500.0, 80.0);
        customerTest = new Customer(1, username, myPin, 3000.0, 500.0, 80.0);

        customerList = new ArrayList<Customer>();
        customerList.add(customerValid);
        when(mockDataAccess.getAllUsers()).thenReturn(customerList);
    }
    @When("the user attempts to login into the ATM using the console")
    public void the_user_attempts_to_login_into_the_atm_using_the_console() throws IOException, SQLException {
        consoleManager.setLocalCustName(customerTest.getCustomerName());
        consoleManager.setLocalPinNumber(customerTest.getPin());
        consoleManager.loginLogic();
    }
    @Then("the login should be {string}")
    public void the_login_should_be(String expectedResult) {
        if (lastLine.isEmpty()) {
            String output = outContent.toString();
            String[] lines = output.split("\\r?\\n");
            lastLine = lines[lines.length - 1];
        }
        assertEquals(expectedResult, lastLine);
    }
}
