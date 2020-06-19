package com.revature.mavenbanking.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.mavenbanking.dao.AccountDao;
import com.revature.mavenbanking.dao.postgresql.DAOUtilities;
import com.revature.mavenbanking.model.Account;
import com.revature.mavenbanking.model.AccountStatus;
import com.revature.mavenbanking.model.AccountType;
import com.revature.mavenbanking.model.User;

public class AccountDaoImpl implements AccountDao {
	private Connection connection = null;
	private PreparedStatement stmt = null;
	
	@Override
	public ArrayList<Account> getAllAccounts() {
		ArrayList<Account> accountList = new ArrayList<Account>();
//		ArrayList<ArrayList<Account>> foo = new ArrayList<ArrayList<Account>>();

		String sql = new String("SELECT a.account_id \"a.account_id\", a.balance \"a.balance\", "+
				"s.status_id \"s.status_id\", s.status \"s.status\","+ 
				"t.type_id \"t.type_id\", t.type_name \"t.type_name\"" + 
				"FROM kmdm_accounts a, kmdm_account_status s, kmdm_account_types t\n" + 
				"WHERE a.status = s.status_id AND a.type_id = t.type_id");
//		String sql = "SELECT * FROM account_types";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				// Grab the s.id and s.status to create the AccountStatus object
				AccountStatus stat = new AccountStatus();
				stat.setStatusId(rs.getInt("s.status_id"));
				stat.setStatusName(rs.getString("s.status"));
				
				// Grab the t.id and t.type to get the account type.
				AccountType type = new AccountType();
				type.setTypeId(rs.getInt("t.type_id"));
//				type.setAccountType(rs.getString("t.type"));
				
				// I'm not sure how to cast the MONEY type to double here, if it's necessary or even possible.
				// Using BigDecimal with the NUMERIC data type is recommended here.
				Account account = new Account(rs.getInt("a.account_id"),rs.getBigDecimal("a.balance"), stat, type);
				accountList.add(account);
			}
			rs.close();
			
