package com.revature.mavenbanking.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mavenbanking.exceptions.RetrieveAccountException;
import com.revature.mavenbanking.exceptions.UpdateAccountException;
import com.revature.mavenbanking.model.Account;
import com.revature.mavenbanking.model.AccountStatus;
import com.revature.mavenbanking.model.User;
import com.revature.mavenbanking.service.AccountService;

/**
 * Servlet implementation class AddUserToAccountServlet
 */
public class UpdateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountService aService = new AccountService();
		User user = ServletUtilities.getUserFromSession(request, response);

		int account_status = Integer.valueOf(request.getParameter("account_status"));
		AccountStatus newStatus = null;
		
		try {
			newStatus = aService.getAccountStatusById(account_status);
		} catch (RetrieveAccountException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
			return;
		}
		
		Account account = (Account) request.getSession().getAttribute("account");
//		System.out.println(account);
		account.setStatus(newStatus);

		// Add the user to the account.
		try {
			aService.updateAccount(account);
			request.setAttribute("account_number", account.getAccountId());
			RequestDispatcher dis = request.getRequestDispatcher("/AccountDetailServlet");
			dis.forward(request, response);
		} catch (UpdateAccountException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
		}
	}

}
