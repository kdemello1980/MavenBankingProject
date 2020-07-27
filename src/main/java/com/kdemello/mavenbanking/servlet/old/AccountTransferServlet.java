package com.kdemello.mavenbanking.servlet.old;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kdemello.mavenbanking.exceptions.RetrieveAccountException;
import com.kdemello.mavenbanking.exceptions.UpdateAccountException;
import com.kdemello.mavenbanking.model.Account;
import com.kdemello.mavenbanking.model.User;
import com.kdemello.mavenbanking.service.AccountService;
import com.kdemello.mavenbanking.servlet.ServletUtilities;

/**
 * Servlet implementation class AccountDeposit
 */
public class AccountTransferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountService service = new AccountService();
		
		// Check for a user in the session, and if it's null include the login.html page.
		User user = ServletUtilities.getUserFromSession(request, response);

		// Get amount and account from the request parameters.
		Account sourceAccount = (Account) request.getSession().getAttribute("account");
		double amount = Double.valueOf(request.getParameter("transfer_amount"));
		Account transferAccount = null;
		
		try {
			transferAccount = service.getAccountById(Integer.parseInt(request.getParameter("transfer_account")));
		} catch (RetrieveAccountException e){
			e.printStackTrace();
			response.sendError(400, e.getMessage());
			return;
		}
		
		try {
			if (service.transfer(sourceAccount, transferAccount, amount)){
				request.setAttribute("account_number", sourceAccount.getAccountId());
				request.getRequestDispatcher("/AccountDetailServlet").forward(request, response);
			} else {
				throw new UpdateAccountException("Failed to transfer funds.");
			}
		} catch (UpdateAccountException e){
			e.printStackTrace();
			response.sendError(500, e.getMessage());
			return;
		}
	}

}