package controller;

import java.util.Scanner;

import dbconnection.PayrollDbService;
import services.EmployeePayrollService;

public class EmployeePayroll {

	static Scanner sc;
	PayrollDbService payrollDbService;

	public static void main(String[] args) {
		System.out.println("************** Welcome to Employee Payroll Service (MySQL) **************");

		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		System.out.println("<---------------- Fetch Employee Data ---------------->");
		employeePayrollService.getAllEmployeesData();
		System.out.println("<---------------- Update Salary with 3000000 ---------------->");
		PayrollDbService payrollDbService = new PayrollDbService();
		employeePayrollService.updateSalary(payrollDbService.getConnection());
		System.out.println("<---------------- Get data with date range ---------------->");
		employeePayrollService.getEmpDataWithDateRange("2021-01-01", "2022-01-01");
		System.out.println("<---------------- Get salary by gender ---------------->");
		employeePayrollService.getSalaryByGender();
		
		System.out.println("<---------------- Add new employee ---------------->");
		employeePayrollService.addNewEmployees();
		
		payrollDbService.close();

	}

}
