package edu.iup.cosc310.company.rpt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import edu.iup.cosc310.company.bo.Company;
import edu.iup.cosc310.company.bo.Department;
import edu.iup.cosc310.company.bo.Employee;
import edu.iup.cosc310.company.io.ReadDepartmentFile;
import edu.iup.cosc310.company.io.ReadEmployeeFile;

/**
 * Print the company report for a Company.
 * 
 * @author David T. Smith
 */
public class CompanyReport {
	private Company company = new Company();
	
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");	

	/**
	 * Main method to print the company report: Creates a company, loads
	 * Departments from the file name given in the first command line argument, and
	 * loads Employees from the file name given in the last command line
	 * argument
	 * 
	 * @param args - the command line arguments.
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out
					.println("Usage: java edu.iup.cosc310.company.rpt.CompanyReport <department file> <employee file>");
			System.exit(-99);
		}

		CompanyReport companyReport = new CompanyReport();
		
		try {
			companyReport.loadDepts(args[0]);
			companyReport.loadEmployees(args[1]);
			companyReport.printCompanyReport();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to run: " + e.getMessage());
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load departments from a text file.
	 * @param fileName - the filename of the file that contains the departments.
	 * @throws IOException - in the event the file cannot be opened or read.
	 */
	public void loadDepts(String fileName) throws NumberFormatException,
			IOException {
		ReadDepartmentFile in = new ReadDepartmentFile(fileName);
		Department department;

		while ((department = in.readDepartment()) != null) {
			company.addDepartment(department);
		}
	}
	
	/**
	 * Load Employees from a binary file.  The employees are added to the list of employees
	 * for their respective Department as indicated by deptCode.
	 * @param fileName - the name of the file that contains the employees.
	 * @throws Exception - catches an Exception.
	 */
	public void loadEmployees(String fileName) throws Exception {
		ReadEmployeeFile in = new ReadEmployeeFile(fileName);
		
		Employee employee;

		while ((employee = in.readEmployee()) != null) {
			Department dept = company.findDepartment(employee.getDeptCode());
			dept.addEmployee(employee);
		}
	}
	
	/**
	 * Prints a company report.  Report include information on the department
	 * and a list of all employees.
	 */
	public void printCompanyReport() {
		// loop over all departments
		for (int i = 0; i < company.getNoDepartments(); i++) {
			Department department = company.getDeparment(i);
			
			// print the department header
			System.out.println(department.getDeptName() + " Department");
			System.out.println();
			System.out.printf("%-20s%-20s\n", "Manager: ", department.getManager().getFirstName() + " " + department.getManager().getLastName());
			System.out.printf("%-20s%-20s\n", "Staff Size: ", department.getNoEmployees());
			System.out.printf("%-20s%d\n", "Vacation Days: ",department.getTotalVacationDays());
			System.out.println();
			
			// print the column labels for employees
			System.out.printf("%-5s %-20s %-12s %-8s %-10s\n", "ID",
					"Employee Name", "Hire Date", "Salary", "Vac Days");
			
			// loop over all employees in the department
			for (int j = 0; j < department.getNoEmployees(); j++) {
				Employee emp = department.getEmployee(j);
				System.out.printf("%-5s %-20s %-12s $%6.2f %6d\n",
						emp.getEmployeeId(),
						emp.getFullName(), 
						dateFormatter.format(emp.getHireDate()),
						emp.getSalary(),
						emp.getVacationDays());
			}
			
			System.out.println();
			System.out.println();
		}
	}

}
