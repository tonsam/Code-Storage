package edu.iup.cosc310.company.bo;

import java.time.LocalDate;
import java.util.Date;

/**
 * An Employee of a Company.
 * 
 * @author David T. Smith
 */
public class Employee {
	
	private String firstName;
	private String lastName;
	private String employeeId;
	private String employeeIndicator;
	private String deptCode;
	private float salary;
	private LocalDate hireDate;
	private short vacationDays;
	private byte training;
	
	/**
	 * Constructor for Employee
	 * @param firstName - first name of the employee
	 * @param lastName - last name of the employee
	 * @param employeeId - the employee's id number
	 * @param employeeIndicator - indicates if an employee ("E") or a contractor ("C")
	 * @param deptCode - department code of the employee's department
	 * @param salary - the employee's salary
	 * @param hireDate - the date the employee was hired
	 * @param vacationDays - the number of vacation days
	 * @param training - a byte using bits to indicated the training the employee has received
	 */
	public Employee(String employeeId, String employeeIndicator, String deptCode, String firstName, String lastName, float salary, LocalDate hireDate, short vacationDays, byte training){
		this.employeeId = employeeId;
		this.employeeIndicator = employeeIndicator;
		this.deptCode = deptCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.hireDate = hireDate;
		this.vacationDays = vacationDays;
		this.training = training;
	}
	
	/**
	 * Get the employee's id 
	 * @return - the employee's id
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * Get the employee's indicator code E for employee, C for contractor
	 * @return - the employee's indicator code
	 */
	public String getEmployeeIndicator() {
		return employeeIndicator;
	}
	
	/**
	 * Get the employee's department code.
	 * @return - the employee's department code
	 */
	public String getDeptCode() {
		return deptCode;
	}
	

	/**
	 * Get the employee's first name
	 * @return - the employee's first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Get the employee's last name
	 * @return - the employee's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Get the employee's full name (i.e., first name and last name)
	 * @return - the employee's full name
	 */
	public String getFullName() {
		return firstName + " " + lastName;
	}

	/**
	 * Get the employee's salary
	 * @return - the employee's salary
	 */
	public float getSalary() {
		return salary;
	}
	
	/**
	 * Get the employee's hire date as a string of the form mm/dd/yyyy.
	 * @return - returns the employee's hire date
	 */
	public LocalDate getHireDate() {
		return hireDate;
	}
	
	/**
	 * Get the number of vacation days for an employee
	 * @return - the number of vacation days an employee has
	 */
	public short getVacationDays() {
		return vacationDays;
	}
	
	/**
	 * Get the encoded traning byte.
	 * @return - a byte whose bits indicate traingin the employee has recieved
	 */
	public byte getTraining() {
		return training;
	}
}
