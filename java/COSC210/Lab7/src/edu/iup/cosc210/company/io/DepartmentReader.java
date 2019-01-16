package edu.iup.cosc210.company.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.iup.cosc210.company.bo.Department;

/**
 * Helper class to read departments from a comma separated
 * department text file. The method readDepartment() returns 
 * the next department from the department file.
 * 
 * @author David T. Smith
 */
public class DepartmentReader {
	BufferedReader input;
	
	/**
	 * Constructor - opens the department file.
	 * 
	 * @param fileName - name of the department file.
	 * 
	 * @throws FileNotFoundException In the event the file could not be opened.
	 */
	public DepartmentReader(String fileName) throws FileNotFoundException {
		input = new BufferedReader(new FileReader(fileName));
	}
	
	/**
	 * Read the next department from the department file.
	 * 
	 * @return Next department from the file.  Returns null in the event the end of
	 * the department file is reached.
	 * 
	 * @throws IOException
	 */
	public Department readDepartment() throws IOException {
		String line = input.readLine();
		
		// Test for end of file
		if (line == null) {
			return null;
		}
		String[] fields = line.split(",");
		String deptCode = fields[0];
		String deptName = fields[1];
		int mgrEmpId = Integer.parseInt(fields[2]);
		
		return new Department(deptCode, deptName, mgrEmpId);
	}
	
	/**
	 * Close the department file
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		input.close();
	}
}
