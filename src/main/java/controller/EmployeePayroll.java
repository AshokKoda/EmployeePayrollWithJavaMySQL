package controller;

import java.util.Scanner;

import services.EmployeePayrollService;

public class EmployeePayroll {

	static Scanner sc;

	public static void main(String[] args) {
		System.out.println("************** Welcome to Employee Payroll Service (MySQL) **************");

		EmployeePayrollService employeePayrollService = new EmployeePayrollService();

		boolean exit = false;
		while (!exit) {
			System.out.println("<---------------- Select Menu ---------------->");
			System.out.println("1.Fetch Data\n0.Quit");
			sc = new Scanner(System.in);
			System.out.println("<-------------- Enter your option ------------->");
			int option = sc.nextInt();

			switch (option) {
			case 1:
				employeePayrollService.getAllEmployeesData();
				break;
			case 0:
				exit = true;
				System.out.println("******** Thank You **********");
				break;
			default:
				System.out.println("Invalid option.Please try again.");
			}

		}

	}

}
