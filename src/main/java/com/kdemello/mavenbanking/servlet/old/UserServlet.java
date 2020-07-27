package com.kdemello.mavenbanking.servlet.old;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kdemello.mavenbanking.exceptions.RetrieveRoleException;
import com.kdemello.mavenbanking.exceptions.RetrieveUserException;
import com.kdemello.mavenbanking.model.Role;
import com.kdemello.mavenbanking.model.User;
import com.kdemello.mavenbanking.service.RoleService;
import com.kdemello.mavenbanking.service.UserService;
import com.kdemello.mavenbanking.servlet.ServletUtilities;


public class UserServlet extends HttpServlet {

	/**
	 * Automatically generated serialVersionUID.
	 */
	private static final long serialVersionUID = -1303561568359443221L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserService uService = new UserService();
		RoleService rService = new RoleService();
		PrintWriter out = response.getWriter();
		
		User user = ServletUtilities.getUserFromSession(request, response);
		boolean canModify = ServletUtilities.hasPermission(user, "e_can_modify_all_customer_info", response);
		boolean canView = ServletUtilities.hasPermission(user, "ea_can_view_all_customer_info", response);
		boolean canUpgrade = ServletUtilities.hasPermission(user, "s_can_upgrade_status_to_premium", response);
		if (request.getParameter("user_to_modify") != null) {
			try {
				user =  uService.getUserById(Integer.valueOf(request.getParameter("user_to_modify")));
			} catch (RetrieveUserException e) {
				e.printStackTrace();
				response.sendError(500, e.getMessage());
				return;
			}
		}
				
		// Begin the document.
		out.println(ServletUtilities.openDocument("Update User Information", "User Details for: " + user.getFirstName() + " " + user.getLastName()));	
		// Modify self form.
		out.println(ServletUtilities.openForm("update_user", "/MavenBankingProject/UpdateUserServlet"));
		String formTable = ServletUtilities.openTable("update_user_table") +
				ServletUtilities.tr(ServletUtilities.th("User ID") + ServletUtilities.td(ServletUtilities.readonlyInput("user_to_modify", String.valueOf(user.getUserId()))))+ 
				ServletUtilities.tr(ServletUtilities.th("Username") + ServletUtilities.td(ServletUtilities.textInput("username", user.getUsername()))) +
				ServletUtilities.tr(ServletUtilities.th("Email") + ServletUtilities.td(ServletUtilities.textInput("email", user.getEmail()))) +
				ServletUtilities.tr(ServletUtilities.th("First Name") + ServletUtilities.td(ServletUtilities.textInput("first_name", user.getFirstName()))) +
				ServletUtilities.tr(ServletUtilities.th("Last Name") + ServletUtilities.td(ServletUtilities.textInput("last_name", user.getLastName()))) +
				ServletUtilities.tr(ServletUtilities.th("Password") + ServletUtilities.td(ServletUtilities.passwordInput("password", "")));
		// Upgrade to premium form.
		if (canUpgrade) {
			Role standard, premium = null;
			try {
				standard = rService.getRoleByName("Standard");
				premium = rService.getRoleByName("Premium");
			} catch (RetrieveRoleException e) {
				e.printStackTrace();
				response.sendError(500, e.getMessage());
				return;
			}
			String upgradeSelect = ServletUtilities.openSelect("user_role", "user_role");
			upgradeSelect += ServletUtilities.selectOption(Integer.toString(standard.getRoleId()), standard.getRole()) +
					ServletUtilities.selectOption(Integer.toString(premium.getRoleId()), premium.getRole());
			upgradeSelect += ServletUtilities.closeSelect();
			formTable += ServletUtilities.tr(ServletUtilities.th("Role") + ServletUtilities.td(upgradeSelect));
		} else if (canModify){
			ArrayList<Role> roles = null;
			try {
				roles = rService.getAllRoles();
			} catch (RetrieveRoleException e) {
				e.printStackTrace();
				response.sendError(500, e.getMessage());
				return;
			}
			String roleSelect = ServletUtilities.openSelect("role", "role");
			for (Role r: roles) {
				roleSelect += ServletUtilities.selectOption(String.valueOf(r.getRoleId()), r.getRole());
			}
			roleSelect += ServletUtilities.closeSelect();
			formTable += ServletUtilities.tr(ServletUtilities.th("Role") + ServletUtilities.td(roleSelect));
		} else {
			formTable += ServletUtilities.tr(ServletUtilities.th("Role") + ServletUtilities.td(user.getRole().getRole()));
		}
		formTable += ServletUtilities.closeTable();
		formTable += ServletUtilities.submitButton("Update User Info");
		formTable += ServletUtilities.closeForm();
		out.println(formTable);
		out.println("<br>");
		
		// If e_can_modify_all_customer_info, modify user form.		
		// If ea_can_view_all_customer_info && !ea_can_view_all_customer_info view all user table.
		if (canView || canModify) {
			out.println("<h2>All Users</h2>");
			ArrayList<User> users = null;
			try {
				users = uService.getAllUsers();
			} catch (RetrieveUserException e) {
				e.printStackTrace();
				response.sendError(500, e.getMessage());
				return;
			}
			String userTable = new String();
			if (canModify) {
				userTable += ServletUtilities.openForm("user_select", "/MavenBankingProject/users");
			}
			userTable += ServletUtilities.openTable("user_table");
			userTable += ServletUtilities.tr(
					ServletUtilities.th("User ID") +
					ServletUtilities.th("Username") +
					ServletUtilities.th("Email") +
					ServletUtilities.th("First Name") +
					ServletUtilities.th("Last Name") +
					ServletUtilities.th("Role"));
			

			for (User u : users) {
				userTable += ServletUtilities.tr(
						ServletUtilities.td((canModify) ? ServletUtilities.radio("user_to_modify", String.valueOf(u.getUserId()), 
								String.valueOf(u.getUserId()), String.valueOf(u.getUserId())) :	String.valueOf(u.getUserId())) +
						ServletUtilities.td(u.getUsername()) +
						ServletUtilities.td(u.getEmail()) +
						ServletUtilities.td(u.getFirstName()) +
						ServletUtilities.td(u.getLastName()) +
						ServletUtilities.td(u.getRole().getRole()));
				
			}
			userTable += ServletUtilities.closeTable();
			if (canModify) {
				userTable += ServletUtilities.submitButton("Modify User") + ServletUtilities.closeForm();
			}
			out.println(userTable);
		}
		
		// End the document.
		out.println(ServletUtilities.closeDocument());
	}
}
