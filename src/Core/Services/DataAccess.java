package Core.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Core.Entities.Customer;
import Core.Entities.Transaction;
import Core.Interfaces.DataAccessInterface;

public class DataAccess implements DataAccessInterface {

	private Connection connection = null;

	DataAccess(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Customer getUserById(int id) throws SQLException {
		String sql = "SELECT * FROM customers WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int userId = resultSet.getInt("id");
					String userName = resultSet.getString("customer_name");
					int userPin = resultSet.getInt("pin");
					double userBalance = resultSet.getDouble("balance");
					double userDailyLimit = resultSet.getDouble("daily_limit");
					double userMoneyUsed = resultSet.getDouble("money_used");
					return new Customer(userId, userName, userPin, userBalance, userDailyLimit, userMoneyUsed);					
				} else {
					return null;
				}
			}
		}
	}

	@Override
	public List<Customer> getAllUsers() throws SQLException {
		List<Customer> custList = new ArrayList<Customer>();

		// Create a prepared statement
		String sql = "SELECT * FROM customers";
		PreparedStatement statement = connection.prepareStatement(sql);

		// Execute the statement and retrieve the results
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			int userId = resultSet.getInt("id");
			String userName = resultSet.getString("customer_name");
			int userPin = resultSet.getInt("pin");
			double userBalance = resultSet.getDouble("balance");
			double userDailyLimit = resultSet.getDouble("daily_limit");
			double userMoneyUsed = resultSet.getDouble("money_used");
			Customer customer = new Customer(userId, userName, userPin, userBalance, userDailyLimit, userMoneyUsed);
			custList.add(customer);
		}
		return custList;
	}

	@Override
	public double updateMoneyUsedById(int id, double moneyUsed) throws SQLException {
		String sql = "UPDATE customers SET money_used = ? WHERE id = ?";

		Customer c = getUserById(id);
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setDouble(1, c.getMoneyUsed() + moneyUsed);
			statement.setInt(2, id);
			statement.executeUpdate();
		}		
		return c.getMoneyUsed() + moneyUsed;
	}
	
	@Override
	public double makeWithdrawal(int id, double amount) throws SQLException {
		String sql = "UPDATE customers SET balance = ? WHERE id = ?";

		Customer c = getUserById(id);
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setDouble(1, c.getBalance() - amount);
			statement.setInt(2, id);
			statement.executeUpdate();
		}
		return c.getBalance() - amount;
	}

	@Override
	public double makeDeposit(int id, double amount) throws SQLException {
		String sql = "UPDATE customers SET balance = ? WHERE id = ?";

		Customer c = getUserById(id);
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setDouble(1, c.getBalance() + amount);
			statement.setInt(2, id);
			statement.executeUpdate();
		}
		return getUserById(id).getBalance();
	}

	@Override
	public double sendFunds(int id, int accountNumber, double amount) throws SQLException {
		String sql = "UPDATE customers SET balance = ? WHERE id = ?";

		Customer c = getUserById(id);
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setDouble(1, c.getBalance() - amount);
			statement.setInt(2, id);
			statement.executeUpdate();
		}
		return c.getBalance() - amount;
	}

	@Override
	public List<Transaction> getAllTransactionsById(int id) throws SQLException {
		List<Transaction> transactionList = new ArrayList<Transaction>();

		// Create a prepared statement
		String sql = "SELECT * FROM transactions WHERE customer_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int transactionId = resultSet.getInt("id");
					int transactionCustomerId = resultSet.getInt("customer_id");
					int transactionToAccountNumber = resultSet.getInt("to_account_number");
					String transactionType = resultSet.getString("type");
					String transactionDate = resultSet.getString("date");
					double transactionAmount = resultSet.getDouble("amount");
					Transaction transaction = new Transaction(transactionId, transactionCustomerId,
							transactionToAccountNumber, transactionType, transactionDate, transactionAmount);
					transactionList.add(transaction);
				}
			}
		}
		return transactionList;
	}

	@Override
	public void createTransaction(Transaction transaction) throws SQLException {
		// Create a prepared statement
		String sql = "INSERT INTO transactions (id, customer_id, to_account_number, type, date, amount) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, transaction.getId());
		statement.setInt(2, transaction.getCustomerId());
		statement.setInt(3, transaction.getToAccountNumber());
		statement.setString(4, transaction.getType());
		statement.setString(5, transaction.getDate());
		statement.setDouble(6, transaction.getAmount());

		// Execute the statement
		statement.executeUpdate();
	}
}