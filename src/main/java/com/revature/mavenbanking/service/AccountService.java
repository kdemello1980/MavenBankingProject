package com.revature.mavenbanking.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.revature.mavenbanking.dao.impl.AccountDaoImpl;
import com.revature.mavenbanking.dao.impl.AccountStatusDaoImpl;
import com.revature.mavenbanking.dao.impl.AccountTypeDaoImpl;
import com.revature.mavenbanking.exceptions.AddAccountException;
import com.revature.mavenbanking.exceptions.RetrieveAccountException;
import com.revature.mavenbanking.exceptions.UpdateAccountException;
import com.revature.mavenbanking.model.Account;
import com.revature.mavenbanking.model.AccountStatus;
import com.revature.mavenbanking.model.AccountType;
import com.revature.mavenbanking.model.User;

public class AccountService {

	private AccountDaoImpl adi = new AccountDaoImpl();
	private AccountTypeDaoImpl atdi = new AccountTypeDaoImpl();
	private AccountStatusDaoImpl asdi = new AccountStatusDaoImpl();
	
	/*
	 * Service methods.
	 */
	
	/*'
	 * calculateInterest: computes the interest for the given number of months compounded
	 * by the period specified in the AccountType and updates the database with the new balance.
	 * 
	 * Returns the new balance upon successful update in the database.  Null otherwise.
	 */
	public BigDecimal calculateInterest(Account account, int months) throws UpdateAccountException {
		AccountStatus status = account.getStatus();
		if ( ! status.getStatusName().equals("Open")){
			return null;
		}
		System.out.println("made it");
		// Get the number of time periods.
		double period = months / 12;
//		int remainder = months % type.getCompoundMonths();
		
		double balance = account.getBalance().doubleValue();
		double interest = account.getType().getInterestRate().doubleValue();
		double newBalance = balance * (1 + interest * period);

		System.out.println(newBalance);
		BigDecimal bd = BigDecimal.valueOf(newBalance);
		account.setBalance(bd);
		if (adi.updateAccount(account)){
			return bd;
		} else {
			throw new UpdateAccountException("Failed to calculate interest.");
		}
	}
	
	/*
	 * deposit: adds the specified amount to the current balance and returns the new
	 * balance on successful database update, null otherwise.
	 */
	public BigDecimal deposit(Account account, double amount) throws UpdateAccountException {
		account.setBalance(account.getBalance().add(BigDecimal.valueOf(amount)));
		if (adi.updateAccount(account))
			return account.getBalance();
		else
			throw new UpdateAccountException("Failed to save depost");
	}
	
