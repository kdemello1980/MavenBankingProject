package com.revature.mavenbanking.servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.mavenbanking.service.UserService;
import com.revature.mavenbanking.exceptions.RetrieveUserException;
import com.revature.mavenbanking.model.User;

public class LoginServlet extends HttpServlet {

	/**
	 * Automatically generated serialVersionUID.
	 */
	private static final long serialVersionUID = -3522635371108824972L;
	

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		PrintWriter out = res.getWriter();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = null;

		try {
			UserService service = new UserService();
			user = service.login(username, password);
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			req.getRequestDispatcher("/accounts").forward(req, res);
		} catch (RetrieveUserException e){
			e.printStackTrace();
			res.setStatus(400);
			res.setHeader("message", "Invalid credentials.");
			req.getRequestDispatcher("index.html").include(req, res);
		}

	}
}
