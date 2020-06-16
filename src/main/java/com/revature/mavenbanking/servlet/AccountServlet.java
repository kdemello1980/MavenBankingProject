package com.revature.mavenbanking.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mavenbanking.exceptions.RetrieveAccountException;
import com.revature.mavenbanking.exceptions.RetrievePermissionException;
import com.revature.mavenbanking.model.Account;
import com.revature.mavenbanking.model.AccountType;
import com.revature.mavenbanking.model.Permission;
import com.revature.mavenbanking.model.User;
import com.revature.mavenbanking.service.AccountService;
import com.revature.mavenbanking.service.PermissionService;
import com.revature.mavenbanking.servlet.ServletUtilities;

public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = -4978087815778481379L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		User user = ServletUtilities.getUserFromSession(req, res);
		
		// Should have been redirected to login page if null already.
		if (user != null) {
			doPost(req, res);
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		HashMap<User, ArrayList<Account>> accts = null;
		Permission adminPermission = null;
		Permission premiumPermission = null;
		boolean isAdmin = false;
		boolean isPremium = false;
		User user = ServletUtilities.getUserFromSession(req, res);
		ArrayList<AccountType> types = null;
		PermissionService pService = new PermissionService();
		AccountService aService = new AccountService();

		// Check to see if the user is an admin.
		try {
			adminPermission = pService.getPermissionByPermissionName("ea_can_view_all_customer_info");
			for (Permission p : user.getRole().getEffectivePermissions()){
				if (p.getPermissionName().equals(adminPermission.getPermissionName())) {
					isAdmin = true;
					break;
				}
			}
		} catch (RetrievePermissionException e) {
			e.printStackTrace();
			res.sendError(500, e.getMessage());
		}
//		System.out.println(isAdmin);
		
		// Check to see if the user is premium.
		try {
			premiumPermission = pService.getPermissionByPermissionName("p_account_types_premium");
			for (Permission p : user.getRole().getEffectivePermissions()) {
				if (p.getPermissionName().equals(premiumPermission.getPermissionName())) {
					isPremium = true;
					break;
				}
			}
		} catch (RetrievePermissionException e) {
			e.printStackTrace();
			res.sendError(500, e.getMessage());
		}
		
		
		// Get user account info from the database.
		try {
			if (isAdmin){
				accts = aService.getAllUserAccounts();
			} else {
				accts = new HashMap<User, ArrayList<Account>>();
				accts.put(user, aService.getAccountsByUser(user));
			}
		} catch (RetrieveAccountException e){
			e.printStackTrace();
			res.sendError(500, e.getMessage());
			return;
		}

		// Print the page.
		out.println(ServletUtilities.openDocument("MavenBank Accounts.  Welcome ", 
				ServletUtilities.anchor("/MavenBankingProject/users", user.getFirstName() + " " + user.getLastName()) + 
				" (" + user.getRole().getRole() + ")"));
		out.println(ServletUtilities.openTable("accounts"));
		out.println(ServletUtilities.openForm("account_detail", "/MavenBankingProject/AccountDetailServlet"));
		out.println(ServletUtilities.tr(ServletUtilities.th("Account Number") + 
				ServletUtilities.th("Type") + 
				ServletUtilities.th("Account Owner") +
				ServletUtilities.th("Account Status") +
				ServletUtilities.th("Balance")));
		
		for (User u : accts.keySet()){
			for (Account a : accts.get(u)){
				out.println(accountRow(a, u));
			}
		}
		out.println(ServletUtilities.closeTable() + ServletUtilities.closeDocument());
		out.println(ServletUtilities.submitButton("Detail"));
		out.println(ServletUtilities.closeForm());
		out.println("<br>");
		
		// Add account form.
		out.println("<h2>Add New Account</h2>");
		String accountFormString = ServletUtilities.openForm("new_account", "/MavenBankingProject/AddAccountServlet") +
				ServletUtilities.openTable("new_account_table") + ServletUtilities.tr(
						ServletUtilities.th("Account Type") + ServletUtilities.th("Owner"));
		
		// Get the list of valid account types for the user's permission level
		ArrayList<AccountType> validTypes = null;
		try {
			validTypes = aService.getAccountTypesByPermission(user);
		} catch (RetrieveAccountException e) {
			e.printStackTrace();
			res.sendError(500, e.getMessage());
			return;
		}
		System.out.println(validTypes);
		
		// Populate the select list of account types.
		String tSelect = ServletUtilities.openSelect("type_select", "type_select");
		for (AccountType t : validTypes) {
			tSelect += ServletUtilities.selectOption(Integer.toString(t.getTypeId()), t.getType());
		}
		tSelect += ServletUtilities.closeSelect();
//		accountFormString += ServletUtilities.td(tSelect);
		
		String uSelect = ServletUtilities.openSelect("user_select", "user_select");
		for (User u : accts.keySet()) {
			uSelect += ServletUtilities.selectOption(Integer.toString(u.getUserId()), u.getLastName() + ", " + u.getFirstName());
		}
		uSelect += ServletUtilities.closeSelect();
//		accountFormString += ServletUtilities.td(uSelect);
		accountFormString += ServletUtilities.tr(ServletUtilities.td(tSelect) + ServletUtilities.td(uSelect));
		accountFormString += ServletUtilities.closeTable();
		accountFormString += ServletUtilities.submitButton("Create Account");
		accountFormString += ServletUtilities.closeForm();
		out.println(accountFormString);
		out.print(ServletUtilities.closeDocument());
	}
	
	private static String accountRow(Account a, User u){
		
		
		String accountId = Integer.toString(a.getAccountId());
		String content = ServletUtilities.tr(
				ServletUtilities.th(ServletUtilities.radio("account_id", accountId, accountId, accountId)) +
				ServletUtilities.td(a.getType().getAccountType()) +
				ServletUtilities.td(u.getLastName() + ", " + u.getFirstName()) +
				ServletUtilities.td(a.getStatus().getStatusName()) +
				ServletUtilities.td(a.getBalance().toString())
				);
		return content;
	}

}
