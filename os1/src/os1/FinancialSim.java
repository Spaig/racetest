package os1;

import java.util.Scanner;

public class FinancialSim {

	public static void main() {
		// create scanner
		Scanner scanner = new Scanner(System.in);
		// create local variables
		int customers;
		int transactions;
		int daily;
		// create testing variable
		int temp = -1;

		// validation loops
		while (temp < 1) {
			System.out.print("Enter an integer number of customers followed by <enter>: ");
			temp = scanner.nextInt();
		}
		customers = temp;
		temp = -1;

		while (temp < 1) {
			System.out.print("Enter an integer number of transactions followed by <enter>: ");
			temp = scanner.nextInt();
		}
		transactions = temp;
		temp = -1;

		while (temp < 1) {
			System.out.print("Enter an integer number of transactions per day followed by <enter>: ");
			temp = scanner.nextInt();
		}
		daily = temp;
		temp = -1;
		// close the scanner
		scanner.close();
		// create monitor
		Monitor m = new Monitor(20, customers);
		// create threadpool
		Thread[] poole = new Thread[customers];

		for (int i = 0; i < customers; i++) {
			poole[i] = new Thread(new Simthread(m, transactions, 10000, daily));
			poole[i].start();
		}
		m.untilDone();
		// print final variables
		System.out.println("Total ending balance: " + m.totalbal);
		System.out.println("Total interest paid: " + m.totalinterest);
		System.out.println("Total starting balance: " + (customers * 10000));

	}
}
