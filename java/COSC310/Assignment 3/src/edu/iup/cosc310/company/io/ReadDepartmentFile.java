package edu.iup.cosc310.company.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.iup.cosc310.company.bo.Department;

/**
 * Helper class to read departments from a comma separated
 * department text file. The method readDepartment() returns 
 * the next department from the department file.
 * 
 * @author David T. Smith
 */
public class ReadDepartmentFile {
	BufferedReader input;
	
	/**
	 * Constructor - opens the department file.
	 * @param fileName - name of the department file
	 * @throws FileNotFoundException - in the event the file could not be opened
	 */
	public ReadDepartmentFile(String fileName) throws FileNotFoundException {
		input = new BufferedReader(new FileReader(fileName));
	}
	
	/**
	 * Read the next department from the department file.
	 * @return a department.  Returns null in the event the end of
	 * the department file is reached.
	 * @throws IOException
	 */
	public Department readDepartment() throws IOException {
		String line = input.readLine();
		
		// Test for end of file
		if (line == null) {
			return null;
		}
		String[] parts = line.split(",");
		String deptCode = parts[0];
		String deptName = parts[1];
		String mgrEmpId = parts[2];
		
		return new Department(deptCode, deptName, mgrEmpId);
	}
	
	/**
	 * Close the department file
	 * @throws IOException
	 */
	public void close() throws IOException {
		input.close();
	}
}
