package bank;

public class Transaction {
	private int from;
	private int to;
	private int money;
	private int index;
	public Transaction(int from,int to,int money) {
		this.from = from;
		this.to = to;
		this.money=money;
	}
	
	
	public int getTo() {
		return to;
	}
	
	public int getMoney() {
		return money;
	}
	public int getFrom() {
		return from;
	}
}
