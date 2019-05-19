package bank;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Bank {
	public static final Transaction NULL_TRANS = new Transaction(-1, -1, -1);
	
	private BlockingQueue<Transaction> queue = new ArrayBlockingQueue<Transaction>(50);
	private CountDownLatch latch;
	private List<Account> accounts = new ArrayList<>();
	
	
	int numThreads;
	String filename;
	public Bank(int numThreads,String filename) {
		latch = new CountDownLatch(numThreads);
		this.numThreads = numThreads;
		
		for(int i=0;i<20;i++) {
			accounts.add(new Account(i, 1000));
		}
		this.filename = filename;
	}
	
	public void start() {
		
		for(int i=0;i<numThreads;i++) {
			Worker w = new Worker(queue, latch, accounts);
			w.start();
		}
		
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			String line;
			while((line = rd.readLine())!= null) {
				String[] args = line.split(" ");
				int from = Integer.parseInt(args[0]);
				int to = Integer.parseInt(args[1]);
				int amount = Integer.parseInt(args[2]);
				
				Transaction trans = new Transaction(from, to, amount);
				queue.put(trans);
				
				
		
			}
			for(int i=0;i<numThreads;i++) {
				queue.put(NULL_TRANS);
			}
			
			latch.await();
			for(Account ac : accounts) {
				System.out.println(ac);
			}
			
			rd.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		Bank bank = new Bank(Integer.parseInt(args[1]),args[0]);
		bank.start();
	}

}
