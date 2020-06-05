package com.revature.mavenbanking.dao.impl;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.mavenbanking.dao.AccountDao;
import com.revature.mavenbanking.dao.oracle.DAOUtilities;
import com.revature.mavenbanking.model.Account;
import com.revature.mavenbanking.model.AccountStatus;
import com.revature.mavenbanking.model.AccountType;

public class AccountDaoImpl implements AccountDao {
	Connection connection = null;
	PreparedStatement stmt = null;
	
	@Override
	public ArrayList<Account> getAllAccounts() {
		ArrayList<Account> accountList = new ArrayList<Account>();

		String sql = new String("SELECT a.id \"a.id\", a.balance \"a.balance\", s.id \"s.id\", s.status \"s.status\", t.id \"t.id\", t.type \"t.type\"\n" + 
				"FROM accounts a, account_status s, account_types t\n" + 
				"WHERE a.status = s.id AND a.type = t.id;");
//		String sql = "SELECT * FROM account_types";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				// Grab the s.id and s.status to create the AccountStatus object
				AccountStatus stat = new AccountStatus();
				stat.setStatusId(rs.getInt("s.id"));
				stat.setStatusName(rs.getString("s.status"));
				
				// Grab the t.id and t.type to get the account type.
				AccountType type = new AccountType();
				type.setTypeId(rs.getInt("t.id"));
				type.setAccountType(rs.getString("t.type"));
				
				// I'm not sure how to cast the MONEY type to double here, if it's necessary or even possible.
				// Using BigDecimal with the NUMERIC data type is recommended here.
				Account account = new Account(rs.getInt("a.id"),rs.getBigDecimal("a.balance"), stat, type);
				accountList.add(account);
			}
			return accountList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return accountList;
	}

	@Override
	public Account getAccountById(int id) {
		String sql = "SELECT a.id \"a.id\", a.balance \"a.balance\", s.id \"s.id\", s.status \"s.status\", t.id \"t.id\", t.type \"t.type\"\n"+
				"FROM accounts a, account_status s, account_types t \n" +
				"WHERE a.status=s.id AND a.type=t.id AND a.id=?";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			
			// Not sure why this isn't working.
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			Account account = null;
			AccountType type = null;
			AccountStatus status = null;

			while (rs.next()) {
				type = new AccountType();
				type.setAccountType(rs.getString("t.type"));
				type.setTypeId(rs.getInt("t.id"));
				
				status = new AccountStatus();
				status.setStatusId(rs.getInt("s.id"));
				status.setStatusName(rs.getString("s.status"));
				account = new Account(rs.getInt("a.id"), rs.getBigDecimal("a.balance"), status, type);
			}
			return account;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return null;
	}

	@Override
	public ArrayList<Account> getAccountsByType(AccountType type) {
		String sql = "SELECT a.id \"a.id\", a.balance \"a.balance\", s.id \"s.id\", s.status \"s.status\", t.id \"t.id\", t.type \"t.type\"\n"+
				"FROM accounts a, account_status s, account_types t \n" +
				"WHERE a.status=s.id AND a.type=t.id AND t.id=?";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, type.getTypeId());
			ResultSet rs = stmt.executeQuery();
			
			ArrayList<Account> accounts = new ArrayList<Account>();

			while (rs.next()) {
				AccountStatus status = new AccountStatus();
				status.setStatusId(rs.getInt("s.id"));
				status.setStatusName(rs.getString("s.status"));
				Account a = new Account(rs.getInt("a.id"), rs.getBigDecimal("a.balance"), status, type);
				accounts.add(a);
			}
			return accounts;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return null;
	}

	@Override
	public ArrayList<Account> getAccountsByStatus(AccountStatus status) {
		String sql = "SELECT a.id \"a.id\", a.balance \"a.balance\", s.id \"s.id\", s.status \"s.status\", t.id \"t.id\", t.type \"t.type\"\n"+
				"FROM accounts a, account_status s, account_types t \n" +
				"WHERE a.status=s.id AND a.type=t.id AND s.id=?";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, status.getStatusId());
			ResultSet rs = stmt.executeQuery();
			
			ArrayList<Account> accounts = new ArrayList<Account>();

			while (rs.next()) {
				AccountType type = new AccountType();
				type.setTypeId(rs.getInt("t.id"));
				type.setAccountType(rs.getString("t.type"));
				Account a = new Account(rs.getInt("a.id"), rs.getBigDecimal("a.balance"), status, type);
				accounts.add(a);
			}
			return accounts;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return null;
	}

	@Override
	public boolean addAccount(Account account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAccount(Account account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAccountById(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	// Closing all resources is important, to prevent memory leaks. 
	// Ideally, you really want to close them in the reverse-order you open them
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
}
