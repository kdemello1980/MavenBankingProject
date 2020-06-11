package com.revature.mavenbanking.servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mavenbanking.dao.impl.UserDaoImpl;
import com.revature.mavenbanking.model.User;

public class LoginServlet extends HttpServlet {

	/**
	 * Automatically generated serialVersionUID.
	 */
	private static final long serialVersionUID = -3522635371108824972L;
	
	/*
	 * init()
	 */
	public void init() throws ServletException {
		super.init();
	}
	
	/*
	 * service()
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		if (email != null && password != null){
			UserDaoImpl udi = new UserDaoImpl();
			User u = udi.getUserByEmail(email);
			if (u != null && u.getPassword().equals(password)){
				out.println("Login success");
			} else {
				out.println("Login failure: Incorrect password.");
			}
			
		} else {
			out.println("Login Failure: invalid username or password");
		}
	}
	
	/*
	 * destroy()
	 */
	public void destroy() {
		super.destroy();
	}

}
