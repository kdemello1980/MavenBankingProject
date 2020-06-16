package com.revature.mavenbanking.servlet;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mavenbanking.exceptions.UpdateAccountException;
import com.revature.mavenbanking.model.Account;
import com.revature.mavenbanking.model.User;
import com.revature.mavenbanking.service.AccountService;

/**
 * Servlet implementation class AccountDeposit
 */
public class AccountWithdrawServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Check for a user in the session, and if it's null include the login.html page.
		User user = ServletUtilities.getUserFromSession(request, response);

		// Get amount and account from the request parameters.
		Account account = (Account) request.getSession().getAttribute("account");
		double amount = Double.valueOf(request.getParameter("withdraw_amount"));
		
		try {
			BigDecimal newBalance = new AccountService().withdraw(account, amount);
			request.setAttribute("account_number", account.getAccountId());
			request.getRequestDispatcher("/AccountDetailServlet").forward(request, response);
		} catch (UpdateAccountException e){
			e.printStackTrace();
			response.sendError(500, e.getMessage());
		}
	}

}