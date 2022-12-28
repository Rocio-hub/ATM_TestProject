package Core.Services;

import java.text.DecimalFormat;
import java.util.Scanner;

public class AccountManager {
	Scanner input = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
	
	/* Set the Customer Number */
	public int setCustomerNumber(int customerNumber) {
		this.customerNumber= customerNumber;
		return customerNumber;
	}
	
	/* Get the Customer Number */
	public int getCustomerNumber() {
		return customerNumber;
	}
	
	/* Set the PIN number */
	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}
	
	/* Get the PIN Number */
	public int getPinNumber() {
		return pinNumber;
	}
	
	/* Get Checking Account Balance */
	public double getCheckingBalance() {
		return checkingBalance;
	}
	
	/* Get Saving Account Balance */
	public double getSavingBalance() {
		return savingBalance;
	}
	
	/* Calculate Checking Account withdrawal */
	public double calcCheckingWithdraw(double amount) {
		setCheckingBalance((getCheckingBalance() - amount));
		return getCheckingBalance();
	}
	
	/* Calculate Saving Account withdrawal */
	public double calcSavingWithdraw(double amount) {
		setSavingBalance((getSavingBalance() - amount));
		return getSavingBalance();
	}
	
	/* Calculate Checking Account deposit */
	public double calcCheckingDeposit(double amount) {
		setCheckingBalance((getCheckingBalance() + amount));
		return getCheckingBalance();
	}
	
	/* Calculate Saving Account deposit */
	public double calcSavingDeposit(double amount) {
		setSavingBalance((getSavingBalance() + amount));
		return getSavingBalance();
	}
	
	/* Customer Checking Account Withdraw input */
	public void getCheckingWithdrawInput() {
		System.out.println("Checking Account Balance: " + moneyFormat.format(getCheckingBalance()));
		System.out.println("Amount you want to withdraw from Checking Account: ");
		double amount = input.nextDouble();
		
		if((getCheckingBalance() - amount) >= 0) {
			calcCheckingWithdraw(amount);
			System.out.println("New Checking Account balance: " + moneyFormat.format(getCheckingBalance()));
		} else {
			System.out.println("Balance cannot be negative." + "\n");
		}		
	}
	
	/* Customer Saving Account Withdraw input */
	public void getSavingWithdrawInput() {
		System.out.println("Saving Account Balance: " + moneyFormat.format(getSavingBalance()));
		System.out.println("Amount you want to withdraw from Saving Account: ");
		double amount = input.nextDouble();
		
		if((getSavingBalance() - amount) >= 0) {
			calcSavingWithdraw(amount);
			System.out.println("New Saving Account balance: " + moneyFormat.format(getSavingBalance()));
		} else {
			System.out.println("Balance cannot be negative." + "\n");
		}		
	}
	
	/* Customer Checking Account Deposit input */
	public void getCheckingDepositInput() {
		System.out.println("Checking Account Balance: " + moneyFormat.format(getCheckingBalance()));
		System.out.println("Amount you want to deposit from Checking Account: ");
		double amount = input.nextDouble();
		
		if((getCheckingBalance() - amount) >= 0) {
			calcCheckingDeposit(amount);
			System.out.println("New Checking Account balance: " + moneyFormat.format(getCheckingBalance()));
		} else {
			System.out.println("Balance cannot be negative." + "\n");
		}		
	}
	
	/* Customer Saving Account Deposit input */
	public void getSavingDepositInput() {
		System.out.println("Saving Account Balance: " + moneyFormat.format(getSavingBalance()));
		System.out.println("Amount you want to deposit from Saving Account: ");
		double amount = input.nextDouble();
		
		if((getSavingBalance() - amount) >= 0) {
			calcSavingDeposit(amount);
			System.out.println("New Saving Account balance: " + moneyFormat.format(getSavingBalance()));
		} else {
			System.out.println("Balance cannot be negative." + "\n");
		}		
	}
	
	public void setCheckingBalance(double checkingBalance) {
		this.checkingBalance = checkingBalance;
	}

	public void setSavingBalance(double savingBalance) {
		this.savingBalance = savingBalance;
	}

	private int customerNumber;
	private int pinNumber;
	private double checkingBalance = 0;
	private double savingBalance = 0;
}
