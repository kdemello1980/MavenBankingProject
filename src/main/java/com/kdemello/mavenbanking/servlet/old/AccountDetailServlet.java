package com.kdemello.mavenbanking.servlet.old;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kdemello.mavenbanking.exceptions.RetrieveAccountException;
import com.kdemello.mavenbanking.exceptions.RetrieveUserException;
import com.kdemello.mavenbanking.model.Account;
import com.kdemello.mavenbanking.model.AccountStatus;
import com.kdemello.mavenbanking.model.User;
import com.kdemello.mavenbanking.service.AccountService;
import com.kdemello.mavenbanking.service.UserService;
import com.kdemello.mavenbanking.servlet.ServletUtilities;

/**
 * Servlet implementation class AccountDetail
 */
public class AccountDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get and verify a user session exists.
		User user = ServletUtilities.getUserFromSession(request, response);
		
		PrintWriter out = response.getWriter();		
		int accountId  = -1;
		try {
			accountId = Integer.parseInt(request.getParameter("account_id"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			try {
				accountId = (int)request.getAttribute("account_number");
			} catch (NullPointerException np) {
				RequestDispatcher dis = request.getRequestDispatcher("/accounts");
				dis.forward(request, response);
				return;
			}
		}
		String accountString = Integer.toString(accountId);
		AccountService service = new AccountService();
		Account acct = null;
		
		// Retrieve the account details from the DB
		try{
			acct = service.getAccountById(accountId);
			request.getSession().setAttribute("account", acct);
		} catch (RetrieveAccountException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
		}
		
		out.println(ServletUtilities.openDocument("Account Details", "Details for Account Number: " + acct.getAccountId()));
		
		if (ServletUtilities.hasPermission(user, "ea_can_modify_account", response)) {
			out.println(ServletUtilities.openForm("update_account", "/MavenBankingProject/UpdateAccountServlet"));
		}
		
		// Detail table.
		out.println(ServletUtilities.openTable("detail_table"));
		out.println(
				ServletUtilities.tr(ServletUtilities.th("Account Number") + 
						ServletUtilities.td(accountString)
						)
				);
		out.println(
				ServletUtilities.tr(ServletUtilities.th("Account Type") + 
						ServletUtilities.td(acct.getType().getAccountType())
						)
				);
		if (ServletUtilities.hasPermission(user, "ea_can_modify_account", response)) {
			ArrayList<AccountStatus> status = null;
			try {
				status = service.getAllStatus();
			} catch (RetrieveAccountException e) {
				e.printStackTrace();
				response.sendError(500, e.getMessage());
				return;
			}
			String statusSelect = new String();
			statusSelect += ServletUtilities.openSelect("account_status", "account_status");
			for (AccountStatus s : status) {
				statusSelect += ServletUtilities.selectOption(String.valueOf(s.getStatusId()), s.getStatusName());
			}
			statusSelect += ServletUtilities.closeSelect();
			out.println(ServletUtilities.tr(ServletUtilities.th("Status") + ServletUtilities.td(statusSelect)));
		} else {
			out.println(
					ServletUtilities.tr(ServletUtilities.th("Account Status") + 
							ServletUtilities.td(acct.getStatus().getStatusName())
							)
					);
		}
		out.println(
				ServletUtilities.tr(ServletUtilities.th("Interest Rate") + 
						ServletUtilities.td(acct.getType().getInterestRate().toString())
						)
				);
		out.println(
				ServletUtilities.tr(ServletUtilities.th("Monthly Fee") + 
						ServletUtilities.td(acct.getType().getMonthlyFee().toString())
						)
				);
	
		out.println(
				ServletUtilities.tr(ServletUtilities.th("Account Balance") + 
						ServletUtilities.td(acct.getBalance().toString())
						)
				);
		out.println(ServletUtilities.closeTable());
		if (ServletUtilities.hasPermission(user, "ea_can_modify_account", response)) {
			out.println(ServletUtilities.submitButton("Update"));
			out.println(ServletUtilities.closeForm());
		}
		
		
		
		
		
		
		out.println("<br><br>");
		
		// Deposit form.
		out.println("<h2>Deposit &amp; Withdraw</h2>");
		out.println(ServletUtilities.openForm("deposit_form", "/MavenBankingProject/AccountDepositServlet"));
		out.println(ServletUtilities.openTable("deposit_table"));

		out.println(
				ServletUtilities.tr(
						ServletUtilities.td(ServletUtilities.dollarInput("deposit_amount", "0.00"))+
						ServletUtilities.td(ServletUtilities.submitButton("Deposit"))
						)
				);
		out.println(ServletUtilities.closeTable());
		out.println(ServletUtilities.closeForm());

		// Withdraw form.
		out.println(ServletUtilities.openForm("withdraw_form", "/MavenBankingProject/AccountWithdrawServlet"));
		out.println(ServletUtilities.openTable("withdraw_form"));
		out.println(
				ServletUtilities.tr(
						ServletUtilities.td(ServletUtilities.dollarInput("withdraw_amount", "0.00"))+
						ServletUtilities.td(ServletUtilities.submitButton("Withdraw"))
						)
				);
		out.println(ServletUtilities.closeTable());
		out.println(ServletUtilities.closeForm());
		out.println("<br>");

		// Transfer form.
		out.println("<h2>Transfer Funds</h2>");
		out.println(ServletUtilities.openForm("deposit_form", "/MavenBankingProject/AccountTransferServlet"));
		out.println(ServletUtilities.openTable("deposit_table"));
		out.println(
				ServletUtilities.tr(ServletUtilities.th("Amount") + ServletUtilities.th("Account Number")) +
				ServletUtilities.tr(
						ServletUtilities.td(ServletUtilities.dollarInput("transfer_amount", "0.00"))+
						ServletUtilities.td(ServletUtilities.numberInput("transfer_account", "01234")) +
						ServletUtilities.td(ServletUtilities.submitButton("Transfer"))
						)
				);
		out.println(ServletUtilities.closeTable());
		out.println(ServletUtilities.closeForm());
		

		
		// Add user to account.
		if (ServletUtilities.hasPermission(user, "p_can_add_user_to_account", response)) {
			ArrayList<User> allUsers = null;
			try {
				allUsers = new UserService().getAllUsers();
			} catch (RetrieveUserException e) {
				e.printStackTrace();
				response.sendError(500, e.getMessage());
				return;
			}
			out.println("<h2>Add Joint Account Owner</h2>");
			
			String userSelect = ServletUtilities.openSelect("user_select", "user_select");
			for (User u : allUsers) {
				userSelect += ServletUtilities.selectOption(Integer.toString(u.getUserId()), u.getLastName() + ", " + u.getFirstName());
			}
			userSelect += ServletUtilities.closeSelect();
			
			String addUserForm = ServletUtilities.openForm("add_user_form", "/MavenBankingProject/AddUserToAccountServlet");
			addUserForm += ServletUtilities.openTable("add_user_table");
			addUserForm += ServletUtilities.tr(ServletUtilities.th("User: ") + ServletUtilities.td(userSelect));
			addUserForm += ServletUtilities.closeTable();
			addUserForm += ServletUtilities.submitButton("Add User");
			addUserForm += ServletUtilities.closeForm();
			out.println(addUserForm);
		}
		
		// Pass time button.
		if (ServletUtilities.hasPermission(user, "a_account_pass_time", response)) {
			out.println("<br><h2>Pass Time</h2>");
			String passTimeForm = ServletUtilities.openForm("pass_time_form", "/MavenBankingProject/PassTimeServlet");
			passTimeForm += ServletUtilities.openTable("pass_time_table");
			passTimeForm += ServletUtilities.tr(ServletUtilities.th("Time (months)") + ServletUtilities.td(ServletUtilities.numberInput("time", "0")) + 
			ServletUtilities.td(ServletUtilities.submitButton("Go")));
			passTimeForm += ServletUtilities.closeTable();
			passTimeForm += ServletUtilities.closeForm();
			out.println(passTimeForm);
		}
		out.println(ServletUtilities.closeDocument());
	}

}
