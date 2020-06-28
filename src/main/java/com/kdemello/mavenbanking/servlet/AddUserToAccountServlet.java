package com.kdemello.mavenbanking.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kdemello.mavenbanking.exceptions.RetrieveUserException;
import com.kdemello.mavenbanking.exceptions.UpdateAccountException;
import com.kdemello.mavenbanking.model.Account;
import com.kdemello.mavenbanking.model.User;
import com.kdemello.mavenbanking.service.AccountService;
import com.kdemello.mavenbanking.service.UserService;

/**
 * Servlet implementation class AddUserToAccountServlet
 */
public class AddUserToAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = ServletUtilities.getUserFromSession(request, response);
		AccountService aService = new AccountService();
		UserService uService = new UserService();
		
		User user1 = null;
		try {
			user1 = uService.getUserById(Integer.valueOf(request.getParameter("user_select")));
		} catch (RetrieveUserException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
			return;
		}
		
		Account account = (Account) request.getSession().getAttribute("account");

		// Add the user to the account.
		try {
			aService.addUserToAccount(account, user1);
			RequestDispatcher dis = request.getRequestDispatcher("/accounts");
			dis.forward(request, response);
		} catch (UpdateAccountException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
			return;
		}
	}

}
