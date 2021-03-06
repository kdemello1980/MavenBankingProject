package com.kdemello.mavenbanking.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.kdemello.mavenbanking.model.*;

public interface AccountDao {
	public ArrayList<Account> getAllAccounts();
	public Account getAccountById(int id);
	public ArrayList<Account> getAccountsByType(AccountType type);
	public ArrayList<Account> getAccountsByStatus(AccountStatus status);
	public ArrayList<Account> getAccountsByUser(User user);
	public HashMap<User, ArrayList<Account>> getAllUserAccounts();
	
	public boolean addUserToAccount(Account account, User user);
	public boolean deleteUserFromAccount(Account account, User user);
	public int addAccount(Account account);
	public boolean updateAccount(Account account);
	public boolean deleteAccountById(int id);
}
