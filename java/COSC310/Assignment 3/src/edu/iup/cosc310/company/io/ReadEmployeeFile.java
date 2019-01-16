package edu.iup.cosc310.company.io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import edu.iup.cosc310.company.bo.Employee;

/**
 * Helper class to read employees from a binary employee file.  
 * The method readEmployee() returns the next employee from 
 * the employee file.
 * 
 * @author David T. Smith
 */
public class ReadEmployeeFile {
	private DataInputStream input;

	/**
	 * Constructor - opens the employee file.
	 * @param fileName - name of the employee file
	 * @throws FileNotFoundException - in the event the file could not be opened
	 */
	public ReadEmployeeFile(String fileName) throws FileNotFoundException {
		input = new DataInputStream(new FileInputStream(fileName));
	}

	/**
	 * Read the next employee from the employee file.
	 * @return an employee.  Returns null in the event the end of
	 * the employee file is reached.
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public Employee readEmployee() throws IOException {
		// Test for end of file
		if (input.available() == 0) {
			return null;
		}
		
		// declare byte buffers for the ascii fields.
		byte[] employeeIdByte = new byte[3];
		byte[] lastNameBytes = new byte[15];
		byte[] firstNameBytes = new byte[20];

		input.read(employeeIdByte);
		String employeeId = new String(employeeIdByte);
		
		String empIndicator = "" + (char) input.readByte();
		
		String deptCode = "" + (char) input.readByte();
		
		input.read(lastNameBytes);
		String lastName = new String(lastNameBytes).trim();
		input.read(firstNameBytes);
		String firstName = new String(firstNameBytes).trim();
		float salary = input.readFloat();
		byte month = input.readByte();
		byte day = input.readByte();
		short year = input.readShort();
		LocalDate hireDate = LocalDate.of(year, month, day);
		short vacationDays = input.readShort();
		byte training = input.readByte();

		Employee employee = new Employee(employeeId, empIndicator, deptCode, firstName,
				lastName, salary, hireDate, vacationDays, training);

		return employee;
	}

	/**
	 * Close the employee file
	 * @throws IOException
	 */
	public void close() throws IOException {
		input.close();
	}
}
