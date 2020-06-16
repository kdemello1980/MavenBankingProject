package com.revature.mavenbanking.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mavenbanking.exceptions.RetrieveAccountException;
import com.revature.mavenbanking.model.Account;
import com.revature.mavenbanking.model.User;
import com.revature.mavenbanking.service.AccountService;

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
		int accountId;
		try {
			accountId = Integer.parseInt(request.getParameter("account_id"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			accountId = (int)request.getAttribute("account_number");
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
		
		out.println(ServletUtilities.openDocument("Account Details", "Details for " + acct.getAccountId()));
		
		
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
		out.println(
				ServletUtilities.tr(ServletUtilities.th("Account Status") + 
						ServletUtilities.td(acct.getStatus().getStatusName())
						)
				);
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
		out.println("<br><br>");
		// Deposit form.
		out.println(ServletUtilities.openForm("deposit_form", "/MavenBankingProject/AccountDepositServlet"));
		out.println(ServletUtilities.openTable("deposit_table"));
		out.println(
				ServletUtilities.tr(
						ServletUtilities.td(ServletUtilities.textInput("deposit_amount", "Enter Amount"))+
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
						ServletUtilities.td(ServletUtilities.textInput("withdraw_amount", "Enter Amount"))+
						ServletUtilities.td(ServletUtilities.submitButton("Withdraw"))
						)
				);
		out.println(ServletUtilities.closeTable());
		out.println(ServletUtilities.closeForm());
		out.println("<br>");

		// Transfer form.
		out.println(ServletUtilities.openForm("deposit_form", "/MavenBankingProject/AccountTransferServlet"));
		out.println(ServletUtilities.openTable("deposit_table"));
		out.println(
				ServletUtilities.tr(ServletUtilities.th("Amount") + ServletUtilities.th("Account Number")) +
				ServletUtilities.tr(
						ServletUtilities.td(ServletUtilities.textInput("transfer_amount", "Enter Amount"))+
						ServletUtilities.td(ServletUtilities.textInput("transfer_account", "Destination Account #")) +
						ServletUtilities.td(ServletUtilities.submitButton("Transfer"))
						)
				);
		out.println(ServletUtilities.closeTable());
		out.println(ServletUtilities.closeForm());
		
		out.println(ServletUtilities.closeDocument());
	}

}
