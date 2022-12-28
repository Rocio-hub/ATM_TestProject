package UnitTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import Core.Entities.Customer;
import Core.Services.ConsoleManager;
import Core.Services.DataAccess;

public class ConsoleManagerTest {

	@Test
	public void testGetLogin() throws IOException, SQLException {
		DataAccess dataAccess = mock(DataAccess.class);
		ConsoleManager cm = new ConsoleManager(dataAccess);
		
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer(1, "Alice", 12345, 2000.0, 123456789, 1000.0));
		
		Scanner menuInput = new Scanner("Alice\n12345\n");		
		
		when(dataAccess.getAllUsers()).thenReturn(customers);
		
		cm.setDataAccess(dataAccess);
		cm.getLogin();
		
		assertEquals("Alice", cm.getLocalCustName());
		assertEquals(12345, cm.getLocalPinNumber());		
	}
}
