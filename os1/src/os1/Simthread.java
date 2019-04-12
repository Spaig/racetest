package os1;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class Simthread implements Runnable {

	private int transactions;
	private int daily;
	private int balance;
	private int finalday;
	Monitor context;

	public Simthread(Monitor m, int x, int y, int z) {
		context = m;
		this.transactions = x;
		this.balance = x;
		this.daily = y;
		this.finalday = (this.transactions % this.daily);

	}

	// method for transfer in
	public void setBal(int var) {
		this.balance = var;
	}

	// method for transfer out
	public int getBal() {
		return this.balance;
	}

	@Override
	public void run() {
		// 'daily' number of transactions per day
		int counter = daily;
		int xfer = 0;
		int daycounter = 0;
		int interest = 0;
		// calculate number of days to run
		int days = (transactions / daily);
		while (daycounter < days) {
			while (counter > 0) {
				// flip a "coin" to determine withdrawl or deposit
				int result = ThreadLocalRandom.current().nextInt(0, 1);
				// determine amount to transfer
				xfer = ThreadLocalRandom.current().nextInt(1, 500);
				// if "heads", withdraw from other via buffer
				if (result == 1) {
					xfer = context.read();
					balance = balance + xfer;

					// otherwise deposit to other via buffer
				} else {
					// if this would bring the balance to below zero, abort
					if ((balance - xfer) > 0) {
						balance = balance - xfer;
						context.write(xfer);
					}
				} // decrement counter
				counter--;
				// add interest
				interest = ((3 * balance) / 10000);
				balance = balance + interest;
			}
		}
		// print amount of interest earned to console
		System.out.println("Total interst earned today: " + interest + " dollars over " + daily + " transactions.");
		// increment day counter
		daycounter++;
		// if there's a final day with less than D transactions
		if (finalday != 0) {
			for (int i = 0; i < finalday; i++) {// flip a "coin" to determine
												// withdrawl or deposit
				int result = ThreadLocalRandom.current().nextInt(0, 1);
				// determine amount to transfer
				xfer = ThreadLocalRandom.current().nextInt(1, 500);
				// if "heads", withdraw from other via buffer
				if (result == 1) {
					xfer = context.read();
					balance = balance + xfer;

					// otherwise deposit to other via buffer
				} else {
					// if this would bring the balance to below zero, abort
					if ((balance - xfer) > 0) {
						balance = balance - xfer;
						context.write(xfer);
					}
				} // decrement counter
				counter--;
				// add interest
				interest = ((3 * balance) / 10000);
				context.addInterest(interest);
				balance = balance + interest;
				// print amount of interest earned to console
				System.out.println(
						"Total interst earned today: " + interest + " dollars over " + daily + " transactions.");
			}

		}
		// final writeout
		context.totalbal(balance);
		// signal that thread is done
		context.done();
	}

}
