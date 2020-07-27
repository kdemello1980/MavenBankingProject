package com.kdemello.mavenbanking.servlet.old;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kdemello.mavenbanking.exceptions.RetrieveRoleException;
import com.kdemello.mavenbanking.exceptions.RetrieveUserException;
import com.kdemello.mavenbanking.exceptions.UpdateUserException;
import com.kdemello.mavenbanking.model.Role;
import com.kdemello.mavenbanking.model.User;
import com.kdemello.mavenbanking.service.RoleService;
import com.kdemello.mavenbanking.service.UserService;
import com.kdemello.mavenbanking.servlet.ServletUtilities;

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
		
		try {
			uService.updateUser(updateUser);
		} catch (UpdateUserException e) {
			e.printStackTrace();
			response.sendError(500, e.getMessage());
			return;
		}
		RequestDispatcher dis = request.getRequestDispatcher("UserInfo.jsp");
		dis.forward(request, response);
	}

}
