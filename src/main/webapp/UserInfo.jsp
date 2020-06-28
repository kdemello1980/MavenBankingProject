<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.kdemello.mavenbanking.model.*"%>
<%@ page import="com.google.gson.*"%>
<%@ page import="com.google.gson.reflect.TypeToken"%>
<%@ page import="java.lang.reflect.Type"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<title>User Information</title>


</head>
<body>
	<jsp:include page="NavBar.html"></jsp:include>
	<%
		if (session == null) {
	%>
	<jsp:forward page="index.jsp" />
	<%
		}
	%>
	<!-- Initialize User Object -->
	<%
		Gson gson = new Gson().newBuilder().enableComplexMapKeySerialization().create();
	Type type = new TypeToken<User>() {}.getType();
	User user = gson.fromJson((String) request.getAttribute("user_info"), type);
	%>
	<div class="container">
		<h1>Details for <%=user.getFirstName() + " " + user.getLastName() %></h1>
		<div class="card col-sm-7">
			<form method="post" action="/MavenBankingProject/login">

				<div class="form-group row">
					<label for="username" class="col-sm-2 col-form-label">Username:
					</label>
					<div class="col-sm-7">
						<input id="username" name="username" required
							placeholder="Enter Username" disabled/>
					</div>
				</div>

				<div class="form-group row">
					<label for="username" class="col-sm-2 col-form-label">Password:
					</label>
					<div class="col-sm-3">
						<input id="password" name="password" type="password" required
							placeholder="Enter Password" />
					</div>
				</div>
				<div class="form-check">
					<input class="form-check-input" type="checkbox" value=""
						id="enable_edit" name="enable_edit"> <label
						class="form-check-label" for="defaultCheck2">Enable Changes</label>
				</div>


				<button type="submit" class="btn btn-primary col-sm-auto">Update User</button>
				<a href="/MavenBankingProject/AddUserServlet">Click here to
					register!</a>
			</form>
		</div>
	</div>
	<script>
		$('#enable_edit').click(function() {
			if ($(this).is(':checked')) {
				//enable form fields
				$("#username").prop('disabled', false); //true,false 
			} else {
				$('#username').prop('disabled', true);
			}
		});
	</script>
</body>
</body>
</html>