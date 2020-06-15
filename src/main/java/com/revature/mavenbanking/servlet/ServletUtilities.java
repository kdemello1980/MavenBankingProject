package com.revature.mavenbanking.servlet;

public class ServletUtilities {
	
	public static String openDocument(String title, String heading){
		return  "<!doctype html><html lang=\"en\"><head><title>" + title + "</title></head><body>" +
		"<h2>Welcome " + heading +"</h2>";
	}

	public static String closeDocument(){
		return "</body></html>";
	}
	
	public static String openForm(String form_id, String action){
		return "<form id=\"" + form_id + "\" action=\"" +  action + "\">";
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
	
	public static String textInput (String id, String value){
		return "<intput type=\"text\" id=\"" + id + "\" value=\"" + value + "\"/>";
	}
	
	public static String submitButton(String value){
		return "<input type=\"submit\" value=\"" + value + "\">";
	}
}
