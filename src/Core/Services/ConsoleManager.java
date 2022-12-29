package Core.Services;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Core.Entities.Customer;
import Core.Entities.Transaction;

public class ConsoleManager {
	private DataAccess dataAccess;
	private Customer currentCustomer;

	public ConsoleManager(DataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}

	private int ATM_AVAILABLE_CASH = 15000;

	private Scanner menuInput = new Scanner(System.in);
	private DecimalFormat moneyFormat = new DecimalFormat("###,##0.00' DKK'");
	private int localPinNumber, selection;
	private String localCustName;
	private Customer customer;
	private List<Customer> customerList;;

	/* Validate Login information customer number and pin number */

	public void loginConsole() throws IOException, SQLException {
		int x = 1;

		do {
			try {
				System.out.printf("--------------------------------------%n");
				System.out.printf("     Welcome to the ATM project! %n");
				System.out.printf("--------------------------------------%n");
				System.out.printf("    Enter your Customer Name: %n");
				System.out.printf("--------------------------------------%n");
				setLocalCustName(menuInput.next());
				System.out.printf("--------------------------------------%n");
				System.out.printf("       Enter your PIN Number: %n");
				System.out.printf("--------------------------------------%n");
				setLocalPinNumber(menuInput.nextInt());
				System.out.printf("--------------------------------------%n");
			} catch (Exception e) {
				System.out.println("\n" + "Invalid character(s). Only numbers." + "\n");
				x = 2;
			}
			if (loginLogic()) {
				goToMenuConsole();
			}
		} while (x == 1);
	}

	public boolean loginLogic() throws IOException, SQLException {
		for (Customer customer : dataAccess.getAllUsers()) {
			System.out.println(getLocalCustName());
			System.out.println(customer.getCustomerName());
			if (getLocalCustName().equals(customer.getCustomerName()) && getLocalPinNumber() == customer.getPin()) {
				currentCustomer = customer;
				//this.setCustomer(customer);

				setLocalCustName("");
				setLocalPinNumber(0);
				return true;
			}
		}
		System.out.println("\n" + "Wrong Customer Number or Pin Number." + "\n");
		return false;
	}

	/* Display Menu */

	public void goToMenuConsole() throws SQLException {
		int x = 1;
		do {
			System.out.printf(">>Select the action you want to perform: %n");
			System.out.printf("--------------------------------------%n");
			System.out.printf("  | %-7s | %-20s | %n", "TYPE", "SELECTION");
			System.out.printf("--------------------------------------%n");
			System.out.printf("  | %-7d | %-20s | %n", 1, "VIEW BALANCE");
			System.out.printf("  | %-7d | %-20s | %n", 2, "MAKE DEPOSIT");
			System.out.printf("  | %-7d | %-20s | %n", 3, "WITHDRAW FUNDS");
			System.out.printf("  | %-7d | %-20s | %n", 4, "SEND FUNDS");
			System.out.printf("  | %-7d | %-20s | %n", 5, "SEE MY TRANSACTIONS");
			System.out.printf("  | %-7d | %-20s | %n", 6, "EXIT");
			System.out.printf("--------------------------------------%n");
			System.out.printf(">> Type your choice: %n");
			System.out.printf("--------------------------------------%n");

			setSelection(menuInput.nextInt());

			switch (getSelection()) {
			case 1:
				System.out.printf("--------------------------------------%n");
				System.out.printf("     Check Your Account Balance %n");
				System.out.printf("--------------------------------------%n");
				viewBalance();
				break;

			case 2:
				System.out.printf("--------------------------------------%n");
				System.out.printf("           Make A Deposit %n");
				System.out.printf("--------------------------------------%n");
				makeDepositConsole();
				break;

			case 3:
				withdrawFundsConsole();
				break;

			case 4:
				sendFundsConsole();
				break;

			case 5:
				seeTransactions();
				break;

			case 6:
				System.out.printf("--------------------------------------%n");
				System.out.printf("Thank you for using this ATM!%n");
				System.out.printf("BYE BYE%n");
				System.out.printf("--------------------------------------%n");
				x = 2;
				break;

			default:
				System.out.println("\n" + "Invalid Choice." + "\n");
			}
		} while (x == 1);
	}

	/* View balance. No requirements */

	public void viewBalance() throws SQLException {
		String balance = moneyFormat.format(dataAccess.getUserById(currentCustomer.getId()).getBalance());

		viewBalanceConsole(balance);
	}

