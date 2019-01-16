package edu.iup.cosc210.company.bo;

import java.util.Date;

/**
 * An Employee of a Company.
 * 
 * @author David T. Smith
 */
public class Employee {
	
	private String firstName;
	private String lastName;
	private String mi;
	private int employeeId;
	private String employeeIndicator;
	private String deptCode;
	private double salary;
	private boolean exempt;
	private Date hireDate;
	private short vacationDays;
	private byte training;
	
	/**
	 * Constructor for Employee
	 * 
	 * @param employeeId The employee's id number
	 * @param employeeIndicator Indicates if an employee ("E") or a contractor ("C")
	 * @param deptCode Department code of the employee's department
	 * @param firstName First name of the employee
	 * @param lastName Last name of the employee
	 * @param mi Middle initial of the employee
	 * @param salary The employee's salary
	 * @param exempt Indicates if the employee is exempt (i.e., does not get paid overtime)
	 * @param hireDate The date the employee was hired
	 * @param vacationDays The number of vacation days
	 * @param training A byte using bits to indicated the training the employee has received
	 */
	public Employee(int employeeId, String employeeIndicator, String deptCode, String firstName, String lastName, String mi, double salary, boolean exempt, Date hireDate, short vacationDays, byte training){
		this.employeeId = employeeId;
		this.employeeIndicator = employeeIndicator;
		this.deptCode = deptCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mi = mi;
		this.salary = salary;
		this.exempt = exempt;
		this.hireDate = hireDate;
		this.vacationDays = vacationDays;
		this.training = training;
	}
	
	/**
	 * Get the employee's id 
	 * 
	 * @return The employee's id
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * Get the employee's indicator code E for employee, C for contractor
	 * 
	 * @return The employee's indicator code
	 */
	public String getEmployeeIndicator() {
		return employeeIndicator;
	}
	
	/**
	 * Get the employee's department code.
	 * 
	 * @return The employee's department code
	 */
	public String getDeptCode() {
		return deptCode;
	}
	

	/**
	 * Get the employee's first name
	 * 
	 * @return The employee's first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Get the employee's last name
	 * 
	 * @return The employee's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Get the employee's middle initial
	 * 
	 * @return The employee's middle initial
	 */
	public String getMi() {
		return mi;
	}

	/**
	 * Get the employee's full name (i.e., first name, middle initial, then last name)
	 * 
	 * @return The employee's full name
	 */
	public String getFullName() {
		return getFirstName() + " " + getMi() + " " + getLastName();
	}

	/**
	 * Get the employee's salary
	 * 
	 * @return The employee's salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * Determine if the employee is exempt (i.e., no overtime)
	 * 
	 * @return True if the employee is exempt, false otherwise
	 */
	public boolean isExempt() {
		return exempt;
	}

	/**
	 * Get the employee's hire date.
	 * 
	 * @return The employee's hire date
	 */
	public Date getHireDate() {
		return hireDate;
	}
	
	/**
	 * Get the number of vacation days for an employee
	 * 
	 * @return The number of vacation days an employee has
	 */
	public short getVacationDays() {
		return vacationDays;
	}
	
	/**
	 * Get the encoded training byte.
	 * @return - a byte whose bits indicate training the employee has received
	 */
	public byte getTraining() {
		return training;
	}
}
