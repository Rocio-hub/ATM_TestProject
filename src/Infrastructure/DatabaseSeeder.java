package Infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSeeder {

	private final Connection connection;

	public DatabaseSeeder(Connection connection) {
		this.connection = connection;
	}
	
	public void createCustomersTable() throws SQLException {
		String sql = "DROP TABLE IF EXISTS customers";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.execute();
		};
		
		String sql2 = "CREATE TABLE customers (id INT NOT NULL AUTO_INCREMENT, customer_name VARCHAR(30) NOT NULL, pin INT NOT NULL, balance DOUBLE NOT NULL, daily_limit DOUBLE NOT NULL, money_used DOUBLE NOT NULL, PRIMARY KEY(id))";
		try (PreparedStatement statement = connection.prepareStatement(sql2)) {
			statement.execute();
		}		
	}
	
	public void createTransactionsTable() throws SQLException {
		String sql = "DROP TABLE IF EXISTS transactions";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.execute();
		};
		
		String sql2 = "CREATE TABLE transactions (id INT NOT NULL AUTO_INCREMENT, customer_id INT NOT NULL, to_account_number INT NOT NULL, type VARCHAR(30) NOT NULL, date VARCHAR(20) NOT NULL, amount DOUBLE NOT NULL, PRIMARY KEY (id))";
		try (PreparedStatement statement = connection.prepareStatement(sql2)) {
			statement.execute();
		}		
	}

	public void seedCustomers() throws SQLException {
		String sql = "INSERT INTO customers (id, customer_name, pin, balance, daily_limit, money_used) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, 7);
			statement.setString(2, "Remi");
			statement.setInt(3, 1234);
			statement.setDouble(4, 2000.0);
			statement.setDouble(5, 500.0);
			statement.setDouble(6, 70.0);
			statement.executeUpdate();

			statement.setInt(1, 8);
			statement.setString(2, "Mads");
			statement.setInt(3, 5678);
			statement.setDouble(4, 2500);
			statement.setDouble(5, 500);
			statement.setDouble(6, 0);
			statement.executeUpdate();
		}
	}
	
	/*public void seedTransactions() throws SQLException {
		String sql = "INSERT INTO customers (id, customer_name, pin, balance, daily_limit, money_used) VALUES (?, ?, ?, ?, ? ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, 1);
			statement.setString(2, "Remi");
			statement.setInt(3, 1234);
			statement.setDouble(4, 2000);
			statement.setDouble(5, 500);
			statement.setDouble(6, 70);
			statement.executeUpdate();

			statement.setInt(1, 2);
			statement.setString(2, "Mads");
			statement.setInt(3, 5678);
			statement.setDouble(4, 2500);
			statement.setDouble(5, 500);
			statement.setDouble(6, 0);
			statement.executeUpdate();
		}
	}*/
}
