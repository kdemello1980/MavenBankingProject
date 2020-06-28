package com.kdemello.mavenbanking.servlet;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kdemello.mavenbanking.exceptions.AddAccountException;
import com.kdemello.mavenbanking.exceptions.RetrieveAccountException;
import com.kdemello.mavenbanking.exceptions.RetrieveUserException;
import com.kdemello.mavenbanking.exceptions.UpdateAccountException;
import com.kdemello.mavenbanking.model.Account;
import com.kdemello.mavenbanking.model.AccountStatus;
import com.kdemello.mavenbanking.model.AccountType;
import com.kdemello.mavenbanking.model.User;
import com.kdemello.mavenbanking.service.AccountService;
import com.kdemello.mavenbanking.service.UserService;


/**
 * Servlet implementation class AddAccountServlet
 */
public class AddAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = ServletUtilities.getUserFromSession(request, response);
		AccountService aService = new AccountService();
		UserService uService = new UserService();
		
		int typeId = Integer.valueOf(request.getParameter("type_select"));
		
		// Retrieve the account owner.
		User owner = null;
		try {
			owner = uService.getUserById(Integer.valueOf(request.getParameter("user_id")));
		} catch (RetrieveUserException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
		}
		
		
		// Retrieve the account type and status objects.
		AccountType type = null;
		AccountStatus status = null;
		try {
			type = aService.getAccountTypeById(typeId);
			status = aService.getDefaultStatus();
		} catch (RetrieveAccountException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
			return;
		}
		
		// Add the account and get the generated ID.
		int accountId;
		Account account = null;
		try {
			account = new Account();
			account.setStatus(status);
			account.setType(type);
			double initialDeposit = Double.valueOf(request.getParameter("initial_deposit"));
			account.setBalance(BigDecimal.valueOf(initialDeposit));
			accountId = aService.addAccount(account);
		} catch (AddAccountException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
			return;
		}
		account.setAccountId(accountId);
		
		// Add the user to the newly generated account.
		try {
			aService.addUserToAccount(account, owner);		
		} catch (UpdateAccountException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
			return;
		}
		
		// If we've made it to this point, go back to the accounts page.
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/accounts");
//		dispatcher.forward(request, response);
		response.sendRedirect("/MavenBankingProject/JSONAccountServlet");
	}

}
