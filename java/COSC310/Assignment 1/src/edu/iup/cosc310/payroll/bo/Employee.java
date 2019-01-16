package edu.iup.cosc310.payroll.bo;

/**
 * Employee object class for the company payroll report program.
 * 
 * @author David Kornish
 *
 */
public class Employee {
	private Timecard[] timecards;
	private int numTimecards;

	private int empNum;
	private String empFirstName;
	private String empLastName;
	private double hourlyWages;

	private int hoursWorked;

	private double grossPay;
	private double fedTax;
	private double stateTax;
	private double netPay;

	/**
	 * constructor for an Employee object. Accepts variables for employee number,
	 * first and last name, and hourly wages, and initializes other instance
	 * variables as zero.
	 * 
	 * @param empNum
	 * @param empFirstName
	 * @param empLastName
	 * @param hourlyWages
	 */
	public Employee(int empNum, String empFirstName, String empLastName, double hourlyWages) {
		super();
		this.timecards = new Timecard[50];

		this.empNum = empNum;
		this.empFirstName = empFirstName;
		this.empLastName = empLastName;
		this.hourlyWages = hourlyWages;

		this.hoursWorked = 0;

		this.fedTax = 0;
		this.stateTax = 0;
		this.grossPay = 0;
		this.netPay = 0;
	}

	/**
	 * Pay-calculating method for an employee. Loops through time cards and
	 * calculates hours clocked, taxes owed, and net and gross pay.
	 */
	public void calculatePay() {
		for (Timecard card : timecards) {
			if (card == null) {
				break;
			}
			this.hoursWorked += card.getHoursClocked();
		}

		this.grossPay = hourlyWages * hoursWorked;
		if (this.grossPay > 200) {
			double taxableIncome = grossPay - 200;
			this.fedTax = taxableIncome * .1;
		}

		stateTax = grossPay * .04;
		netPay = grossPay - (stateTax + fedTax);
	}

	/**
	 * Method to add a timecard to the Employee's timecard array.
	 * 
	 * @param timecard
	 */
	public void addTimecard(Timecard timecard) {
		this.timecards[numTimecards] = timecard;
		this.numTimecards++;
	}

	/**
	 * method to return a specific timecard, identified by its index in the
	 * Employee's timecard array.
	 * 
	 * @param index
	 * @return
	 */
	public Timecard getTimecard(int index) {
		return timecards[index];
	}

	/**
	 * Getter method returning the number of timecards.
	 * 
	 * @return
	 */
	public int getNumTimecards() {
		return this.numTimecards;
	}

	/**
	 * Getter method returning the employee number.
	 * 
	 * @return
	 */
	public int getEmpNum() {
		return empNum;
	}

	/**
	 * Getter method returning the Employee's first name.
	 * 
	 * @return
	 */
	public String getEmpFirstName() {
		return empFirstName;
	}

	/**
	 * Getter method returning the Employee's last name.
	 * 
	 * @return
	 */
	public String getEmpLastName() {
		return empLastName;
	}

	/**
	 * Getter method returning the Employee's hourly wages.
	 * 
	 * @return
	 */
	public double getHourlyWages() {
		return hourlyWages;
	}

	/**
	 * Getter method returning the Employee's hours worked.
	 * 
	 * @return
	 */
	public int getHoursWorked() {
		return hoursWorked;
	}

	/**
	 * Getter method to return the Employee's gross pay.
	 * 
	 * @return
	 */
	public double getGrossPay() {
		return grossPay;
	}

	/**
	 * Getter method returning the federal tax owed.
	 * 
	 * @return
	 */
	public double getFedTax() {
		return fedTax;
	}

	/**
	 * Getter method returning the state tax owed.
	 * 
	 * @return
	 */
	public double getStateTax() {
		return stateTax;
	}

	/**
	 * Getter method returning the net pay.
	 * 
	 * @return
	 */
	public double getNetPay() {
		return netPay;
	}

	/**
	 * Setter method to set an employee's first name.
	 * 
	 * @param empFirstName
	 */
	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}

	/**
	 * Setter method to set an employee's last name.
	 * 
	 * @param empLastName
	 */
	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}

	/**
	 * Setter method to set hourly wages.
	 * 
	 * @param hourlyWages
	 */
	public void setHourlyWages(double hourlyWages) {
		this.hourlyWages = hourlyWages;
	}

}
