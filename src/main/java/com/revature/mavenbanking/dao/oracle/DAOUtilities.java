package com.revature.mavenbanking.dao.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOUtilities {
	private static final String CONNECTION_USERNAME = "admin";
	private static final String CONNECTION_PASSWORD = "12345678";
	private static final String URL = "jdbc:oracle:thin:@database-2.c0rzi76acgyn.us-east-1.rds.amazonaws.com:1521/FIRSTDB";
	private static Connection conneciton;

	public static synchronized Connection getConnection() throws SQLException {
		if (conneciton == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!");
				e.printStackTrace();
			}
			conneciton = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);			
			System.out.println("Opened connection...");

		}
		
		if (conneciton.isClosed()) {
//			System.out.println("Opening new connection...");
			conneciton = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);

		}
		return conneciton;
	}
	
	public static void closeConnection(Connection con) {
		try {
			if (con.isClosed())
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
}
