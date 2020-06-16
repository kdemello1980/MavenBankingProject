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
import com.revature.mavenbanking.model.Permission;
import com.revature.mavenbanking.model.User;
import com.revature.mavenbanking.service.AccountService;
import com.revature.mavenbanking.service.PermissionService;
import com.revature.mavenbanking.servlet.ServletUtilities;

public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = -4978087815778481379L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		User user = (User)req.getSession().getAttribute("user");
		PrintWriter out = res.getWriter();
		HashMap<User, ArrayList<Account>> accts = null;
		Permission adminPermission = null;
		boolean isAdmin = false;

		if (user == null){
			res.setStatus(401);
			res.setHeader("message", "The incoming token has expired.");
			req.getRequestDispatcher("login.html").include(req, res);
		}
		System.out.println(user);
		
		try {
			adminPermission = new PermissionService().getPermissionByPermissionName("ea_can_view_all_customer_info");
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
		System.out.println(isAdmin);
		// Get user account info from the database.
		try {
			if (isAdmin){
				accts = new AccountService().getAllUserAccounts();
			} else {
				accts = new HashMap<User, ArrayList<Account>>();
				accts.put(user, new AccountService().getAccountsByUser(user));
			}
		} catch (RetrieveAccountException e){
			e.printStackTrace();
			res.sendError(500, e.getMessage());
		}

		// Print the page.
		out.println(ServletUtilities.openDocument("MavenBank Accounts.  Welcome ", user.getFirstName() + " " + user.getLastName()));
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
