package com.revature.mavenbanking.dao;

import java.util.ArrayList;
import java.util.List;

import com.revature.mavenbanking.model.AccountType;
import com.revature.mavenbanking.model.User;

public interface AccountTypeDao {
	public ArrayList<AccountType> getAllAccountTypes();
	public AccountType getAccountTypeById(int id);
	public ArrayList<AccountType> getAccountTypesByPermission(User user);
	
	public int addAccountType(AccountType type);
	public boolean updateAccountType(AccountType type);
	public boolean deleteAccountTypeById(int id);
}
