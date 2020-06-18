package com.revature.mavenbanking.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mavenbanking.exceptions.UpdateAccountException;
import com.revature.mavenbanking.model.Account;
import com.revature.mavenbanking.model.User;
import com.revature.mavenbanking.service.AccountService;

/**
 * Servlet implementation class AddUserToAccountServlet
 */
public class PassTimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountService aService = new AccountService();
		User user = ServletUtilities.getUserFromSession(request, response);

		int time = Integer.valueOf(request.getParameter("time"));
		
		Account account = (Account) request.getSession().getAttribute("account");
		System.out.println(account);

		// Add the user to the account.
		try {
			aService.calculateInterest(account, time);
			request.setAttribute("account_number", account.getAccountId());
			RequestDispatcher dis = request.getRequestDispatcher("/AccountDetailServlet");
			dis.forward(request, response);
		} catch (UpdateAccountException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
		}
	}

}
