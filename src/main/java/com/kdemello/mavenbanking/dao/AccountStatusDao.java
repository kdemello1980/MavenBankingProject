package com.kdemello.mavenbanking.dao;

import java.util.ArrayList;

import com.kdemello.mavenbanking.model.AccountStatus;

public interface AccountStatusDao {
	public ArrayList<AccountStatus> getAllStatus();
	public AccountStatus getAccountStatusById(int id);
	public AccountStatus getAccountStatusByStatus(String status);
	
	public boolean addAccountStatus(AccountStatus status);
	public boolean updateAccountStatus(AccountStatus status);
	public boolean deleteAccountStatusById(int id);
}
