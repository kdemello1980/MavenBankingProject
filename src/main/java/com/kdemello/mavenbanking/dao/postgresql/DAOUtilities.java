package com.kdemello.mavenbanking.dao.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.kdemello.mavenbanking.dao.impl.AccountDaoImpl;



public class DAOUtilities {
	
	private static final String CONNECTION_USERNAME = "postgres";
	private static final String CONNECTION_PASSWORD = "postgres";
	private static final String URL = "jdbc:postgresql://localhost:5432/banking";
	private static Connection conneciton;

	public static synchronized Connection getConnection() throws SQLException {
		if (conneciton == null) {
			try {
				Class.forName("org.postgresql.Driver");
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
	
	public static AccountDaoImpl getAccountDaoImpl() {
		return new AccountDaoImpl();
	}
}
