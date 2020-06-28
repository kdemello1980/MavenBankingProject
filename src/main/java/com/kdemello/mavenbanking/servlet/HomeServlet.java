package com.kdemello.mavenbanking.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = -7879124574949421808L;
	
	public void init() throws ServletException {
		super.init();
	}
	
	public void destroy(){
		super.destroy();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		out.println(req.getAttribute("message"));
	}
}
