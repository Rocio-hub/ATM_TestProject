package UnitTests;

import java.util.ArrayList;
import java.util.List;

import synopsis.BE.Transaction;

public class MockTransaction {
	
	List<Transaction> transactionList = new ArrayList<>();
	
	public void addNewTransaction(int id, int customerNumber, int toAccountNumber, String type, String date, double amount) {
		Transaction transaction = new Transaction(id, customerNumber, toAccountNumber, type, date, amount);
		transactionList.add(transaction);
	}
	
	public List<Transaction> GetTransactionList(int customerNumber){
		List<Transaction> customerTransactionList = new ArrayList<>();
		for(Transaction transaction : transactionList) {
			if(transaction.getCustomerNumber() == customerNumber) {
				customerTransactionList.add(transaction);
			}
		}
		return customerTransactionList;
	}
}
