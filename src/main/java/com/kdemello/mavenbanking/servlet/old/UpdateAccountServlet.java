package com.kdemello.mavenbanking.servlet.old;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kdemello.mavenbanking.exceptions.RetrieveAccountException;
import com.kdemello.mavenbanking.exceptions.UpdateAccountException;
import com.kdemello.mavenbanking.model.Account;
import com.kdemello.mavenbanking.model.AccountStatus;
import com.kdemello.mavenbanking.model.User;
import com.kdemello.mavenbanking.service.AccountService;
import com.kdemello.mavenbanking.servlet.ServletUtilities;

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
