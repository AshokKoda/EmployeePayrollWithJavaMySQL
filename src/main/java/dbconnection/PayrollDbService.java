package dbconnection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import common.Constants;

import java.sql.Connection;
import java.sql.Driver;

public class PayrollDbService {

	private static PayrollDbService payrollDbService;
	private Connection connection;

	public PayrollDbService() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver class is not found");
			e.printStackTrace();
		}
		listDrivers();
	}

	private void listDrivers() {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			System.out.println(driver.getClass().getName());
		}

	}

	public static PayrollDbService init() {
		if (payrollDbService == null) {
			payrollDbService = new PayrollDbService();
		}
		return payrollDbService;
	}

	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection(Constants.JDBC_STR, Constants.USERNAME, Constants.PASSWORD);
			System.out.println("Connection is established.");
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection can not established.");
			return null;
		}
	}

	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
