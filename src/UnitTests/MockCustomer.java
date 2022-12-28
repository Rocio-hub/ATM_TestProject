package UnitTests;

import java.util.ArrayList;
import java.util.List;

import synopsis.BE.Customer;

public class MockCustomer {

	List<Customer> customerList;

	public void CreateMockCustomers() {
		customerList = new ArrayList<>();
		customerList.add(new Customer(1, 1111, 1234, 12000.0, 2500.0, 0.0));
		customerList.add(new Customer(2, 2222, 1234, 6000.0, 5000.0, 0.0));
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public Customer getCustomerById(int id) {
		for (Customer customer : customerList) {
			if (customer.getId() == id)
				return customer;
		}
		return null;
	}

	public double getBalance(int id) {
		for (Customer customer : customerList) {
			if (customer.getId() == id)
				return customer.getBalance();
		}
		return 0;
	}

	public double makeDeposit(int id, int amount) {
		for (Customer customer : customerList) {
			if (customer.getId() == id)
				customer.setBalance(customer.getBalance() + amount);
			return customer.getBalance();
		}
		return 0;
	}

	public double makeWithdrawal(int id, int amount) {
		for (Customer customer : customerList) {
			if (customer.getId() == id)
				customer.setBalance(customer.getBalance() - amount);
			return customer.getBalance();
		}
		return 0;
	}

	public double sendFunds(int id, int accountNumber, int amount) {
		for (Customer customer : customerList) {
			if (customer.getCustomerNumber() == accountNumber)
				customer.setBalance(customer.getBalance() - amount);			
			return customer.getBalance();
		}
		return 0;
	}
	
	public void receiveMoney(int accountNumber, int amount) {
		for(Customer customer : customerList) {
			if(customer.getCustomerNumber() == accountNumber) {
				customer.setBalance(customer.getBalance() + amount);
			}
		}
		
	}

	public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }
}
