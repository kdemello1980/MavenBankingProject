package com.revature.mavenbanking.service;

import java.util.ArrayList;

import com.revature.mavenbanking.dao.impl.AccountDaoImpl;
import com.revature.mavenbanking.dao.impl.AccountTypeDaoImpl;
import com.revature.mavenbanking.model.Account;
import com.revature.mavenbanking.model.AccountStatus;
import com.revature.mavenbanking.model.AccountType;

public class AccountService {

	private AccountDaoImpl adi = new AccountDaoImpl();
	private AccountTypeDaoImpl atdi = new AccountTypeDaoImpl();
	
	/*
	 * Account DAO methods.
	 */
	public boolean addAccount(Account account){
		return adi.addAccount(account);
	}
	
	public boolean deleteAccountById(int id){
		return adi.deleteAccountById(id);
	}
	
	public Account getAccountById(int id){
		return adi.getAccountById(id);
	}
	
	public ArrayList<Account> getAccountsByStatus(AccountStatus status){
		return adi.getAccountsByStatus(status);
	}
	
	public ArrayList<Account> getAccountsByType(AccountType type){
		return adi.getAccountsByType(type);
	}
	
	public ArrayList<Account> getAllAccounts(){
		return adi.getAllAccounts();
	}
	
	public boolean updateAccount(Account account){
		return adi.updateAccount(account);
	}
	
	/*
	 * AccountType DAO methods.
	 */
	public boolean addAccountType(AccountType type){
		return atdi.addAccountType(type);
	}
	
	public boolean deleteAccountTypeById(int id){
		return atdi.deleteAccountTypeById(id);
	}
	
	public AccountType getAccountTypeById(int id){
		return atdi.getAccountTypeById(id);
	}
	
	public ArrayList<AccountType> getAllAccountTypes(){
		return atdi.getAllAccountTypes();
	}
	
	public boolean updateAccountType(AccountType type){
		return atdi.updateAccountType(type);
	}
}
