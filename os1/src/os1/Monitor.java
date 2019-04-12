package os1;

public class Monitor {

	private int thisize;
	private int herethreads;
	private int start = 0;
	private int end = 0;
	private int count = 0;
	private int done;
	private int[] buffer;
	int totalinterest = 0;
	int totalbal = 0;

	public Monitor(int size, int threads) {

		thisize = size;
		herethreads = threads;
		done = 0;
		buffer = new int[size];
	}

	public synchronized void write(int x) {

		while (count == thisize) {
			try {
				wait();
			} catch (InterruptedException ex) {
			}
		}

		buffer[end] = x;
		end = (end + 1) % thisize;
		count++;
		notify();
	}

	public synchronized int read() {

		while (count == 0) {
			try {
				wait();
			} catch (InterruptedException ex) {
			}
		}

		int out = buffer[start];
		start = (start + 1) % thisize;
		count--;
		notify();
		return out;
	}

	public void addInterest(int x) {
		totalinterest = totalinterest + x;
	}

	public void totalbal(int x) {
		totalbal = totalbal + x;
	}

	public synchronized void untilDone() {
		while (!(done == herethreads)) {
			try {
				wait();
			} catch (Exception e) {
			}
		}
	}

	public synchronized void done() {
		done++;
		notify();
	}

}
