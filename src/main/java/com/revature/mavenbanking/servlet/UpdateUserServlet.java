package com.revature.mavenbanking.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mavenbanking.exceptions.RetrieveRoleException;
import com.revature.mavenbanking.exceptions.RetrieveUserException;
import com.revature.mavenbanking.exceptions.UpdateUserException;
import com.revature.mavenbanking.model.Role;
import com.revature.mavenbanking.model.User;
import com.revature.mavenbanking.service.RoleService;
import com.revature.mavenbanking.service.UserService;

/**
 * Servlet implementation class UpdateUserServlet
 */
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = ServletUtilities.getUserFromSession(request, response);
		int userId = Integer.valueOf(request.getParameter("user_to_modify"));
		UserService uService = new UserService();
		RoleService rService = new RoleService();
		
		// Fetch the user to update from the database.
		User updateUser = null;
		if (request.getParameter("user_to_modify") != null) {
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
		
		updateUser.setUsername(request.getParameter("username"));
		updateUser.setEmail(request.getParameter("email"));
		updateUser.setPassword(request.getParameter("password"));
		updateUser.setFirstName(request.getParameter("first_name"));
		updateUser.setLastName(request.getParameter("last_name"));
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
		RequestDispatcher dis = request.getRequestDispatcher("/MavenBankingProject/users");
		dis.forward(request, response);
	}

}