	/*
	 * withdraw: subtracts the amount specified from the account. Returns the resulting balance
	 * upon successful database update, null otherwise. Throws an exception if there are insufficient
	 * funds to withdraw.
	 */
	public BigDecimal withdraw(Account account, double amount) throws UpdateAccountException {
		if (account.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0){
			throw new UpdateAccountException("Insufficient funds for withdraw.");
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
	public boolean transfer(Account source, Account destination, double amount) throws UpdateAccountException {
		if (source.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0){
			throw new UpdateAccountException("Insufficient funds for transfer");
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
	public int addAccount(Account account) throws AddAccountException {
		int a = adi.addAccount(account);
		System.out.println(a);
		if(a != 0)
			return a;
		else
			throw new AddAccountException("Failed to add new account");
	}
	
	public boolean deleteAccountById(int id) throws UpdateAccountException {
		if (adi.deleteAccountById(id))
			return true;
		else
			throw new UpdateAccountException("Failed to delete account " + id);
	}
	
	public Account getAccountById(int id) throws RetrieveAccountException {
		Account a = adi.getAccountById(id);
		if (a != null)
			return a;
		else
			throw new RetrieveAccountException("Failed to retrieve account " + id);
	}
	
	public ArrayList<Account> getAccountsByStatus(AccountStatus status) throws RetrieveAccountException {
		ArrayList<Account> list = adi.getAccountsByStatus(status);
		if (list != null)
			return list;
		else
			throw new RetrieveAccountException("Failed to retrieve accounts with status " + status.getStatusName());
	}
	
	public ArrayList<Account> getAccountsByType(AccountType type) throws RetrieveAccountException {
		ArrayList<Account> list = adi.getAccountsByType(type);
		if (list != null)
			return list;
		else
			throw new RetrieveAccountException("Failed to retrieve accounts of type " + type.getAccountType());
	}
	
	public ArrayList<Account> getAllAccounts() throws RetrieveAccountException {
		ArrayList<Account> list = adi.getAllAccounts();
		if (list != null)
			return list;
		else
			throw new RetrieveAccountException("Failed to retrieve all accounts.");
	}
	
	public boolean updateAccount(Account account) throws UpdateAccountException {
		if (adi.updateAccount(account))
			return true;
		else
			throw new UpdateAccountException("Failed to update account " + account.getAccountId());
	}
	
	public boolean addUserToAccount(Account account, User user) throws UpdateAccountException {
		if (adi.addUserToAccount(account, user))
			return true;
		else
			throw new UpdateAccountException("Failed to add " + user.getUsername() + " to account " + account.getAccountId());
	}
	
	public boolean deleteUserFromAccount(Account account, User user) throws UpdateAccountException {
		if (adi.deleteUserFromAccount(account, user))
			return true;
		else
			throw new UpdateAccountException("Failed to remove user " + user.getUsername() + " from account " + account.getAccountId());
	}
	
	public ArrayList<Account> getAccountsByUser(User user) throws RetrieveAccountException {
		ArrayList<Account> list = adi.getAccountsByUser(user);
		if (list != null)
			return list;
		else
			throw new RetrieveAccountException("Failed to retrieve accounts for user: " + user.getUsername());
	}
	
	public HashMap<User, ArrayList<Account>> getAllUserAccounts() throws RetrieveAccountException {
		HashMap<User, ArrayList<Account>> accts = adi.getAllUserAccounts();
		if (accts != null)
			return accts;
		else
			throw new RetrieveAccountException("Failed to retrieve all user-associated accounts.");
	}

	
	
	/*
	 * AccountType DAO methods.
	 */
	public int addAccountType(AccountType type) throws AddAccountException {
		int a = atdi.addAccountType(type);
		if (a != 0)
			return a;
		else
			throw new AddAccountException("Failed to add account type " + type.getAccountType());
	}
	
	public boolean deleteAccountTypeById(int id) throws UpdateAccountException {
		if (atdi.deleteAccountTypeById(id))
			return true;
		else
			throw new UpdateAccountException("Failed to delete account type id: " + id);
	}
	
	public AccountType getAccountTypeById(int id) throws RetrieveAccountException {
		AccountType type = atdi.getAccountTypeById(id);
		if (type != null)
			return type;
		else
			throw new RetrieveAccountException("Failed to retrieve account type id: " + id);
	}
	
	public ArrayList<AccountType> getAllAccountTypes() throws RetrieveAccountException {
		ArrayList<AccountType> list = atdi.getAllAccountTypes();
		if (list != null)
			return list;
		else
			throw new RetrieveAccountException("Failed to retrieve all account types.");
	}
	
	public boolean updateAccountType(AccountType type) throws UpdateAccountException {
		if (atdi.updateAccountType(type))
			return true;
		else
			throw new UpdateAccountException("Failed to update account type " + type.getAccountType());
	}
	
	public ArrayList<AccountType> getAccountTypesByPermission(User user) throws RetrieveAccountException {
		ArrayList<AccountType> list = atdi.getAccountTypesByPermission(user);
		if (list != null) 
			return list;
		else
			throw new RetrieveAccountException("Failed to retrive allowed account types for user: " + user.getUsername());
	}
	
	/*
	 * AcountStatus DAO methods.
	 */
	public AccountStatus getAccountStatusByStatus(String status) throws RetrieveAccountException {
		AccountStatus s = asdi.getAccountStatusByStatus(status);
		if (s != null)
			return s;
		else
			throw new RetrieveAccountException("Failed to retrieve status for: " + status);
	}
	
	public AccountStatus getDefaultStatus() throws RetrieveAccountException {
		return this.getAccountStatusByStatus("Open");
	}
}
