package bank;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Worker extends Thread{
	
	
	private BlockingQueue<Transaction> queue;
	private CountDownLatch latch;
	private List<Account> accounts;
	
	public Worker(BlockingQueue<Transaction> queue,CountDownLatch latch,List<Account> accounts) {
		this.queue = queue;
		this.latch = latch;
		this.accounts = accounts;
	}
	
	@Override
	public void run() {
		Transaction nextTrans;
		try {
			nextTrans = queue.take();
			while(nextTrans  != Bank.NULL_TRANS) {
				
				accounts.get(nextTrans.getFrom()).changeMoney(-nextTrans.getMoney());
				accounts.get(nextTrans.getTo()).changeMoney(nextTrans.getMoney());
				
				nextTrans = queue.take();
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		latch.countDown();
		
	}

}
