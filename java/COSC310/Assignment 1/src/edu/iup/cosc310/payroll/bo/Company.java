package edu.iup.cosc310.payroll.bo;

/**
 * 
 * Company object class for the payroll report program
 * 
 * @author David Kornish
 *
 */
public class Company {
	private Employee[] employees;
	private int numEmployees;

	/**
	 * constructor for a Company object. Initializes instance variables.
	 */
	public Company() {
		this.numEmployees = 0;
		this.employees = new Employee[50];
	}

	/**
	 * Pay-calculating method for a company object. Calls the calculate-pay method
	 * on each employee in the company.
	 */
	public void calculatePay() {
		for (Employee emp : employees) {
			if (emp == null) {
				break;
			}

			emp.calculatePay();
		}
	}

	/**
	 * Method to add a new employee to the company.
	 * 
	 * @param emp
	 */
	public void addEmployee(Employee emp) {
		employees[this.numEmployees] = emp;
		this.numEmployees++;
	}

	/**
	 * method to return a specific employee, identified by its index in the
	 * company's employee array.
	 * 
	 * @param index
	 * @return
	 */
	public Employee getEmployee(int index) {
		return employees[index];
	}

	/**
	 * method to return the number of employees
	 * 
	 * @return
	 */
	public int getNumEmployees() {
		return this.numEmployees;
	}

	/**
	 * Method to return a specific employee, identified by an employee number from a
	 * time card rather than index. Loops through each employee and checks if its
	 * employee number matches the one provided.
	 * 
	 * @param empNum
	 * @return
	 */
	public Employee timecardMatch(int empNum) {
		Employee cardOwner = null;

		for (Employee emp : employees) {
			if (emp.getEmpNum() == empNum) {
				cardOwner = emp;
				break;
			}
		}

		return cardOwner;
	}
}
