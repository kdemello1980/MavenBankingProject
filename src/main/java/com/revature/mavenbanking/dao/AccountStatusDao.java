package com.revature.mavenbanking.dao;

import java.util.List;

import com.revature.mavenbanking.model.AccountStatus;

public interface AccountStatusDao {
	public List<AccountStatus> getAllStatus();
	public AccountStatus getAccountStatusById(int id);
	
	public boolean addAccountStatus(AccountStatus status);
	public boolean updateAccountStatus(String statusName);
	public boolean deleteAccountStatusById(int id);
}
