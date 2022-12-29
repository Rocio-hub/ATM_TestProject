package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Core.Entities.Customer;
import Core.Entities.Transaction;
import Core.Services.ConsoleManager;
import Core.Services.DataAccess;

public class ConsoleManagerTests {

	private ConsoleManager consoleManager;
	private Customer mockCustomer;
	private DataAccess mockDataAccess;
	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUp() {
		System.setOut(new PrintStream(outContent));
		mockCustomer = mock(Customer.class);
		mockDataAccess = mock(DataAccess.class);
		consoleManager = new ConsoleManager(mockDataAccess);
	}

	@Test
	public void loginLogic_ValidUsername_ValidPin_ReturnsTrue() throws IOException, SQLException {

		// Arrange: Create mock objects
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer(1, "Bob", 1234, 3000.0, 2000.0, 80.0));
		when(mockDataAccess.getAllUsers()).thenReturn(customers);

		// Act: Set up test input and expected output
		consoleManager.setLocalCustName("Bob");
		consoleManager.setLocalPinNumber(1234);
		consoleManager.setDataAccess(mockDataAccess);

		// Assert: Invoke the method and assert the output
		assertTrue(consoleManager.loginLogic());
		assertEquals("Bob", consoleManager.getCustomer().getCustomerName());
		assertEquals(1234, consoleManager.getCustomer().getPin());
	}

	@Test
	public void loginLogic_InvalidUserName_InvalidPin_ReturnsFalse() throws IOException, SQLException {

		// Arrange: Create mock objects
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer(1, "Bob", 1234, 3000.0, 2000.0, 80.0));
		when(mockDataAccess.getAllUsers()).thenReturn(customers);

		// Act: Set up test input and expected output
		consoleManager.setLocalCustName("Alice");
		consoleManager.setLocalPinNumber(5678);
		consoleManager.setDataAccess(mockDataAccess);

		// Assert: Invoke the method and assert the output
		assertTrue(!consoleManager.loginLogic());
	}

	@Test
	public void loginLogic_ValidUserName_InvalidPin_ReturnsFalse() throws IOException, SQLException {

		// Arrange: Create mock objects
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer(1, "Bob", 1234, 3000.0, 2000.0, 80.0));
		when(mockDataAccess.getAllUsers()).thenReturn(customers);

		// Act: Set up test input and expected output
		consoleManager.setLocalCustName("Bob");
		consoleManager.setLocalPinNumber(5678);
		consoleManager.setDataAccess(mockDataAccess);

		// Assert: Invoke the method and assert the output
		assertTrue(!consoleManager.loginLogic());
	}

	@Test
	public void loginLogic_InvalidUserName_ValidPin_ReturnsFalse() throws IOException, SQLException {

		// Arrange: Create mock objects
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer(1, "Bob", 1234, 3000.0, 2000.0, 80.0));
		when(mockDataAccess.getAllUsers()).thenReturn(customers);

		// Act: Set up test input and expected output
		consoleManager.setLocalCustName("Alice");
		consoleManager.setLocalPinNumber(1234);
		consoleManager.setDataAccess(mockDataAccess);

		// Assert: Invoke the method and assert the output
		assertTrue(!consoleManager.loginLogic());
	}

	@Test
	public void loginLogic_5678_EmptyUserName_ValidPin_ReturnsFalse() throws IOException, SQLException {

		// Arrange: Create mock objects
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer(1, "Bob", 1234, 3000.0, 2000.0, 80.0));
		when(mockDataAccess.getAllUsers()).thenReturn(customers);

		// Act: Set up test input and expected output
		consoleManager.setLocalCustName("");
		consoleManager.setLocalPinNumber(1234);
		consoleManager.setDataAccess(mockDataAccess);

		// Assert: Invoke the method and assert the output
		assertTrue(!consoleManager.loginLogic());
	}

	@Test
	public void loginLogic_Bob_ValidUserName_EmptyPin_ReturnsFalse() throws IOException, SQLException {

		// Arrange: Create mock objects
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer(1, "Bob", 1234, 3000.0, 2000.0, 80.0));
		when(mockDataAccess.getAllUsers()).thenReturn(customers);

		// Act: Set up test input and expected output
		consoleManager.setLocalCustName("Bob");
		consoleManager.setDataAccess(mockDataAccess);

		// Assert: Invoke the method and assert the output
		assertTrue(!consoleManager.loginLogic());
	}

	@Test
	public void loginLogic_EmptyUserName_EmptyPin_ReturnsFalse() throws IOException, SQLException {

		// Arrange: Create mock objects
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer(1, "Bob", 1234, 3000.0, 2000.0, 80.0));
		when(mockDataAccess.getAllUsers()).thenReturn(customers);

		// Act: Set up test input and expected output
		consoleManager.setLocalCustName("");
		consoleManager.setDataAccess(mockDataAccess);

		// Assert: Invoke the method and assert the output
		assertTrue(!consoleManager.loginLogic());
	}

	@Test
	public void viewBalance_2000() throws IOException, SQLException {

		// Arrange: Create mock objects
		Customer customer = new Customer(1, "Bob", 1234, 3000.0, 2000.0, 80.0);
		when(mockDataAccess.getUserById(customer.getId())).thenReturn(customer);

		// Act: Set up test input and expected output
		consoleManager.setCustomer(customer);
		consoleManager.setDataAccess(mockDataAccess);

		// Assert: Invoke the method and assert the output
		if (consoleManager.loginLogic()) {
			consoleManager.viewBalance();
			double expectedOutput = 2000.0;
			double delta = 0.0;
			assertEquals(expectedOutput, consoleManager.getCustomer().getBalance(), delta);
		}
	}

	// 1. positive balance 2000 2000
	// 2. zero balance 0 0
	// 3. negative balance -$50 -$50
	// 4. very large balance $1,000,000 $1,000,000
	// 5. decimal points $12.35" $12.35"
	// 6. very large decimal points: input: $12.345678 output: $12.35"

	@Test
	public void makeDeposit_Positive() throws IOException, SQLException {

		// Arrange: Create mock objects
		Customer customer = new Customer(1, "Bob", 1234, 3000.0, 2000.0, 80.0);
		when(mockDataAccess.getAllTransactionsById(customer.getId())).thenReturn(new ArrayList<Transaction>());
		when(mockDataAccess.makeDeposit(customer.getId(), 500)).thenReturn(2500.0);

		// Act: Set up test input and expected output
		consoleManager.setDataAccess(mockDataAccess);
		consoleManager.setCustomer(customer);

		// Assert: Invoke the method and assert the output
		if (consoleManager.loginLogic()) {
			consoleManager.viewBalance();
			double expectedOutput = 2500.0;
			assertTrue(expectedOutput == (double) consoleManager.getCustomer().getBalance());
		}
	}

	// 1. positive amount: user has 100 input 50 output 150
	// 2. deposit of zero: user has 100 input 0 output 100
	// 3. negative amount: user has 100 input -50 output error message
	// 4. deposit large amount: user has 100 input 1000000 output 1 000 100
	// 5. amount with decimal points: user has 100 input 12.34 output 112.34
	// 6. large amount of decimal points: user has 100 input 12.345678 output:
	// 112.34

	@Test
	public void withdrawFunds() throws IOException, SQLException {

		// Arrange: Create mock objects
		when(mockCustomer.getBalance()).thenReturn(3000.0);
		when(mockDataAccess.getUserById(anyInt())).thenReturn(mockCustomer);
		when(mockDataAccess.getUserById(anyInt()).getDailyLimit()).thenReturn((double) 2000.0);

		// no estoy haciendo make withdrawal????

		// Act: Set up test input and expected output
		consoleManager.setDataAccess(mockDataAccess);
		consoleManager.setCustomer(mockCustomer);
		consoleManager.setATMAvailableCash(15000);
		consoleManager.withdrawFunds(500);

		// Assert: Invoke the method and assert the output
		verify(mockDataAccess).createTransaction(any(Transaction.class));
	}

	@Test
	public void sendFunds() throws IOException, SQLException {

		// Arrange: Create mock objects
		when(mockCustomer.getBalance()).thenReturn(3000.0);
		when(mockDataAccess.getUserById(anyInt())).thenReturn(mockCustomer);
		when(mockDataAccess.getUserById(anyInt()).getDailyLimit()).thenReturn((double) 2000.0);

		// Act: Invoke the method
		consoleManager.setDataAccess(mockDataAccess);
		consoleManager.setCustomer(mockCustomer);
		consoleManager.setATMAvailableCash(15000);
		consoleManager.sendFunds(8888, 500);

		// Assert: Verify that the method behaved as expected
		verify(mockDataAccess).createTransaction(argThat(t -> t.getType().equals("SEND") && t.getAmount() == 500));
	}

	// 1. sufficient balance, not reached daily limit, money is sent
	// 2. insufficient balance, not reached dailylimit, money is not sent
	// 3. sufficient balance, reached daily limit, money is not sent
	// 4. test that everything correct a transaction is created
	// 5. test that everything correct the balance of the user is updated

	public class seeTransactionsTests {

		@Test
		public void seeTransactions_WithTransactions_ShouldPrintTransactions() throws IOException, SQLException {

			// Arrange: Create mock objects
			Customer mockCustomer = mock(Customer.class); // Create a mock object for the Customer class
			when(mockDataAccess.getAllTransactionsById(1))
					.thenReturn(Arrays.asList(new Transaction(1, 1, 8888, "WITHDRAW", "29/12/22", 500),
							new Transaction(2, 1, 9999, "SEND", "30/12/22", 300)));
			when(mockCustomer.getId()).thenReturn(1);

			// Act: Set up test input and expected output
			consoleManager.setDataAccess(mockDataAccess);
			consoleManager.setCustomer(mockCustomer);
			consoleManager.seeTransactions();

			// Assert: Verify that the method prints the expected output
			String expectedOutput = "1\n" + "--------------------------------------\n"
					+ "| ID | TYPE  | DATE   | AMOUNT   | TO ACCOUNT |\n"
					+ "| 1  | WITHDRAW  | 29/12/22 | 500.00   | 8888 |\n" + "--------------------------------------\n"
					+ "| 2  | SEND  | 30/12/22 | 300.00   | 9999 | \n" + "--------------------------------------\n";
			assertEquals(expectedOutput, outContent.toString());

			// este pasa pero no se sabe por qué
		}
	}

	@Test
	public void getCustomer_ShouldReturnCorrectCustomer() {
		consoleManager.setCustomer(mockCustomer);
		assertEquals(mockCustomer, consoleManager.getCustomer());
	}

	@Test
	public void setCustomer_ShouldSetCorrectCustomer() {
		consoleManager.setCustomer(mockCustomer);
		assertEquals(mockCustomer, consoleManager.getCustomer());
	}

	@Test
	public void getLocalCustName_ShouldReturnCorrectName() {
		consoleManager.setLocalCustName("Alice");
		assertEquals("Alice", consoleManager.getLocalCustName());
	}

	@Test
	public void setLocalCustName_ShouldSetCorrectName() {
		consoleManager.setLocalCustName("Alice");
		assertEquals("Alice", consoleManager.getLocalCustName());
	}

	@Test
	public void getLocalPinNumber_ShouldReturnCorrectPinNumber() {
		consoleManager.setLocalPinNumber(1234);
		assertEquals(1234, consoleManager.getLocalPinNumber());
	}

	@Test
	public void setLocalPinNumber_ShouldSetCorrectPinNumber() {
		consoleManager.setLocalPinNumber(1234);
		assertEquals(1234, consoleManager.getLocalPinNumber());
	}

	@Test
	public void getCustomerList_ShouldReturnCorrectList() {
		List<Customer> customerList = Arrays.asList(mockCustomer);
		consoleManager.setCustomerList(customerList);
		assertEquals(customerList, consoleManager.getCustomerList());
	}

	@Test
	public void setCustomerList_ShouldSetCorrectList() {
		List<Customer> customerList = Arrays.asList(mockCustomer);
		consoleManager.setCustomerList(customerList);
		assertEquals(customerList, consoleManager.getCustomerList());
	}

	@Test
	public void getSelection_ShouldReturnCorrectSelection() {
		consoleManager.setSelection(1);
		assertEquals(1, consoleManager.getSelection());
	}

	@Test
	public void setSelection_ShouldSetCorrectSelection() {
		consoleManager.setSelection(1);
		assertEquals(1, consoleManager.getSelection());
	}

	@Test
	public void setATMAvailableCash_ShouldSetCorrectAvailableCash() {
		consoleManager.setATMAvailableCash(100);
		assertEquals(100, consoleManager.getATMAvailableCash(), 0.0);
	}
}
