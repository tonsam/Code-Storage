package edu.iup.cosc310.util;

/**
 * @author David Smith and David Kornish
 * 
 *         Utility class to create an array of primes
 */
public class Primes {

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

		for (int i = 2; primeInx < no; i++) {
			if (isPrime(i)) {
				primes[primeInx++] = i;
			}
		}

		return primes;
	}

	/**
	 * Static method to check if an integer is a prime number.
	 * 
	 * @param n
	 * @return
	 */
	public static boolean isPrime(int n) {
		if (n == 2 || n == 3)
			return true;
		if (n % 2 == 0 || n % 3 == 0)
			return false;
		int sqrtN = (int) Math.sqrt(n) + 1;
		for (int i = 6; i <= sqrtN; i += 6) {
			if (n % (i - 1) == 0 || n % (i + 1) == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
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