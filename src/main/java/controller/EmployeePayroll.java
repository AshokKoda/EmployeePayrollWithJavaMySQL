package controller;

import dbconnection.PayrollDbService;

public class EmployeePayroll {

	public static void main(String[] args) {
		System.out.println("************** Welcome to Employee Payroll Service (MySQL) **************");

		PayrollDbService payrollDbService = new PayrollDbService();
		System.out.println("Please wait Database is connecting......");
		payrollDbService.getConnection();

	}

}
