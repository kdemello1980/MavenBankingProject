package com.revature.mavenbanking.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mavenbanking.model.*;
import com.revature.mavenbanking.dao.impl.UserDaoImpl;


@WebServlet ("/users")
public class UserServlet extends HttpServlet {

	/**
	 * Automatically generated serialVersionUID.
	 */
	private static final long serialVersionUID = -1303561568359443221L;

	/*
	 * init()
	 */
	public void init() throws ServletException {
		super.init();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Creates the user.
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		PrintWriter out = res.getWriter();
		UserDaoImpl udi = new UserDaoImpl();
		
		if (req.getParameter("action").equals("add")){
			User newUser = new User();
			newUser.setEmail(req.getParameter("email"));
			newUser.setUsername(req.getParameter("userName"));
			newUser.setFirstName(req.getParameter("firstName"));
			newUser.setLastName(req.getParameter("lastName"));
			newUser.setPassword(req.getParameter("password"));
			
			Role role = new Role();
			role.setRoleId(1);
			newUser.setRole(role);
			

			
			if (new UserDaoImpl().addUser(newUser)){
				out.println("User " + newUser.getUsername() + " created.");
			} else {
				out.println("Failed to add user " + newUser.getUsername());
			}
		} else if (req.getParameter("action").equals("update")) {
			
			User user = udi.getUserByEmail(req.getParameter("email"));
			user.setPassword(req.getParameter("password"));
			if (udi.updateUser(user))
				out.println("User " + user.getUsername() + " updated.");
			else
				out.println("Failed to update user " + user.getUsername());
		}
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		UserDaoImpl udi = new UserDaoImpl();
		
		ArrayList<User> users = udi.getAllUsers();
		
		out.println("<table><tr><th>Username</th><th>First Name</th><th>Last Name</th><th>Email</th></tr>");
		
		for (User u : users){

			out.println("<tr><td>" + u.getUsername() +"</td><td>"+u.getFirstName()+"</td><td>"
					+u.getLastName()+"</td><td>"+u.getEmail()+"</td></tr>");
		}
		out.println("</table>");
	
	}
	
	/*
	 * destroy()
	 */
	public void destroy() {
		super.destroy();
	}
}
