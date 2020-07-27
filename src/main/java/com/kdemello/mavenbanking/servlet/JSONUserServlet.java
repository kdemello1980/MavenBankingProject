package com.kdemello.mavenbanking.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kdemello.mavenbanking.exceptions.RetrieveRoleException;
import com.kdemello.mavenbanking.exceptions.RetrieveUserException;
import com.kdemello.mavenbanking.exceptions.UpdateUserException;
import com.kdemello.mavenbanking.model.Role;
import com.kdemello.mavenbanking.model.User;
import com.kdemello.mavenbanking.service.AccountService;
import com.kdemello.mavenbanking.service.PermissionService;
import com.kdemello.mavenbanking.service.RoleService;
import com.kdemello.mavenbanking.service.UserService;

/**
 * Servlet implementation class JSONUserServlet
 */
public class JSONUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountService aService = new AccountService();
	private PermissionService pService = new PermissionService();
	private UserService uService = new UserService();
	private RoleService rService = new RoleService();
	Gson gson = new Gson().newBuilder().enableComplexMapKeySerialization().create();
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public JSONUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		User loggedIn = ServletUtilities.getUserFromSession(request, response);
		
		
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = null;
		try {
			user = uService.getUserById(userId);
		} catch (RetrieveUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendError(500,e.getMessage());
		}
		String userString = gson.toJson(user);
//		response.getWriter().println(userString);
		request.setAttribute("user", userString);
		boolean isAdmin = ServletUtilities.hasPermission(loggedIn, "a_can_modify_all_customer_info", response);
		boolean isStandard = ServletUtilities.hasPermission(loggedIn, "sp_account_types_standard", response);
		boolean readOnly =  ServletUtilities.hasPermission(loggedIn, "ea_can_view_all_customer_info", response);
		request.setAttribute("is_admin", isAdmin);
		request.setAttribute("is_standard", isStandard);
		request.setAttribute("read_only", readOnly);
		
	
		// Get list of roles to send to the jsp.

		request.getRequestDispatcher("UserInfo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = ServletUtilities.getUserFromSession(request, response);
		int userId = Integer.valueOf(request.getParameter("id"));
		UserService uService = new UserService();
		RoleService rService = new RoleService();
		
		// Fetch the user to update from the database.
		User updateUser = null;
		if (request.getParameter("id") != null) {
			try {
				updateUser = uService.getUserById(userId);
			} catch (RetrieveUserException e) {
				e.printStackTrace();
				response.sendError(500, e.getMessage());
				return;
			}
		} else {
			updateUser = user;
		}
		
		// Get the role to update if it exists.
		Role updateRole = null;
		try {
			if (request.getParameter("user_role") != null){
				updateRole = rService.getRoleById(Integer.valueOf(request.getParameter("user_role")));
			}
		} catch (RetrieveRoleException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
			return;
		}
		
		if (request.getParameter("user_name_input") != null)
			updateUser.setUsername(request.getParameter("user_name_input"));
		if (request.getParameter("email") != null)
			updateUser.setEmail(request.getParameter("email"));
		if (request.getParameter("password") != null)
			updateUser.setPassword(request.getParameter("password"));
		if (request.getParameter("first_name") != null)
			updateUser.setFirstName(request.getParameter("first_name"));
		if (request.getParameter("last_name") != null)
			updateUser.setLastName(request.getParameter("last_name"));
		if (updateRole != null) {
			updateUser.setRole(updateRole);
		}
		
		if (updateRole != null) {
			updateUser.setRole(updateRole);
		}
		
		try {
			uService.updateUser(updateUser);
		} catch (UpdateUserException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
			return;
		}
//		RequestDispatcher dis = request.getRequestDispatcher("JSONUserServlet&id=" + updateUser.getUserId());
//		dis.forward(request, response);
		response.sendRedirect("/MavenBankingProject/JSONUserServlet?id=" + updateUser.getUserId());

	}
	

}
