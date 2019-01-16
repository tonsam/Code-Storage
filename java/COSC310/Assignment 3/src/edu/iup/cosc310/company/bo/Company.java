package edu.iup.cosc310.company.bo;

import edu.iup.cosc310.util.ItemList;
import edu.iup.cosc310.util.LinkedItemList;

/**
 * A Company.  Maintains a list of departments and methods access
 * the company's departments.
 * 
 * @author David T. Smith, David Kornish
 */
public class Company {
	private ItemList<Department> departments = new LinkedItemList<Department>();
	private int noDepartments = 0;

	/**
	 * Add a Department to the list of departments for this company.
	 * 
	 * @param department - the company to be added
	 */
	public void addDepartment(Department department) {
		departments.append(department);
		noDepartments++;
	}

	/**
	 * Find a department with a given department code
	 * 
	 * @param deptCode - the department code used to find a department
	 * @return the department with the given code.  Returns null if 
	 * a department by the given department code is not found.
	 */
	public Department findDepartment(String deptCode) {
		for (int i = 0; i < noDepartments; i++) {
			Department department = departments.get(i);
			if (deptCode.equals(department.getDeptCode())) {
				return department;
			}
		}
		
		return null;
	}

	/**
	 * Get the number of departments in this company.
	 * @return the number of departments in this company.
	 */
	public int getNoDepartments() {
		return noDepartments;
	}

	/**
	 * Get the ith department in this company
	 * @param i - index identifying the department to be returned
	 * @return the ith department in this company
	 */
	public Department getDeparment(int i) {
		return departments.get(i);
	}
}
