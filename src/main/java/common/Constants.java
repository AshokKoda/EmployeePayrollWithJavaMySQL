package common;

public class Constants {

	/* MySql Connection configuration */
	public static final String JDBC_STR = "jdbc:mysql://localhost:3306/payroll_service";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "Koda@1996";

	/* SQL QUERIES */
	public final String FETCH_EMP_DATA = "select * from employee_payroll";
	public final String EMP_UPDATE_SALARY = "update salary_tbl set basic_pay=?,"
			+ "deduction=?, taxable_pay=?, tax=?, net_pay=? where id=?";
	public final String EMP_DATA_BASED_ON_JOIN_DATE = "SELECT * FROM employee_payroll where startDate between CAST(? AS DATE) AND CAST(? AS DATE)";

	public final String EMP_SALARY_GROUP_BY_GENDER = "select ep.gender, SUM(s.basic_pay) from employee_payroll ep, salary_tbl s where ep.id=s.emp_id GROUP BY gender";
	public final String MAX_EMP_SALARY_BY_GENDER = "select ep.gender, MAX(s.basic_pay) from employee_payroll ep, salary_tbl s where ep.id=s.emp_id GROUP BY gender";
	public final String MIN_EMP_SALARY_BY_GENDER = "select ep.gender, MIN(s.basic_pay) from employee_payroll ep, salary_tbl s where ep.id=s.emp_id GROUP BY gender";
	public final String AVG_EMP_SALARY_BY_GENDER = "select ep.gender, AVG(s.basic_pay) from employee_payroll ep, salary_tbl s where ep.id=s.emp_id GROUP BY gender";
	public final String COUNT_EMP_SALARY_BY_GENDER = "select ep.gender, COUNT(s.basic_pay) from employee_payroll ep, salary_tbl s where ep.id=s.emp_id GROUP BY gender";

	public final String NEW_EMP_ADD = "insert into employee_payroll (name, gender, startDate, phoneno, address) values (?,?,?,?,?);";
	public final String NEW_SALARY_DETAILS = "insert into salary_tbl (basic_pay,deduction, taxable_pay, tax, net_pay,emp_id)"
			+ "values (?,?,?,?,?,?)";
	public final String DELETE_EMP_DETAILS = "delete from employee_payroll where id =?";
}
