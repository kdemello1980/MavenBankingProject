package com.revature.mavenbanking.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mavenbanking.exceptions.RetrieveAccountException;
import com.revature.mavenbanking.model.Account;
import com.revature.mavenbanking.model.User;
import com.revature.mavenbanking.service.AccountService;

public class AccountServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4978087815778481379L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		User user = (User)req.getSession().getAttribute("user");
		PrintWriter out = res.getWriter();
		ArrayList<Account> accounts = null;

		if (user == null){
			res.setStatus(401);
			res.setHeader("message", "The incoming token has expired.");
			req.getRequestDispatcher("login.html").include(req, res);
		}
		
		String title = "MavenBank Home";
		out.println("<!doctype html><html lang=\"en\"><head><title>" + title + "</title></head><body>");
		out.println("<h2>Welcome " + user.getFirstName() + " " + user.getLastName() +"</h2>");
		out.println("<form id=\"accounts_form\"><table>");
		
		if (user.getRole().getEffectivePermissions().contains("ea_can_view_all_customer_info")){
			try {
				accounts = new AccountService().getAllAccounts();

			} catch (RetrieveAccountException e) {
				e.printStackTrace();
				res.setStatus(501);
				res.setHeader("message", "Error retrieving accounts for all users.");
				req.getRequestDispatcher("error.html").forward(req, res);
			}
		} else {
			try {
				accounts = new AccountService().getAccountsByUser(user);
			} catch (RetrieveAccountException e){
				e.printStackTrace();
				res.setStatus(501);
				res.setHeader("message", "Error retrieving accounts for " + user.getFirstName() + " " + user.getLastName());
				req.getRequestDispatcher("error.html").forward(req, res);
			}
		}
		for (Account account : accounts){
			out.println("<tr>");
			// Link to account detail page.
			out.println("<td>");
			out.println("<a href=\"MavenBankingProject/accounts/" + account.getAccountId() + "\">" + 
			account.getAccountId()+ "</a>");
			out.println("</td>");
			
			// Account type. 
			out.println("<td>");
			out.println(account.getType().getType());
			out.println("</td>");
			
			// Account status.
			out.println("<td>");
			out.println(account.getStatus().getStatusName());
			out.println("</td>");
			
			// First Name + Last Name
			out.println("<td>");
			out.println(user.getFirstName() + " " + user.getLastName());
			out.println("</td>");
			
			// Balance.
			out.println("<td>");
//			out.println();
			out.println("</td>");
			out.println("</tr>");
		}
		out.println("</table></form></body></html>");

	}

}
