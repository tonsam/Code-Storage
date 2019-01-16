package edu.iup.cosc210.company.io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import edu.iup.cosc210.company.bo.Company;
import edu.iup.cosc210.company.bo.Employee;

/**
 * Helper class to read employees from a binary employee file.  
 * The method readEmployee() returns the next employee from 
 * the employee file.
 * 
 * @author David T. Smith
 */
public class EmployeeInputStream {
	private DataInputStream input;

	/**
	 * Constructor - opens the employee file.
	 * @param fileName - name of the employee file
	 * 
	 * @throws FileNotFoundException In the event the file could not be opened
	 */
	public EmployeeInputStream(String fileName) throws FileNotFoundException {
		input = new DataInputStream(new FileInputStream(fileName));
	}

	/**
	 * Read the next employee from the employee file.
	 * @return The next employee from the file.  Returns null in the event the end of
	 * the employee file is reached.
	 * 
	 * @throws IOException
	 */
	public Employee readEmployee() throws IOException {
		// Test for end of file
		if (input.available() == 0) {
			return null;
		}
		
		// declare byte buffers for the ascii fields.
		byte[] d = new byte[10];

		int employeeId = input.readInt();
		String lastName = input.readUTF();
		String firstName = input.readUTF();
		String mi = "" + (char) input.readByte();
		String empIndicator = "" + (char) input.readByte();		
		String deptCode = "" + (char) input.readByte();	
		
		double salary = input.readDouble();
		boolean exempt = input.readBoolean();
		
		input.read(d);
		Date hireDate;
		try {
			hireDate = Company.standardDateFormat.parse(new String(d));
		} catch (ParseException e) {
			hireDate = null;
			e.printStackTrace();
		}
		
		short vacationDays = input.readShort();
		byte training = input.readByte();

		Employee employee = new Employee(employeeId, empIndicator, deptCode, firstName,
				lastName, mi, salary, exempt, hireDate, vacationDays, training);

		return employee;
	}

	/**
	 * Close the employee file
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		input.close();
	}
}
