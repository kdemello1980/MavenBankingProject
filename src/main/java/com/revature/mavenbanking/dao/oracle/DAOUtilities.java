package com.revature.mavenbanking.dao.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.revature.mavenbanking.dao.impl.*;

public class DAOUtilities {
	private static final String CONNECTION_USERNAME = "kenneth";
	private static final String CONNECTION_PASSWORD = "kenneth";
	private static final String URL = "jdbc:oracle:thin:@192.168.0.183:1521/orcl";
	private static Connection conneciton;

	public static synchronized Connection getConnection() throws SQLException {
		if (conneciton == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!");
				e.printStackTrace();
			}
			conneciton = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);			System.out.println("Opening new connection...");
			System.out.println("Opened connection...");

		}
		
		if (conneciton.isClosed()) {
//			System.out.println("Opening new connection...");
			conneciton = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);

		}
		return conneciton;
	}
	
	public static AccountDaoImpl getAccountDaoImpl() {
		return new AccountDaoImpl();
	}
}
