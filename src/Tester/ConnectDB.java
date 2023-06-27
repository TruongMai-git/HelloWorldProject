package Tester;

import org.testng.annotations.Test;
import java.sql.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class ConnectDB {
	// Connection object
	static Connection con = null;
	// Statement object
	private static Statement stmt;
	// Constant for SSH properties
	String driverName = "org.postgresql.Driver";
	public static String DB_USER = "master";
	public static String DB_PASSWORD = "4JR7YS3gilF82xyjMMUb";
	public static String DB_NAME = "customer";

	@BeforeTest

	public void setUp() throws Exception {
		try {
			String dbUrl = "jdbc:mysql://localhost:5433/";

			// Connect to the database using JDBC
			con = DriverManager.getConnection(dbUrl + DB_NAME, DB_USER, DB_PASSWORD);
			if (con != null) {
				System.out.println("Get connection");
			} else {
				System.out.println("Cannot get connection");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(stmt);

		}
	}




	@Test
	public void test() {
		try {
			// Statement object to send the SQL statement to the Database
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("Select * from customer limit 10");
			if (result != null) {
				System.out.println("Result is: ");
				while (result.next()) {
					System.out.print(result.getString(1));
					System.out.print(" " + result.getString(2));
					System.out.print(" " + result.getString(3));
					System.out.println(" " + result.getString(4));
				}
			} else {
				System.out.println("No have result");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterTest

	public void tearDown() throws Exception {
		// Close DB connection
		if (con != null) {
			con.close();
		}
	}
}
