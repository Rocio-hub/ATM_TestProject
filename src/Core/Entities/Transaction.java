package Core.Entities;

public class Transaction {
	
	private int id;
	private int customerId;
	private int toAccountNumber;
	private String type;
	private String date;
	private double amount;
	
	public Transaction() {
		
	}

	public Transaction(int id, int customerId, int toAccountNumber, String type, String date, double amount) {
		this.setId(id);
        this.setcustomerId(customerId);
        this.setToAccountNumber(toAccountNumber);
        this.setType(type);
        this.setDate(date);
        this.setAmount(amount);
	}

	public int getcustomerId() {
		return customerId;
	}

	public void setcustomerId(int customerId) {
		this.customerId = customerId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getToAccountNumber() {
		return toAccountNumber;
	}

	public void setToAccountNumber(int toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}
}
