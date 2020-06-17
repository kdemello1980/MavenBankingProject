package com.revature.mavenbanking.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.mavenbanking.exceptions.RetrievePermissionException;
import com.revature.mavenbanking.model.Permission;
import com.revature.mavenbanking.model.User;
import com.revature.mavenbanking.service.PermissionService;

public class ServletUtilities {
	
	public static String openDocument(String title, String heading){
		return  "<!doctype html><html lang=\"en\"><head><title>" + title + "</title><style>table, th, td { border: 1px solid black; } </style></head>" +
				"<body><form name=\"home\" action=\"/MavenBankingProject/accounts\" method=\"post\"> <input type=\"submit\" value=\"Home\"></submit>" +
				"</form><h2>" + heading +"</h2>";
	}

	public static String closeDocument(){
		return "</body></html>";
	}
	
	public static String openForm(String form_id, String action){
		return "<form method=\"post\" id=\"" + form_id + "\" action=\"" +  action + "\">";
	}
	
	public static String closeForm(){
		return "</form>";
	}
	
	public static String openTable(String id){
		return "<table id=\"" + id + "\">";
	}
	
	public static String closeTable(){
		return "</table>";
	}
	
	public static String tr(String content){
		return "<tr>" + content + "</tr>";
	}
	
	public static String th(String content){
		return "<th>" + content + "</th>";
	}
	
	public static String td(String content){
		return "<td>" + content + "</td>";
	}
	
	public static String anchor(String target, String content){
		return "<a href=\"" + target + "\">" + content + "</a>";
	}
	
	public static String textInput(String id, String value){
		return "<input type=\"text\" name=\"" + id + "\" value=\"" + value + "\" required/>";
	}
	
	public static String numberInput(String id, String value){
		return "<input type=\"number\" name=\"" + id + "\" value=\"" + value + "\" required/>";
	}
	
	public static String dollarInput(String id, String value){
		return "<input type=\"number\" step=\".01\" min=\"0.00\" name=\"" + id + "\" value=\"" + value + "\" required/>";
	}
	public static String passwordInput (String id, String value){
		return "<input type=\"password\" name=\"" + id + "\" value=\"" + value + "\" required/>";
	}
	
	public static String radio(String name, String id, String value, String label){
		return "<input type=\"radio\" id=\"" + id + "\" name=\"" + name + "\" value=\"" + value + "\">" +
				"<label for=\"" + id + "\">" + label + "</label>";
	}
	
	public static String submitButton(String value){
		return "<input type=\"submit\" value=\"" + value + "\">";
	}
	
	public static String openSelect(String name, String id){
		return "<select name=\"" + name + "\" id=\"" + id + "\">";
	}
	
	public static String selectOption(String value, String label) {
		return "<option value=\"" + value + "\">" + label + "</option>";
	}
	
	public static String closeSelect(){
		return "</select>";
	}
	
	// Get and verify a user session exists.
	public static User getUserFromSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		User user = (User)request.getSession().getAttribute("user");
		if (user == null){
			response.setStatus(401);
			response.setHeader("message", "The incoming token has expired.");
			request.getRequestDispatcher("index.html").include(request, response);
		}
		return user;
	}
	
	// Check a user's permissions.
	public static boolean hasPermission(User user, String permission, HttpServletResponse res) {
		PermissionService pService = new PermissionService();
		boolean hasPermission = false;
		try {
			Permission perm = pService.getPermissionByPermissionName(permission);
			for (Permission p : user.getRole().getEffectivePermissions()){
				if (p.getPermissionName().equals(perm.getPermissionName())) {
					hasPermission = true;
					break;
				}
			}
			return hasPermission;
		} catch (RetrievePermissionException e) {
			e.printStackTrace();
			try {
				res.sendError(500, e.getMessage());
			} catch (IOException io) {
				io.printStackTrace();
			}
			return false;
		}
	}
}