	private void viewBalanceConsole(String balance) throws SQLException {
		System.out.printf("  | %-30s | %n", "FUNDS");
		System.out.printf("--------------------------------------%n");
		System.out.printf("  | %-30s | %n", balance);
		System.out.printf("--------------------------------------%n");
	}

	/* Make a deposit. No requirements */

	private void makeDepositConsole() throws SQLException {
		System.out.println(">> Insert the amount");
		System.out.printf("--------------------------------------%n");
		int amount = menuInput.nextInt();

		makeDeposit(amount);
	}

	private void makeDeposit(double amount) throws SQLException {
		int transactionId = dataAccess.getAllTransactionsById(currentCustomer.getId()).size() + 1;
		Transaction transaction = new Transaction(transactionId, getCustomer().getId(), 0, "DEPOSIT", "26/11/22",
				amount);
		dataAccess.createTransaction(transaction);

		System.out.println(dataAccess.getAllTransactionsById(currentCustomer.getId()));

		double newBalance = dataAccess.makeDeposit(currentCustomer.getId(), amount);
		String temp = moneyFormat.format(newBalance);

		System.out.printf("--------------------------------------%n");
		System.out.printf("  | %-12s | %-15s | %n", "DEPOSIT", "NEW BALANCE");
		System.out.printf("--------------------------------------%n");
		System.out.printf("  | %-12s | %-15s | %n", amount, temp);
		System.out.printf("--------------------------------------%n");
	}

	/*
	 * Withdraw funds. Balance should be >= input Available cash should be >= input
	 * Daily limit should be >= input
	 */

	private void withdrawFundsConsole() throws SQLException {
		System.out.printf("--------------------------------------%n");
		System.out.println(">> Insert the amount");
		System.out.printf("--------------------------------------%n");
		int amount = menuInput.nextInt();

		withdrawFunds(amount);
	}

	public void withdrawFunds(double amount) throws SQLException {
		if (dataAccess.getUserById(currentCustomer.getId()).getBalance() >= amount
				&& ATM_AVAILABLE_CASH >= amount
				&& dataAccess.getUserById(currentCustomer.getId()).getDailyLimit() >= amount) {
			
			dataAccess.updateMoneyUsedById(currentCustomer.getId(), amount);

			int transactionId = dataAccess.getAllTransactionsById(currentCustomer.getId()).size() + 1;
			Transaction transaction = new Transaction(transactionId, getCustomer().getId(), 0, "WITHDRAW",
					"29/12/22", amount);
			dataAccess.createTransaction(transaction);

			double newBalance = dataAccess.makeWithdrawal(currentCustomer.getId(), amount);
			String temp = moneyFormat.format(newBalance);
			ATM_AVAILABLE_CASH -= amount;

			System.out.printf("--------------------------------------%n");
			System.out.printf("  | %-12s | %-15s | %n", "WITHDRAWN", "NEW BALANCE");
			System.out.printf("--------------------------------------%n");
			System.out.printf("  | %-12s | %-15s | %n", amount, temp);
			System.out.printf("--------------------------------------%n");

		} else {
			//User has less money than withdrawal amount
			if (getCustomer().getBalance() < amount) {
				System.out.printf("--------------------------------------%n");
				System.out.printf("         Insuficient funds.%n");
				System.out.printf("--------------------------------------%n");
			}
			//ATM has less money that withdrawal amount
			else if (ATM_AVAILABLE_CASH < amount) {
				System.out.printf("--------------------------------------%n");
				System.out.printf("         This ATM cannot withdraw that amount.%n");
				System.out.printf("--------------------------------------%n");
			}

			else {
				//User has reached daily limit amount
				String tempUsed = moneyFormat.format(getCustomer().getMoneyUsed());
				String tempLimit = moneyFormat.format(getCustomer().getDailyLimit());

				System.out.printf("--------------------------------------%n");
				System.out.printf("         You cannot withdraw that amount.%n");
				System.out.printf("--------------------------------------%n");
				System.out.printf("  | %-5s | %-11s | %-11s | %n", "WITHDRAWN", "MONEY USED", "DAILY LIMIT");
				System.out.printf("--------------------------------------%n");
				System.out.printf("  | %-5s | %-11s | %-11s | %n", amount, tempUsed, tempLimit);
				System.out.printf("--------------------------------------%n");
			}
		}
	}

