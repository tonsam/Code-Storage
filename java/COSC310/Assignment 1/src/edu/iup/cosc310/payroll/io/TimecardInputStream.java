package edu.iup.cosc310.payroll.io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import edu.iup.cosc310.payroll.bo.Timecard;

/**
 * Input stream for Timecard objects for the company payroll reporting project.
 * 
 * @author David Kornish
 *
 */
public class TimecardInputStream {
	private DataInputStream input;

	/**
	 * Constructor for the Timecard input stream. Accepts the binary input file as a
	 * string, may throw a FileNotFoundException if the file is nonexistent.
	 * 
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public TimecardInputStream(String fileName) throws FileNotFoundException {
		input = new DataInputStream(new FileInputStream(fileName));
	}

	/**
	 * Read method, interprets the next timecard from the binary file and returns it
	 * as a timecard object.
	 * 
	 * @return
	 * @throws IOException
	 */
	public Timecard readTimecard() throws IOException {

		if (input.available() == 0) {
			return null;
		}

		short number = input.readShort();
		short year = input.readShort();
		byte month = input.readByte();
		byte day = input.readByte();
		byte timeIn = input.readByte();
		byte timeOut = input.readByte();

		Timecard card = new Timecard(number, year, month, day, timeIn, timeOut);
		return card;
	}

}
