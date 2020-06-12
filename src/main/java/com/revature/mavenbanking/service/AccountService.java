package com.revature.mavenbanking.service;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.revature.mavenbanking.dao.impl.AccountDaoImpl;
import com.revature.mavenbanking.dao.impl.AccountTypeDaoImpl;
import com.revature.mavenbanking.model.Account;
import com.revature.mavenbanking.model.AccountStatus;
import com.revature.mavenbanking.model.AccountType;
import com.revature.mavenbanking.model.User;

public class AccountService {

	private AccountDaoImpl adi = new AccountDaoImpl();
	private AccountTypeDaoImpl atdi = new AccountTypeDaoImpl();
	
	/*
	 * Service methods.
	 */
	
	/*'
	 * calculateInterest: computes the interest for the given number of months compounded
	 * by the period specified in the AccountType and updates the database with the new balance.
	 * 
	 * Returns the new balance upon successful update in the database.  Null otherwise.
	 */
	public BigDecimal calculateInterest(Account account, int months){
		AccountType type = atdi.getAccountTypeById(account.getType().getTypeId());
		AccountStatus status = account.getStatus();
		if ( ! status.getStatusName().equals("Open")){
			return null;
		}
		BigDecimal newBalance = null;
		
		// Get the number of time periods.
		int periods = months / type.getCompoundMonths();
		int remainder = months % type.getCompoundMonths();
		
		// BigDecimals are ugly because there's no operator overloading in Java.
		newBalance = account.getBalance().multiply((BigDecimal.valueOf(1.0).add(type.getInterestRate()))).pow(periods);
		BigDecimal interest  = newBalance.multiply(type.getInterestRate()).multiply(BigDecimal.valueOf((double)remainder));
		newBalance.add(interest);
		account.setBalance(newBalance);
		if (adi.updateAccount(account)){
			return newBalance;
		} else {
			return null;
		}
	}
	
	/*
	 * deposit: adds the specified amount to the current balance and returns the new
	 * balance on successful database update, null otherwise.
	 */
	public BigDecimal deposit(Account account, double amount){
		account.setBalance(account.getBalance().add(BigDecimal.valueOf(amount)));
		if (adi.updateAccount(account))
			return account.getBalance();
		else
			return null;
	}
	
	/*
	 * withdraw: subtracts the amount specified from the account. Returns the resulting balance
	 * upon successful database update, null otherwise. Throws an exception if there are insufficient
	 * funds to withdraw.
	 */
	public BigDecimal withdraw(Account account, double amount) throws Exception {
		if (account.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0){
			Exception e = new Exception("Insufficient funds for withdraw.");
			throw e;
		} else {
			account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(amount)));
			if (adi.updateAccount(account))
				return account.getBalance();
			else
				return null;	
		}
	}
	
	/*
	 * transfer: subtracts the amount from the source account and adds it to the second.
	 * returns true on success or false on failure. Throws an exception if there  are insufficient
	 * funds to transfer.
	 */
	public boolean transfer(Account source, Account destination, double amount) throws Exception {
		if (source.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0){
			Exception e = new Exception("Insufficient funds for transfer");
			throw e;
		} else {
			source.setBalance(source.getBalance().subtract(BigDecimal.valueOf(amount)));
			destination.setBalance(destination.getBalance().add(BigDecimal.valueOf(amount)));
			
			if (adi.updateAccount(source) && adi.updateAccount(destination))
				return true;
			else
				return false;
		}
	}
	
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
	
	public boolean addUserToAccount(Account account, User user){
		return adi.addUserToAccount(account, user);
	}
	
	public boolean deleteUserFromAccount(Account account, User user){
		return adi.deleteUserFromAccount(account, user);
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
