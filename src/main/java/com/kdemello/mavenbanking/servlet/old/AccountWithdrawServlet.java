package com.kdemello.mavenbanking.servlet.old;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kdemello.mavenbanking.exceptions.UpdateAccountException;
import com.kdemello.mavenbanking.model.Account;
import com.kdemello.mavenbanking.model.User;
import com.kdemello.mavenbanking.service.AccountService;
import com.kdemello.mavenbanking.servlet.ServletUtilities;

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