package edu.iup.cosc310.util;

/**
 * @author David Smith
 * 
 *         Utility class to create an array of primes
 */
public class Primes2 {
	// primes[i]
	// primes[i].something[j]<i
	/**
	 * Get a set of prime numbers.
	 * 
	 * @param no
	 *            the number of primes to create
	 * @return an array containing the requested number of primes
	 */
	public static int[] getPrimes(int no) {
		int[] primes = new int[no];
		int primeInx = 0;
		int i = 2;

		while (primeInx < no) {
			boolean prime = true;
			
			for (int j = 2; j <= Math.sqrt(i); j++) {
				if (i  % j == 0) {
					prime = false;
					break;
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
				System.out.printf("Primes %d, %d, %d, %d, %d .... %d, %d, %d, %d, %d%n", primes[0], primes[1],
						primes[2], primes[3], primes[4], primes[995], primes[996], primes[997], primes[998],
						primes[999]);
			}
		}, "Get 1,000 primes", System.out).start();

		new TimeExec(new Runnable() {
			public void run() {
				int[] primes = getPrimes(10000);
				System.out.printf("Primes %d, %d, %d, %d, %d .... %d, %d, %d, %d, %d%n", primes[0], primes[1],
						primes[2], primes[3], primes[4], primes[9995], primes[9996], primes[9997], primes[9998],
						primes[9999]);
			}
		}, "Get 10,000 primes", System.out).start();

		new TimeExec(new Runnable() {
			public void run() {
				int[] primes = getPrimes(100000);
				System.out.printf("Primes %d, %d, %d, %d, %d .... %d, %d, %d, %d, %d%n", primes[0], primes[1],
						primes[2], primes[3], primes[4], primes[99995], primes[99996], primes[99997], primes[99998],
						primes[99999]);
			}
		}, "Get 100,000 primes", System.out).start();

		new TimeExec(new Runnable() {
			public void run() {
				int[] primes = getPrimes(1000000);
				System.out.printf("Primes %d, %d, %d, %d, %d .... %d, %d, %d, %d, %d%n", primes[0], primes[1],
						primes[2], primes[3], primes[4], primes[999995], primes[999996], primes[999997], primes[999998],
						primes[999999]);
			}
		}, "Get 1,000,000 primes", System.out).start();
	}
}