	/*
	 * Send funds. Balance should be >= input Daily limit should be >= input
	 */

	private void sendFundsConsole() throws SQLException {
		System.out.printf("--------------------------------------%n");
		System.out.printf(">>Insert the account number%n");
		System.out.printf("--------------------------------------%n");
		int accountNumber = menuInput.nextInt();
		System.out.printf("--------------------------------------%n");
		System.out.printf("Insert the amount%n");
		System.out.printf("--------------------------------------%n");
		int amount = menuInput.nextInt();

		sendFunds(accountNumber, amount);
	}

	public void sendFunds(int accountNumber, double amount) throws SQLException {

		if (getCustomer().getBalance() >= amount && getCustomer().getDailyLimit() >= amount) {
			getCustomer().setMoneyUsed(getCustomer().getMoneyUsed() - amount);

			int transactionId = dataAccess.getAllTransactionsById(currentCustomer.getId()).size() + 1;
			Transaction transaction = new Transaction(transactionId, getCustomer().getId(), accountNumber, "SEND",
					"26/11/22", amount);
			dataAccess.createTransaction(transaction);

			double newBalance = dataAccess.sendFunds(currentCustomer.getId(), accountNumber, amount);
			String temp = moneyFormat.format(amount);

			System.out.printf("--------------------------------------%n");
			System.out.printf("  | %-12s | %-10s | %-10s | %n", "SENT", "ACCOUNT NUMBER", "BALANCE");
			System.out.printf("--------------------------------------%n");
			System.out.printf("  | %-12s | %-15s | %-10s | %n", temp, accountNumber, newBalance);
			System.out.printf("--------------------------------------%n");
		}

		else if (getCustomer().getBalance() < amount) {
			System.out.printf("--------------------------------------%n");
			System.out.println("Insuficient funds.");
			System.out.printf("--------------------------------------%n");
		} else {
			String tempUsed = moneyFormat.format(getCustomer().getMoneyUsed());
			String tempLimit = moneyFormat.format(getCustomer().getDailyLimit());

			System.out.printf("--------------------------------------%n");
			System.out.printf("         You cannot withdraw that amount.%n");
			System.out.printf("--------------------------------------%n");
			System.out.printf("  | %-5s | %-11s | %-11s | %n", "WITHDRAWN", "MONEY USED", "DAILY LIMIT");
			System.out.printf("--------------------------------------%n");
			System.out.printf("  | %-5s | %-11s | %-11s | %n", amount, tempUsed, tempLimit);
			System.out.printf("--------------------------------------%n");
		}
	}

	/* See all transactions for specific customer */

	public void seeTransactions() throws SQLException {
		System.out.println(currentCustomer.getId());
		List<Transaction> transactionList = dataAccess.getAllTransactionsById(currentCustomer.getId());
		for (Transaction transaction : transactionList) {
			String tempAmount = moneyFormat.format(transaction.getAmount());

			System.out.printf("--------------------------------------%n");
			System.out.printf("| %-1s | %-7s | %-7s | %-11s | %-11s | %n", "ID", "TYPE", "DATE", "AMOUNT",
					"TO ACCOUNT");
			System.out.printf("| %-1d | %-7s | %-7s | %-11s | %-11d | %n", transaction.getId(), transaction.getType(),
					transaction.getDate(), tempAmount, transaction.getToAccountNumber());
			System.out.printf("--------------------------------------%n");
		}
	}

	public Customer getCustomer() {
		return currentCustomer;
	}
	
	public Customer setCustomer(Customer customer) {
		return this.currentCustomer = customer;
	}

	public String getLocalCustName() {
		return localCustName;
	}

	public void setLocalCustName(String localCustName) {
		this.localCustName = localCustName;
	}

	public int getLocalPinNumber() {
		return localPinNumber;
	}

	public void setLocalPinNumber(int localPinNumber) {
		this.localPinNumber = localPinNumber;
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public int getSelection() {
		return selection;
	}

	public void setSelection(int selection) {
		this.selection = selection;
	}

	public void setDataAccess(DataAccess dataAccess2) {
		this.dataAccess = dataAccess2;
	}

	public void setATMAvailableCash(int cash) {
		this.ATM_AVAILABLE_CASH = cash;
	}
	
	public int getATMAvailableCash() {
		return ATM_AVAILABLE_CASH;
	}
}
