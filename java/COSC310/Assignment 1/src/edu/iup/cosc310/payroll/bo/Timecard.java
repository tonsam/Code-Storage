package edu.iup.cosc310.payroll.bo;

/**
 * Timecard object class for the company payroll reporting project.
 * 
 * @author David Kornish
 *
 */
public class Timecard {
	private int empNum;
	private int year;
	private int month;
	private int day;
	private int timeIn;
	private int timeOut;

	private int hoursClocked;

	/**
	 * Constructor for a Timecard object. Initializes instance variables.
	 * 
	 * @param empNum
	 * @param year
	 * @param month
	 * @param day
	 * @param timeIn
	 * @param timeOut
	 */
	public Timecard(int empNum, int year, int month, int day, int timeIn, int timeOut) {
		super();
		this.empNum = empNum;
		this.year = year;
		this.month = month;
		this.day = day;

		this.timeIn = timeIn;
		this.timeOut = timeOut;

		this.hoursClocked = timeOut - timeIn;
		if (this.hoursClocked > 6) {
			this.hoursClocked--;
		}
	}

	/**
	 * Getter method returning the employee number of the timecard.
	 * 
	 * @return
	 */
	public int getEmpNum() {
		return empNum;
	}

	/**
	 * Getter method returning the year.
	 * 
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Getter method returning the month.
	 * 
	 * @return
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Getter method returning the day.
	 * 
	 * @return
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Getter method returning the time clocked in.
	 * 
	 * @return
	 */
	public int getTimeIn() {
		return timeIn;
	}

	/**
	 * Getter method returning the time clocked out.
	 * 
	 * @return
	 */
	public int getTimeOut() {
		return timeOut;
	}

	/**
	 * Getter method returning the hours clocked.
	 * 
	 * @return
	 */
	public int getHoursClocked() {
		return hoursClocked;
	}

}