			AccountTypeDaoImpl dao = new AccountTypeDaoImpl();
			for (Account a : accountList){
				a.setType(dao.getAccountTypeById(a.getType().getTypeId()));
			}
			return accountList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public Account getAccountById(int id) {
		String sql = "SELECT a.account_id \"a.account_id\", a.balance \"a.balance\", s.status_id \"s.status_id\", t.type_id \"t.type_id\"\n"+
				"FROM kmdm_accounts a, kmdm_account_status s, kmdm_account_types t \n" +
				"WHERE a.status = s.status_id AND a.type_id = t.type_id AND a.account_id=?";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			
			// Not sure why this isn't working.
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			Account account = null;
			int type = 0;
			int status = 0;
			if (rs.next()) {
				do {
					account = new Account();
					account.setAccountId(rs.getInt("a.account_id"));
					account.setBalance(rs.getBigDecimal("a.balance"));
					status = rs.getInt("s.status_id");
					type = rs.getInt("t.type_id");
				} while (rs.next());
			}
			rs.close();
			if (account != null) {
				AccountType at = new AccountTypeDaoImpl().getAccountTypeById(type);
				AccountStatus as = new AccountStatusDaoImpl().getAccountStatusById(status);
				account.setType(at);
				account.setStatus(as);
			}
			return account;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public ArrayList<Account> getAccountsByType(AccountType type) {
		String sql = "SELECT a.account_id \"a.id\", a.balance \"a.balance\", s.status_id \"s.id\", s.status \"s.status\", t.type_id \"t.id\", t.type \"t.type\"\n"+
				"FROM kmdm_accounts a, kmdm_account_status s, kmdm_account_types t \n" +
				"WHERE a.status=s.status_id AND a.type=t.type_id AND t.type_id=?";
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
			rs.close();
			return accounts;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public ArrayList<Account> getAccountsByStatus(AccountStatus status) {
		String sql = "SELECT a.account_id \"a.id\", a.balance \"a.balance\", s.status_id \"s.id\", s.status \"s.status\", t.type_id \"t.id\", t.type \"t.type\"\n"+
				"FROM kmdm_accounts a, kmdm_account_status s, kmdm_account_types t \n" +
				"WHERE a.status=s.status_id AND a.type=t.type_id AND s.status_id=?";
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
			rs.close();
			return accounts;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public int addAccount(Account account) {
		String sql = "INSERT INTO kmdm_accounts (balance, status, type_id) VALUES (?, ?, ?)";
		try {
			String[] generatedColumns = {"account_id"};
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql, generatedColumns);
			stmt.setBigDecimal(1, account.getBalance());
			stmt.setInt(2, account.getStatus().getStatusId());
			stmt.setInt(3, account.getType().getTypeId());

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				int key = (int) rs.getLong(1);
//				System.out.println(key);
				return key;
			} else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean updateAccount(Account account) {
		String sql = "UPDATE kmdm_accounts SET balance=?, status=?, type_id=? WHERE account_id=?";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setBigDecimal(1, account.getBalance());
			stmt.setInt(2, account.getStatus().getStatusId());
			stmt.setInt(3, account.getType().getTypeId());
			stmt.setInt(4, account.getAccountId());
			
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
	public boolean deleteAccountById(int id) {
		String sql = "DELETE FROM kmdm_accounts WHERE account_id = ?";
		
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
			return false;
		} finally {
			closeResources();
		}
	}

	/* (non-Javadoc)
	 * @see com.revature.mavenbanking.dao.AccountDao#addUserToAccount(com.revature.mavenbanking.model.Account, com.revature.mavenbanking.model.User)
	 */
	@Override
	public boolean addUserToAccount(Account account, User user) {
		String sql = "INSERT INTO kmdm_user_accounts (account_id, user_id) VALUES (?,?)";
		try{
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, account.getAccountId());
			stmt.setInt(2, user.getUserId());
			
			if(stmt.executeUpdate() != 0)
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
	

	/* (non-Javadoc)
	 * @see com.revature.mavenbanking.dao.AccountDao#deleteUserFromAccount(com.revature.mavenbanking.model.Account, com.revature.mavenbanking.model.User)
	 */
	@Override
	public boolean deleteUserFromAccount(Account account, User user) {
		String sql = "DELETE FROM kmdm_user_accounts WHERE user_id = ? AND account_id = ?";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, user.getUserId());
			stmt.setInt(2, account.getAccountId());
			
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

	/*
	 * (non-Javadoc)
	 * @see com.revature.mavenbanking.dao.AccountDao#getAccountsByUser(com.revature.mavenbanking.model.User)
	 * 
	 * 
	 * This one's a doozy.
	 */
	@Override
	public ArrayList<Account> getAccountsByUser(User user) {
		ArrayList<Account> accts = null;
		String sql ="SELECT A.account_id \"A.account_id\", A.type_id \"A.type_id\", A.status \"A.status\"," +
				"A.balance \"A.balance\", T.type_id \"T.type_id\", T.type_name \"T.type_name\", S.status_id " +
						"\"S.status_id\", S.status \"S.status\", U.user_id \"U.User_id\"\n" +
					"FROM kmdm_accounts A\n" +
						"INNER JOIN kmdm_account_types T ON A.type_id = T.type_id\n" +
							"INNER JOIN kmdm_account_status S ON A.status = S.status_id\n" +
								"INNER JOIN kmdm_user_accounts U ON u.account_id = A.account_id\n" +
									"WHERE U.user_id = ? ORDER BY A.account_id";
		
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, user.getUserId());
			HashMap<Account, Integer> account_status = new HashMap<Account, Integer>();
			HashMap<Account, Integer> account_type = new HashMap<Account, Integer>();
			accts = new ArrayList<Account>();

			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				Account a = new Account();
				a.setAccountId(rs.getInt("A.account_id"));
				a.setBalance(rs.getBigDecimal("A.balance"));
				
				account_status.put(a, Integer.valueOf(rs.getInt("A.status")));
				account_type.put(a, Integer.valueOf(rs.getInt("A.type_id")));

				accts.add(a);
			}
			rs.close();
			
			// Add the status objects.
			AccountStatusDaoImpl sdi = new AccountStatusDaoImpl();
			for (Account a : accts){
				int statusId = account_status.get(a).intValue();
				a.setStatus(sdi.getAccountStatusById(statusId));
			}
			
			// Add the type objects.
			AccountTypeDaoImpl tdi = new AccountTypeDaoImpl();
			for (Account a : accts){
				int typeId = account_type.get(a).intValue();
				a.setType(tdi.getAccountTypeById(typeId));
			}
			return accts;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}	
	}

	
	/* 
	 *    /\ /\  
	 *    |   |  
	 *    That's not a doozy.  THIS is a doozy.
	 *                         |   |
	 *                        \/  \/
	 */
	@Override
	public HashMap<User, ArrayList<Account>> getAllUserAccounts() {
		ArrayList<User> users = new UserDaoImpl().getAllUsers();
		if (users != null){
			HashMap<User, ArrayList<Account>> userAccounts = new HashMap<User, ArrayList<Account>>();
			for (User u: users){
				userAccounts.put(u, this.getAccountsByUser(u));
			}
			return userAccounts;
		} else {
			return null;
		}
	}
	
	/*
	 * /\ /\
	 *  | |
	 * Maybe not.  Data structures ROCK!!!
	 */
}
