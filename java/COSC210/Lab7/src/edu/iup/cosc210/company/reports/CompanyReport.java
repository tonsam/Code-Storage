package edu.iup.cosc210.company.reports;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.iup.cosc210.company.bo.Company;
import edu.iup.cosc210.company.bo.Department;
import edu.iup.cosc210.company.bo.Employee;
import edu.iup.cosc210.company.io.DepartmentReader;
import edu.iup.cosc210.company.io.EmployeeInputStream;

/**
 * The company report.  Prints information on departments and employees loaded from a department file
 * and employee file.
 * 
 * @author David T. Smith
 */
public class CompanyReport {
	private Company company = new Company();
	

	/**
	 * Main method to print the company report: Creates a company Loads
	 * Departments from the file name given in the first command line argument
	 * Loads Employees from the file name given in the last command line
	 * argument.
	 * 
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out
					.println("Usage: java edu.iup.cosc210.reports.CompanyReport <department file> <employee file>");
			System.exit(-100);
		}

		CompanyReport companyReport = new CompanyReport();
		
		try {
			companyReport.loadDepts(args[0]);
			companyReport.loadEmployees(args[1]);
			companyReport.printCompanyReport();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file: " + e.getMessage());
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load departments from a text file.
	 * 
	 * @param fileName The filename of the file that contains the departments.
	 * 
	 * @throws IOException In the event the file cannot be opened or read.
	 */
	public void loadDepts(String fileName) throws NumberFormatException,
			IOException {
		DepartmentReader in = new DepartmentReader(fileName);
		Department department;

		while ((department = in.readDepartment()) != null) {
			company.addDepartment(department);
		}
		
		in.close();
	}
	
	/**
	 * Load Employees from a binary file.  The employees are added to the list of employees
	 * for their respective Department as indicated by deptCode.
	 * 
	 * @param fileName The name of the file that contains the employees.
	 * 
	 * @throws Exception In the event the file cannot be opened or read.
	 */
	public void loadEmployees(String fileName) throws Exception {
		EmployeeInputStream in = new EmployeeInputStream(fileName);
		
		Employee employee;

		while ((employee = in.readEmployee()) != null) {
			// find the department to which the employee will be added
			Department dept = company.findDepartment(employee.getDeptCode());
			dept.addEmployee(employee);
		}
		
		in.close();
	}
	
	/**
	 * Prints a company report.  Report include information on the department
	 * and a list of all employees.
	 */
	public void printCompanyReport() {
		// loop over all departments
		for (int i = 0; i < company.getNoDepts(); i++) {
			Department department = company.getDeparment(i);
			
			// print the department header
			System.out.println(department.getDeptName() + " Department");
			System.out.println();
			System.out.printf("%-20s%-20s\n", "Manager: ", department.getManager().getFullName());
			System.out.printf("%-20s%-20s\n", "Staff Size: ", department.getNoEmployees());
			System.out.printf("%-20s%d\n", "Vacation Days: ",department.getTotalVacationDays());
			System.out.println();
			
			// print the column labels for employees
			System.out.printf("%-3s %-22s %-12s %-11s %5s %-10s\n", "ID",
					"Employee Name", "Hire Date", "  Salary", "Exmpt", "Vac Days");
			
			// loop over all employees in the department
			for (int j = 0; j < department.getNoEmployees(); j++) {
				Employee emp = department.getEmployee(j);
				System.out.printf("%3d %-22s %-12s $%10.2f   %s  %6d\n",
						emp.getEmployeeId(),
						emp.getFullName(), 
						Company.standardDateFormat.format(emp.getHireDate()),
						emp.getSalary(),
						!emp.isExempt() ? " " : "Y",
						emp.getVacationDays());
			}
			
			System.out.println();
			System.out.println();
		}
	}
}
