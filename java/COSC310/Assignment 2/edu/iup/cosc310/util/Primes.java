package edu.iup.cosc310.util;

/**
 * @author David Smith
 * 
 * Utility class to create an array of primes
 */
public class Primes {

	/**
	 * Get a set of prime numbers.
	 * @param no the number of primes to create
	 * @return an array containing the requested number
	 * of primes
	 */
	public static int[] getPrimes(int no) {
		int[] primes = new int[no];
		int primeInx = 0;
		int i = 2;
		
		while (primeInx < no) {
			boolean prime = true;
			for (int j = 2; j < i; j++) {
				if (i == i / j * j) {
					prime = false;
				}
			}
			if (prime) {
				primes[primeInx++] = i;
			}
			i++;
		}

		return primes;
	}

	public static void main(String[] args) {
		new TimeExec(new Runnable() {
			public void run() {
				int[] primes = getPrimes(1000);
			}
		}, "Get 1,000 primes", System.out).start();
		
		new TimeExec(new Runnable() {
			public void run() {
				int[] primes = getPrimes(10000);
			}
		}, "Get 10,000 primes", System.out).start();
		
		new TimeExec(new Runnable() {
			public void run() {
				int[] primes = getPrimes(100000);
			}
		}, "Get 100,000 primes", System.out).start();
		
//		new TimeExec(new Runnable() {
//			public void run() {
//				int[] primes = getPrimes(1000000);
//			}
//		}, "Get 1,000,000 primes", System.out).start();
	}
}