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
 * Servlet implementation class SourceServlet
 */
public class SourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SourceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cookie[] cookies = request.getCookies();

		for (int i = 0; i < cookies.length; i++){
			System.out.println(cookies[i].getName());
			System.out.println(cookies[i].getValue());
		}
		response.addCookie(new Cookie("security", "abcd123"));

		
		String username = request.getParameter("username");
		HttpSession session = request.getSession();
		session.setAttribute("user", username);
	
		// Print dynamic html response
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<a href='TargetServlet'> Click here to see the username.</a>");
	}

}
