package com.kdemello.mavenbanking.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.kdemello.mavenbanking.exceptions.RetrieveAccountException;
import com.kdemello.mavenbanking.exceptions.RetrievePermissionException;
import com.kdemello.mavenbanking.exceptions.RetrieveUserException;
import com.kdemello.mavenbanking.exceptions.UpdateAccountException;
import com.kdemello.mavenbanking.model.Account;
import com.kdemello.mavenbanking.model.AccountType;
import com.kdemello.mavenbanking.model.Permission;
import com.kdemello.mavenbanking.model.User;
import com.kdemello.mavenbanking.service.AccountService;
import com.kdemello.mavenbanking.service.PermissionService;
import com.kdemello.mavenbanking.service.UserService; 


/**
 * Servlet implementation class JSONAccountServlet
 */
public class JSONAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountService aService = new AccountService();
	private PermissionService pService = new PermissionService();
	private UserService uService = new UserService();

	Gson gson = new Gson().newBuilder().enableComplexMapKeySerialization().create();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int accountId = -1;

		if (request.getParameter("id") != null)
			accountId = Integer.parseInt(request.getParameter("id"));
		
		System.out.println(accountId);
		PrintWriter out = response.getWriter();
		Permission adminPermission = null;
		Permission addUserPermission = null;
		boolean isAdmin = false;
		boolean canAddUser = false;
		User user = ServletUtilities.getUserFromSession(request, response);
		ArrayList<AccountType> types = null;
		aService = new AccountService();
		HashMap<User, ArrayList<Account>> accts = null;
		
		// Get list of account types for the user.
		try {
			types = aService.getAccountTypesByPermission(user);
		} catch (RetrieveAccountException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
			return;
		}
		
		Account account = null;
		// Check to see if the user is an admin.
		isAdmin = uService.hasPermission(user, "ea_can_view_all_customer_info");
		canAddUser = uService.hasPermission(user, "p_can_add_user_to_account");
				
		
		if (accountId > 0) {
			try {
				account = aService.getAccountById(accountId);
			} catch (RetrieveAccountException e) {
				e.printStackTrace();
				response.sendRedirect("/JSONAccountServlet");
			}
			
	
			
			System.out.println(isAdmin);
			if (isAdmin || canAddUser) {

				ArrayList<User> users = null;
				try {
					users = uService.getAllUsers();
				} catch (RetrieveUserException e){
					e.printStackTrace();
					response.sendRedirect("/JSONAccountServlet");
				}
				String userString = gson.toJson(users);
				request.setAttribute("users", userString);
			}
			
			String accountString = gson.toJson(account);
			RequestDispatcher req = request.getRequestDispatcher("AccountDetail.jsp");

			request.setAttribute("account", accountString);
			req.forward(request, response);
		} else {
	
//		out.println(user.getEmail());
	
			if (request.getParameter("account_id") != null) {
				response.setStatus(400);
				response.setHeader("message", "The incoming token has expired.");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				try {
					if (isAdmin) {
	
						accts = aService.getAllUserAccounts();
					} else {
						accts = new HashMap<User, ArrayList<Account>>();
						accts.put(user, aService.getAccountsByUser(user));
					}
					
					
	//				String accountString = objectMapper.writeValueAsString(allAccounts);
					String accountString = gson.toJson(accts);
					String typeString = gson.toJson(types);
					System.out.println(typeString);
					request.setAttribute("account_types", typeString);
					
					response.setContentType("application/json");
					out.print(accountString);
					RequestDispatcher req = request.getRequestDispatcher("Accounts.jsp");
					request.setAttribute("accounts", accountString);
					req.forward(request, response);
				} catch (RetrieveAccountException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int accountId = -1;
		if (request.getParameter("id") != null)
			accountId = Integer.parseInt(request.getParameter("id"));
		User user = ServletUtilities.getUserFromSession(request, response);
		
		Account account = null;
		try {
			account = aService.getAccountById(accountId);
		} catch (RetrieveAccountException e) {
			e.printStackTrace();
			response.sendRedirect("/MavenBankingProject/JSONAccountServlet?id=" + accountId);
		}

		try {
			if (request.getParameter("form_name").equals("deposit_form")) {
				aService.deposit(account, Double.valueOf(request.getParameter("deposit_amount")));
			} else if (request.getParameter("form_name").equals("withdraw_form")) {
				aService.withdraw(account, Double.valueOf(request.getParameter("withdraw_amount")));
			} else if (request.getParameter("form_name").equals("transfer_form")) {
				
				Account destination = null;
				try {
					destination = aService.getAccountById(Integer.valueOf(request.getParameter("transfer_account")));
				} catch (RetrieveAccountException e){
					e.printStackTrace();
				}
				aService.transfer(account, destination, Double.valueOf(request.getParameter("transfer_amount")));

			} 
		} catch (UpdateAccountException e) {
			e.printStackTrace();
		} finally {
			try {
				account = aService.getAccountById(accountId);
			} catch (RetrieveAccountException e) {
				e.printStackTrace();
			}
		}
		response.sendRedirect("/MavenBankingProject/JSONAccountServlet?id=" + account.getAccountId());
	}

		
	

}
