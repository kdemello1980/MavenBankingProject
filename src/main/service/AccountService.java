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
	public BigDecimal deposit(Account account, double amount) {
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
	public boolean addAccount(Account account) throws Exception {
		if(adi.addAccount(account))
			return true;
		else
			throw new Exception("Failed to add new account");
	}
	
	public boolean deleteAccountById(int id) throws Exception {
		if (adi.deleteAccountById(id))
			return true;
		else
			throw new Exception("Failed to delete account " + id);
	}
	
	public Account getAccountById(int id) throws Exception {
		Account a = adi.getAccountById(id);
		if (a != null)
			return a;
		else
			throw new Exception("Failed to retrieve account " + id);
	}
	
	public ArrayList<Account> getAccountsByStatus(AccountStatus status) throws Exception {
		ArrayList<Account> list = adi.getAccountsByStatus(status);
		if (list != null)
			return list;
		else
			throw new Exception("Failed to retrieve accounts with status " + status.getStatusName());
	}
	
	public ArrayList<Account> getAccountsByType(AccountType type) throws Exception {
		ArrayList<Account> list = adi.getAccountsByType(type);
		if (list != null)
			return list;
		else
			throw new Exception("Failed to retrieve accounts of type " + type.getAccountType());
	}
	
	public ArrayList<Account> getAllAccounts() throws Exception {
		ArrayList<Account> list = adi.getAllAccounts();
		if (list != null)
			return list;
		else
			throw new Exception("Failed to retrieve all accounts.");
	}
	
	public boolean updateAccount(Account account) throws Exception {
		if (adi.updateAccount(account))
			return true;
		else
			throw new Exception("Failed to update account " + account.getAccountId());
	}
	
	public boolean addUserToAccount(Account account, User user) throws Exception {
		if (adi.addUserToAccount(account, user))
			return true;
		else
			throw new Exception("Failed to add " + user.getUsername() + " to account " + account.getAccountId());
	}
	
	public boolean deleteUserFromAccount(Account account, User user) throws Exception {
		if (adi.deleteUserFromAccount(account, user))
			return true;
		else
			throw new Exception("Failed to remove user " + user.getUsername() + " from account " + account.getAccountId());
	}
	
	/*
	 * AccountType DAO methods.
	 */
	public boolean addAccountType(AccountType type) throws Exception {
		if (atdi.addAccountType(type))
			return true;
		else
			throw new Exception("Failed to add account type " + type.getAccountType());
	}
	
	public boolean deleteAccountTypeById(int id) throws Exception {
		if (atdi.deleteAccountTypeById(id))
			return true;
		else
			throw new Exception("Failed to delete account type id: " + id);
	}
	
	public AccountType getAccountTypeById(int id) throws Exception {
		AccountType type = atdi.getAccountTypeById(id);
		if (type != null)
			return type;
		else
			throw new Exception("Failed to retrieve account type id: " + id);
	}
	
	public ArrayList<AccountType> getAllAccountTypes() throws Exception {
		ArrayList<AccountType> list = atdi.getAllAccountTypes();
		if (list != null)
			return list;
		else
			throw new Exception("Failed to retrieve all account types.");
	}
	
	public boolean updateAccountType(AccountType type) throws Exception {
		if (atdi.updateAccountType(type))
			return true;
		else
			throw new Exception("Failed to update account type " + type.getAccountType());
	}
}
