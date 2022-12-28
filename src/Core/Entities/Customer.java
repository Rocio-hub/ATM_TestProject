package Core.Entities;

public class Customer {
		
	private int id;
	private String customerName;
	private int pin;
	//private String name;
	private Double balance = 0.0;
	private Double dailyLimit = 0.0;
	private Double moneyUsed = 0.0;
	//private List<Transaction> transactionList;
	
	public Customer() {
		
	}
	
	public Customer(int id, String customerName, int pin, double balance, double dailyLimit, double moneyUsed) {
		this.setId(id);
		this.setCustomerName(customerName);
		this.setPin(pin);
        this.setBalance(balance);
        //this.name = name;
        this.setDailyLimit(dailyLimit);
        this.setMoneyUsed(moneyUsed);
        //this.transactionList = transactionList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public Double getMoneyUsed() {
		return moneyUsed;
	}

	public void setMoneyUsed(Double moneyUsed) {
		this.moneyUsed = moneyUsed;
	}

	public Double getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(Double dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
}
