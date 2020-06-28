package com.kdemello.mavenbanking.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TargetServlet
 */
public class TargetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("user");
		
		Cookie[] cookies = request.getCookies();

		for (int i = 0; i < cookies.length; i++){
			System.out.println(cookies[i].getName());
			System.out.println(cookies[i].getValue());
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h1>Our username is: " + username + "</h1>");
	}

}
