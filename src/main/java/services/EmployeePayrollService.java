package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.Constants;
import dbconnection.PayrollDbService;
import model.Employee;

public class EmployeePayrollService {

	static PayrollDbService payrollDbService;
	Connection connection;
	List<Employee> empList = new ArrayList<Employee>();
	Constants constants;
	
	public EmployeePayrollService() {
		constants = new Constants();
		payrollDbService = PayrollDbService.init();
		connection = payrollDbService.getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* FETCH EMPLOYEE DATA FROM DATABASE */
	public void getAllEmployeesData() {
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(constants.FETCH_EMP_DATA);
			
			while(rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getInt("id"));
				employee.setName(rs.getString("name"));
				employee.setGender(rs.getString("gender").charAt(0));
				employee.setPhoneno(rs.getString("phoneno"));
				employee.setAddress(rs.getString("address"));
				employee.setStartDate(rs.getDate("startDate"));
				empList.add(employee);
			}
			
			empList.forEach(e -> {
				System.out.println("ID: " + e.getId());
				System.out.println("Name: " + e.getName());
				System.out.println("Gender: " + e.getGender());
				System.out.println("Phone No: " + e.getPhoneno());
				System.out.println("Address: " + e.getAddress());
				System.out.println("Joining Date: " + e.getStartDate());
				System.out.println("<--------------------------------------------------->");
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
