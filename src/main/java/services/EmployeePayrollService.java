package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import common.Constants;
import common.Util;
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

			while (rs.next()) {
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

	/* UPDATE SALARY */
	public void updateSalary(Connection connection) {
		try {
			PreparedStatement ps = connection.prepareStatement(constants.EMP_UPDATE_SALARY);

			double basic_pay = 3000000;
			double deduction = Util.formatDoubleValue(basic_pay * 0.1);
			double taxable_pay = Util.formatDoubleValue(basic_pay - deduction);
			double tax = Util.formatDoubleValue(taxable_pay * 0.2);
			double net_pay = Util.formatDoubleValue(taxable_pay - tax);

			ps.setDouble(1, basic_pay);
			ps.setDouble(2, deduction);
			ps.setDouble(3, taxable_pay);
			ps.setDouble(4, tax);
			ps.setDouble(5, net_pay);
			ps.setInt(6, 1);

			int status = ps.executeUpdate();

			if (status > 0) {
				System.out.println("Salary is updated with " + basic_pay + " successfully.");
			} else {
				System.out.println("There is some error occurs.");
			}
			System.out.println("<--------------------------------------------------->");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* GET EMPLOYEE DATA WITH DATE RANGE */
	public void getEmpDataWithDateRange(String startDate, String endDate) {
		try {
			PreparedStatement ps = connection.prepareStatement(constants.EMP_DATA_BASED_ON_JOIN_DATE);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getInt("id"));
				employee.setName(rs.getString("name"));
				employee.setGender(rs.getString("gender").charAt(0));
				employee.setPhoneno(rs.getString("phoneno"));
				employee.setAddress(rs.getString("address"));
				employee.setStartDate(rs.getDate("startDate"));

				System.out.println(employee);
			}
			System.out.println("<--------------------------------------------------->");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* GET SALARY DETAILS BY GENDER */
	public void getSalaryByGender() {
		try {
			PreparedStatement psSum = connection.prepareStatement(constants.EMP_SALARY_GROUP_BY_GENDER); // SUM
			ResultSet rsSum = psSum.executeQuery();
			while (rsSum.next()) {
				System.out.println("Gender: " + rsSum.getString("gender"));
				System.out.println("Sum of salary: " + rsSum.getDouble("SUM(s.basic_pay)"));
			}

			PreparedStatement psMax = connection.prepareStatement(constants.MAX_EMP_SALARY_BY_GENDER); // MAX
			ResultSet rsMax = psMax.executeQuery();
			while (rsMax.next()) {
				System.out.println("Gender: " + rsMax.getString("gender"));
				System.out.println("Maximum of salary: " + rsMax.getDouble("MAX(s.basic_pay)"));
			}

			PreparedStatement psMin = connection.prepareStatement(constants.MIN_EMP_SALARY_BY_GENDER); // MIN
			ResultSet rsMin = psMin.executeQuery();
			while (rsMin.next()) {
				System.out.println("Gender: " + rsMin.getString("gender"));
				System.out.println("Minimum of salary: " + rsMin.getDouble("MIN(s.basic_pay)"));
			}

			PreparedStatement psAvg = connection.prepareStatement(constants.AVG_EMP_SALARY_BY_GENDER); // AVG
			ResultSet rsAvg = psAvg.executeQuery();
			while (rsAvg.next()) {
				System.out.println("Gender: " + rsAvg.getString("gender"));
				System.out.println("Average of salary: " + Util.formatDoubleValue(rsAvg.getDouble("AVG(s.basic_pay)")));
			}

			PreparedStatement psCount = connection.prepareStatement(constants.COUNT_EMP_SALARY_BY_GENDER); // COUNT
			ResultSet rsCount = psCount.executeQuery();
			while (rsCount.next()) {
				System.out.println("Gender: " + rsCount.getString("gender"));
				System.out.println("Count of genders: " + rsCount.getInt("COUNT(s.basic_pay)"));
			}
			System.out.println("<--------------------------------------------------->");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* ADD NEW EMPLOYEE DATA */
	public void addNewEmployees() {
		Scanner sc = new Scanner(System.in);
		try {
			PreparedStatement ps = connection.prepareStatement(constants.NEW_EMP_ADD, Statement.RETURN_GENERATED_KEYS);
			System.out.println("Enter the employee name: ");
			String name = sc.nextLine();
			ps.setString(1, name);

			System.out.println("Enter the gender (M/F): ");
			ps.setString(2, sc.nextLine());

			System.out.println("Enter the joining date (YYYY-MM-DD): ");
			Date joinDate = Date.valueOf(sc.nextLine());
			ps.setDate(3, joinDate);

			System.out.println("Enter the phone number: ");
			ps.setString(4, sc.nextLine());

			System.out.println("Enter the address: ");
			ps.setString(5, sc.nextLine());

			int status = ps.executeUpdate();

			if (status > 0) {
				int id = 0;
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					System.out.println(rs);
					id = rs.getInt(1);
				}
				System.out.println("Enter the salary : ");
				double salary = sc.nextDouble();
				addSalaryDetails(salary, id);
			}
			System.out.println("<--------------------------------------------------->");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sc.close();
	}

	/* ADD NEW EMPLOYEE SALARY DETAILS */
	private void addSalaryDetails(double salary, int id) {
		try {
			PreparedStatement ps = connection.prepareStatement(constants.NEW_SALARY_DETAILS);

			double deduction = Util.formatDoubleValue(salary * 0.1);
			double taxable_pay = Util.formatDoubleValue(salary - deduction);
			double tax = Util.formatDoubleValue(taxable_pay * 0.2);
			double net_pay = Util.formatDoubleValue(taxable_pay - tax);

			ps.setDouble(1, salary);
			ps.setDouble(2, deduction);
			ps.setDouble(3, taxable_pay);
			ps.setDouble(4, tax);
			ps.setDouble(5, net_pay);
			ps.setInt(6, id);

			int status = ps.executeUpdate();
			if (status > 0) {
				System.out.println("Employee added successfully.");
				connection.commit();
			} else {
				System.out.println("There is some error occurs.");
			}
			System.out.println("<--------------------------------------------------->");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/* DELETE EMPLOYEE FROM DATABASE */
	public void deleteEmployee() {
		Scanner sc = new Scanner(System.in);
		try {
			PreparedStatement ps = connection.prepareStatement(constants.DELETE_EMP_DETAILS);
			System.out.println("Enter the ID to delete: ");
			int id = sc.nextInt();
			ps.setInt(1, id);
			int count = ps.executeUpdate();
			System.out.println("Data deleted successfully." + "Total count: " + count);
			connection.setAutoCommit(true);
			sc.close();
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
}
