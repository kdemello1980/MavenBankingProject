package com.revature.mavenbanking.dao;

import java.util.ArrayList;

import com.revature.mavenbanking.model.AccountStatus;

public interface AccountStatusDao {
	public ArrayList<AccountStatus> getAllStatus();
	public AccountStatus getAccountStatusById(int id);
	
	public boolean addAccountStatus(AccountStatus status);
	public boolean updateAccountStatus(AccountStatus status);
	public boolean deleteAccountStatusById(int id);
}
