package com.kdemello.mavenbanking.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kdemello.mavenbanking.exceptions.RetrieveUserException;
import com.kdemello.mavenbanking.model.User;
import com.kdemello.mavenbanking.service.UserService;

/**
 * Servlet implementation class JSONUserServlet
 */
public class JSONUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService uService = new UserService();
    private Gson gson = new Gson().newBuilder().enableComplexMapKeySerialization().create();
	
    		
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @param id: the user id of the user for which to display info.
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		User user = ServletUtilities.getUserFromSession(request, response);
		
		User editUser = null;
		int userId = Integer.parseInt((String)request.getParameter("id"));
		try {
			editUser = uService.getUserById(userId);
		} catch (RetrieveUserException e) {
			e.printStackTrace();
			response.sendError(500,e.getMessage());
		}
		
		
//		Need to check role & permissions and add roles here.

		System.out.println(gson.toJson(editUser));
		request.setAttribute("user_info", gson.toJson(editUser));
		request.getRequestDispatcher("/UserInfo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}

}
