package edu.iup.cosc310.util;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author David Smith
 *
 * A utility class used to time the execution of a Runnable
 */
public class TimeExec {
	private Runnable runnable;
	private String message;
	private OutputStream log;

	/**
	 * Contructor that creates a TimeExec
	 * @param runnable the runnable to execute
	 * @param message the message to be displayed when the run completes
	 * @param log the stream to which the message is logged
	 */
	public TimeExec(Runnable runnable, String message, OutputStream log) {
		this.runnable = runnable;
		this.message = message;
		this.log = log;
	}

	/**
	 * Start the TimeExec.  This will execute the runnable and log
	 * the execution time (in secs) to the log.
	 */
	public void start() {
		PrintStream out = new PrintStream(log);
		long startTime = System.currentTimeMillis();
		runnable.run();
		long stopTime = System.currentTimeMillis();
		out.println("TimeExec: " + message + ": " + ((stopTime - startTime) / 1000.0) + "s");
	}
}
