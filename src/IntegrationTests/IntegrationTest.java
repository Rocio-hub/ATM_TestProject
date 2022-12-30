package IntegrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import Core.Entities.Customer;
import Core.Entities.Transaction;
import Core.Services.ConsoleManager;
import Core.Services.DataAccess;

public class IntegrationTest {

	// The class under test
	private ConsoleManager consoleManager;
	private DataAccess mockDataAccess;

	// The class that is integrated by my class
	private Customer customer;

	@Before
	public void setUp() {
		// Setup the customer class
		customer = new Customer(1, "Alice", 9999, 4000.0, 2000.0, 20.0);

		// Setup the ConsoleManager class
		mockDataAccess = mock(DataAccess.class);
		consoleManager = new ConsoleManager(mockDataAccess);
		consoleManager.setCustomer(customer);
	}

	@Test
	public void test1() {
		assertEquals(1, consoleManager.getCustomer().getId());
	}

	@Test
	public void test2() throws SQLException {
		when(mockDataAccess.getUserById(customer.getId())).thenReturn(customer);
		consoleManager.withdrawFunds(500.0);
		assertEquals(14500.0, consoleManager.getATMAvailableCash());
	}

}
