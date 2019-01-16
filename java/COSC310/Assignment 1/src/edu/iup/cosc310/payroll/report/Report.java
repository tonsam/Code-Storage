package edu.iup.cosc310.payroll.report;

import java.io.IOException;

import edu.iup.cosc310.payroll.bo.Company;
import edu.iup.cosc310.payroll.bo.Employee;
import edu.iup.cosc310.payroll.bo.Timecard;
import edu.iup.cosc310.payroll.io.EmployeeInputStream;
import edu.iup.cosc310.payroll.io.TimecardInputStream;

/**
 * Report class for the company payroll reporting project.
 * 
 * @author David Kornish
 *
 */
public class Report {

	/**
	 * Main method for the Report class. This is where all the action happens.
	 * Creates a company, loads in the employees and timecards, and prints the
	 * payroll report.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Company company = new Company();

		loadEmployees(company, args[0]);

		loadTimecards(company, args[1]);

		printReport(company);

	}

	/**
	 * Employee loader method for a company payroll report. Accepts the company to
	 * load the employees into and the employee data file to be read.
	 * 
	 * @param company
	 * @param arg
	 * @throws IOException
	 */
	public static void loadEmployees(Company company, String arg) throws IOException {
		Employee tempEmp;
		EmployeeInputStream empIn = new EmployeeInputStream(arg);

		while (true) {

			tempEmp = empIn.readEmployee();

			if (tempEmp == null) {
				break;
			} else {
				company.addEmployee(tempEmp);
			}
		}
	}

	/**
	 * Timecard loader method for a company payroll report. Accepts the company to
	 * load the timecards into and the timecard data file to be read.
	 * 
	 * @param company
	 * @param arg
	 * @throws IOException
	 */
	public static void loadTimecards(Company company, String arg) throws IOException {
		Timecard tempCard;
		TimecardInputStream cardIn = new TimecardInputStream(arg);

		// since timecards are not necessarily in order, we must parse through the
		// employees using the Company class's timecardMatch method.
		while (true) {

			tempCard = cardIn.readTimecard();

			if (tempCard == null) {
				break;
			} else {
				int cardOwnerNum = tempCard.getEmpNum();
				company.timecardMatch(cardOwnerNum).addTimecard(tempCard);
			}
		}
	}

	/**
	 * Print method for a company payroll report. Accepts the company and prints its
	 * report. Company must already be loaded.
	 * 
	 * @param company
	 */
	public static void printReport(Company company) {
		company.calculatePay();
		Employee currentEmp = null;

		for (int x = 0; x < company.getNumEmployees(); x++) {
			currentEmp = company.getEmployee(x);
			if (currentEmp.getNumTimecards() == 0) {
				continue;
			} else {
				System.out.println("      Supermarket Payroll Report");
				System.out.println();
				System.out.printf("Employee No:       %d%n", currentEmp.getEmpNum());
				System.out.printf("Employee name:     %s %s%n", currentEmp.getEmpFirstName(),
						currentEmp.getEmpLastName());
				System.out.println();
				System.out.printf("Hourly Wage:                       $  %5.2f %n", currentEmp.getHourlyWages());
				System.out.printf("Hours Worked:                            %2d %n", currentEmp.getHoursWorked());
				System.out.println();
				System.out.printf("Gross Pay:                         $ %6.2f %n", currentEmp.getGrossPay());
				System.out.printf("Federal Tax:                       $ %6.2f %n", currentEmp.getFedTax());
				System.out.printf("State Tax:                         $ %6.2f %n", currentEmp.getStateTax());
				System.out.printf("Net Pay:                           $ %6.2f %n", currentEmp.getNetPay());
				System.out.println();

				System.out.println("                  Time Log");
				System.out.println("   Date        In          Out       Hours");
				Timecard tempCard;
				for (int y = 0; y < currentEmp.getNumTimecards(); y++) {
					tempCard = currentEmp.getTimecard(y);
					int month = tempCard.getMonth();
					int day = tempCard.getDay();
					int year = tempCard.getYear();
					int in = tempCard.getTimeIn();
					int out = tempCard.getTimeOut();
					int hours = tempCard.getHoursClocked();
					System.out.printf("%02d/%02d/%4d   %2d:00        %2d:00        %2d   %n", month, day, year, in, out,
							hours);
				}

				System.out.println();
				System.out.println();

			}
		}
	}

}
