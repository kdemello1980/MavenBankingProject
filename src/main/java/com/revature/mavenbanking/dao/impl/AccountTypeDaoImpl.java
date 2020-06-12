package com.revature.mavenbanking.dao.impl;

import com.revature.mavenbanking.dao.AccountTypeDao;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.mavenbanking.dao.oracle.*;
import com.revature.mavenbanking.model.AccountType;

public class AccountTypeDaoImpl implements AccountTypeDao {
	Connection connection = null;
	PreparedStatement stmt = null;
	


	@Override
	public ArrayList<AccountType> getAllAccountTypes() {
		String sql = "SELECT * FROM kmdm_account_types";
		ArrayList<AccountType> list = new ArrayList<AccountType>();
		
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				AccountType temp = new AccountType();
				temp.setAccountType(rs.getString("type"));
				temp.setTypeId(rs.getInt("type_id"));
				list.add(temp);
			}
			rs.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public AccountType getAccountTypeById(int id) {
		String sql = "SELECT type, interest_rate, monthly_fee FROM kmdm_account_types WHERE type_id=?";
		AccountType temp = null;

		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				temp = new AccountType();
				temp.setAccountType(rs.getString("type"));
				temp.setTypeId(id);
				temp.setInterestRate(rs.getBigDecimal("interest_rate"));
				temp.setMonthlyFee(rs.getBigDecimal("monthly_fee"));
			}
			rs.close();
			return temp;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean addAccountType(AccountType type) {
		String sql = "INSERT INTO kmdm_account_types(type, interest_rate, monthly_fee, compound_months)\n" +
				"VALUES (?,?,?,?)";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, type.getAccountType());
			stmt.setBigDecimal(2, type.getInterestRate());
			stmt.setBigDecimal(3, type.getMonthlyFee());
			stmt.setInt(4, type.getCompoundMonths());
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean updateAccountType(AccountType type) {
		String sql = "UPDATE kmdm_account_types SET type = ?, monthly_fee = ?, interest_rate = ?\n" +
				"WHERE type_id = ?";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, type.getAccountType());
			stmt.setBigDecimal(2, type.getMonthlyFee());
			stmt.setBigDecimal(3, type.getInterestRate());
			stmt.setInt(4, type.getTypeId());
			
			if(stmt.executeUpdate() != 0) 
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	/*
	 * Not sure if this is a good idea.  I think the foreign key
	 * in the accounts table should prevent this from happening
	 * if the type_id being deleted is referenced by another
	 * record.
	 */
	@Override
	public boolean deleteAccountTypeById(int id) {
		String sql = "DELETE FROM kmdm_account_types WHERE type_id = ?";
		
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			closeResources();
			return false;
		} finally  {
			closeResources();
		}
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

