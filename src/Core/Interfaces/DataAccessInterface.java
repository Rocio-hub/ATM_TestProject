package Core.Interfaces;

import java.sql.SQLException;
import java.util.List;

import Core.Entities.Customer;
import Core.Entities.Transaction;

public interface DataAccessInterface {
	
	public Customer getUserById(int id) throws SQLException;
	public List<Customer> getAllUsers() throws SQLException;
	public double updateMoneyUsedById(int id, double moneyUsed) throws SQLException;
	public double makeWithdrawal(int id, double amount) throws SQLException;
	public double makeDeposit(int id, double amount) throws SQLException;
	public double sendFunds(int id, int accountNumber, int amount) throws SQLException;
	
	public void createTransaction(Transaction transaction) throws SQLException;
	public List<Transaction> getAllTransactionsById(int id) throws SQLException;

}
