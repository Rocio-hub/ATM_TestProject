package Core.Services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Infrastructure.DatabaseSeeder;

public class Main extends ConsoleManager {
	
	public Main(DataAccess dataAccess) {
		super(dataAccess);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, SQLException {		
		Connection connection;
		
		

		// Load the JDBC driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		// Establish a connection to the database
		String url = "jdbc:mysql://localhost:3306/atmdb";
		String username = "root";
		String password = "root";

		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		// Create an instance of the DatabaseSeeder class and seed the database
		DatabaseSeeder seeder = new DatabaseSeeder(connection);
		seeder.createCustomersTable();
		seeder.createTransactionsTable();
		seeder.seedCustomers();
		seeder.seedTransactions();
		DataAccess dataAccess = new DataAccess(connection);

		ConsoleManager optionMenu = new ConsoleManager(dataAccess);

		optionMenu.getLogin();
	}
}
