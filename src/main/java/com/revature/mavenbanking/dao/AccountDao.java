package com.revature.mavenbanking.dao;

import java.util.ArrayList;

import com.revature.mavenbanking.model.*;

public interface AccountDao {
	public ArrayList<Account> getAllAccounts();
	public Account getAccountById(int id);
	public ArrayList<Account> getAccountsByType(AccountType type);
	public ArrayList<Account> getAccountsByStatus(AccountStatus status);
	
	public boolean addAccount(Account account);
	public boolean updateAccount(Account account);
	public boolean deleteAccountById(int id);
}
