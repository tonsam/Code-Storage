package edu.iup.cosc210.company.bo;

import java.text.SimpleDateFormat;


/**
 * A Company.  Maintains a list of departments and provides methods to access
 * the company's departments.
 * 
 * @author David T. Smith
 */
public class Company {
	private Department[] departments = new Department[10];
	private int noDepts = 0;
	
	// define a simple date format to be used throughout the Company application
	public static final SimpleDateFormat standardDateFormat = new SimpleDateFormat("MM/dd/yyyy");	

	/**
	 * Add a Department to the list of departments for this company.
	 * 
	 * @param department the department to be added
	 */
	public void addDepartment(Department department) {
		departments[noDepts++] = department;
	}

	/**
	 * Find a department with a given department code
	 * 
	 * @param deptCode the department code used to find a department
	 * 
	 * @return the department with the given code.  Returns null if 
	 * a department by the given department code is not found.
	 */
	public Department findDepartment(String deptCode) {
		for (int i = 0; i < noDepts; i++) {
			Department department = departments[i];
			if (deptCode.equals(department.getDeptCode())) {
				return department;
			}
		}
		
		return null;
	}

	/**
	 * Get the number of departments in this company.
	 * 
	 * @return the number of departments in this company.
	 */
	public int getNoDepts() {
		return noDepts;
	}

	/**
	 * Get the ith department in this company
	 * 
	 * @param index index identifying the department to be returned
	 * 
	 * @return the ith department in this company
	 */
	public Department getDeparment(int index) {
		return departments[index];
	}
}
