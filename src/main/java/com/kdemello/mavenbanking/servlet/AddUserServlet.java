package com.kdemello.mavenbanking.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kdemello.mavenbanking.dao.impl.RoleDaoImpl;
import com.kdemello.mavenbanking.exceptions.AddUserException;
import com.kdemello.mavenbanking.exceptions.RetrieveRoleException;
import com.kdemello.mavenbanking.exceptions.UpdateUserException;
import com.kdemello.mavenbanking.model.Role;
import com.kdemello.mavenbanking.model.User;
import com.kdemello.mavenbanking.service.RoleService;
import com.kdemello.mavenbanking.service.UserService;

/**
 * Servlet implementation class AddAccount
 */
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println(ServletUtilities.openDocument("Register", "Register"));
		out.println(ServletUtilities.openForm("new_user", "/MavenBankingProject/AddUserServlet"));
		out.println(ServletUtilities.openTable("user_table"));
		out.println(ServletUtilities.tr(ServletUtilities.th("Username") + ServletUtilities.td(ServletUtilities.textInput("user_name", ""))));
		out.println(ServletUtilities.tr(ServletUtilities.th("Email") + ServletUtilities.td(ServletUtilities.textInput("email", ""))));
		out.println(ServletUtilities.tr(ServletUtilities.th("First Name") + ServletUtilities.td(ServletUtilities.textInput("first_name", ""))));
		out.println(ServletUtilities.tr(ServletUtilities.th("Last Name") + ServletUtilities.td(ServletUtilities.textInput("last_name", ""))));
		out.println(ServletUtilities.tr(ServletUtilities.th("Password") + ServletUtilities.td(ServletUtilities.passwordInput("password", ""))));
		out.print(ServletUtilities.closeTable());
		out.println(ServletUtilities.submitButton("Register"));
		
		out.println(ServletUtilities.closeForm() + ServletUtilities.closeDocument());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User newUser = new User();
		UserService uService = new UserService();
		RoleService rService = new RoleService();
		newUser.setEmail(request.getParameter("email"));
		newUser.setUsername(request.getParameter("user_name"));
		newUser.setFirstName(request.getParameter("first_name"));
		newUser.setLastName(request.getParameter("last_name"));
		newUser.setPassword(request.getParameter("password"));
		
		Role initialRole = null;
		try {
			initialRole = rService.getRoleByName("Standard");
		} catch (RetrieveRoleException e) {
			e.printStackTrace();
			response.sendError(500, "Unable to retrieve default role.");
		}
		
		newUser.setRole(initialRole);
		
		try {
			uService.addUser(newUser);
		} catch (AddUserException e) {
			e.printStackTrace();
			response.sendError(500, "Failed to add new user");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/accounts");
		dispatcher.forward(request, response);
	}
}
