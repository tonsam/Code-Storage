package edu.iup.cosc310.payroll.io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import edu.iup.cosc310.payroll.bo.Employee;

/**
 * 
 * Input Stream to convert Employee.dat files into Employee objects
 * 
 * @author David Kornish
 * 
 */
public class EmployeeInputStream {
	private DataInputStream input;

	/**
	 * constructor for the Employee input stream. Accepts the argument string of the
	 * input file
	 * 
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public EmployeeInputStream(String fileName) throws FileNotFoundException {
		input = new DataInputStream(new FileInputStream(fileName));
	}

	/**
	 * read method for the Employee input stream. Converts a data stream into an
	 * Employee object and returns said Employee object.
	 * 
	 * @return
	 * @throws IOException
	 */
	public Employee readEmployee() throws IOException {
		// Test for end of file
		if (input.available() == 0) {
			return null;
		}

		// declare byte buffers for the ascii fields.
		byte[] fn = new byte[10];
		byte[] ln = new byte[10];

		int empNum = input.readShort();

		input.read(fn);
		input.read(ln);
		String firstName = new String(fn);
		String lastName = new String(ln);

		// remove trailing spaces
		firstName = firstName.replaceAll("\\s", "");
		lastName = lastName.replaceAll("\\s", "");

		double rate = input.readDouble();

		Employee employee = new Employee(empNum, firstName, lastName, rate);

		return employee;
	}
}
