package com.revature.mavenbanking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import com.revature.mavenbanking.dao.AccountStatusDao;
import com.revature.mavenbanking.dao.DAO;
import com.revature.mavenbanking.dao.postgresql.DAOUtilities;
import com.revature.mavenbanking.model.AccountStatus;

public class AccountStatusDaoImpl implements AccountStatusDao {
	
	private Connection connection = null;
	private PreparedStatement stmt = null;

	@Override
	public ArrayList<AccountStatus> getAllStatus() {
		String sql = "SELECT * FROM kmdm_account_status";
		
		try{
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			ArrayList<AccountStatus> result = new ArrayList<AccountStatus>();
			while(rs.next()){
				AccountStatus s = new AccountStatus();
				s.setStatusId(rs.getInt("status_id"));
				s.setStatusName(rs.getString("status"));
				result.add(s);
			}
			return result;
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public AccountStatus getAccountStatusById(int id) {
		String sql = "SELECT * FROM kmdm_account_status WHERE status_id = ?";
		
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			AccountStatus as = new AccountStatus();
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				as.setStatusId(rs.getInt("status_id"));
				as.setStatusName(rs.getString("status"));
			}
			return as;
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public AccountStatus getAccountStatusByStatus(String status) {
		String sql = "SELECT * FROM kmdm_account_status WHERE status = ?";
		AccountStatus s = null;
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, status);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				s = new AccountStatus();
				s.setStatusId(rs.getInt("status_id"));
				s.setStatusName(rs.getString("status"));
			}
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
		
	}

	@Override
	public boolean addAccountStatus(AccountStatus status) {
		String sql = "INSERT INTO kmdm_account_status (status) VALUES (?)";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, status.getStatusName());
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean updateAccountStatus(AccountStatus status) {
		String sql = "SELECT * FROM kmdm_account_status WHERE status = ?";
		AccountStatus s = null;
		
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, status.getStatusName());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				status.setStatusId(rs.getInt("status_id"));
			}
			rs.close();
			sql = "UPDATE kmdm_account_status SET status = ? WHERE status_id = ?";
			stmt.close();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, status.getStatusId());
			stmt.setString(2, status.getStatusName());
			
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
	public boolean deleteAccountStatusById(int id) {
		String sql = "DELETE FROM kmdm_account_status WHERE status_id = ?";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} finally {
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
