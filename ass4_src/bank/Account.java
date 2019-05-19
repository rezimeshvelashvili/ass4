package bank;

import java.util.concurrent.Semaphore;

public class Account {
	
	private Semaphore sem = new Semaphore(1);
	
	private int balance;
	private int numT;
	private int id;
	public Account(int number, int balance) {
		
		this.balance = balance;
		this.id = number;
		
		
	}
	
	public void changeMoney(int money) {
		try {
			sem.acquire();
			this.balance += money;
			numT++;
			sem.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		
		build.append("acct: ");
		build.append(id);
		build.append("bal: ");
		build.append(balance);
		build.append("transactions: ");
		build.append(numT);
		
		return build.toString();
		
	}
	
}
