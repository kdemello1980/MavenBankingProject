<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.kdemello.mavenbanking.model.*"%>
<%@ page import="com.google.gson.*"%>
<%@ page import="com.google.gson.reflect.TypeToken"%>
<%@ page import="java.lang.reflect.Type"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Details</title>



</head>
<body>
	<%
		if (session == null) {
	%>
	<jsp:forward page="index.jsp" />
	<%
		}
	%>
	<jsp:include page="NavBar.html"></jsp:include>
	<%
		Type type = new TypeToken<User>() {
	}.getType();
	Gson gson = new Gson().newBuilder().enableComplexMapKeySerialization().create();
	User user = gson.fromJson((String) request.getAttribute("user"), type);
	boolean isAdmin = (boolean) request.getAttribute("is_admin");
	boolean isStandard = (boolean) request.getAttribute("is_standard");
	boolean readOnly = (boolean) request.getAttribute("read_only");
	%>


	<div class="container col-sm-6">
		<div class="card">
			<div class="card-body border border-primary rounded">
				<form method="post">
					<input name="id" type="hidden" value="<%=user.getUserId()%>" type="hidden"/>
					<h3>
						Details for
						<%=user.getFirstName() + " " + user.getLastName()%></h3>
					<div class="form-group row border">
						<label for="user_name" class="col-sm-2 col-form-label">Username</label>
						<div class="col-sm-auto">
							<input type="text" class="form-control col-sm-auto"
								id="user_name_input" name="user_name_input"
								value="<%=user.getUsername()%>" disabled>
						</div>
						<div class="col-sm-auto">
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="checkbox"
									id="user_form_check"> <label class="form-check-label"
									for="user_form_check">modify</label>
							</div>
						</div>
					</div>

					<div class="form-group row border">
						<label for="email" class="col-sm-2 col-form-label">Email</label>
						<div class="col-sm-auto">
							<input type="text" class="form-control col-sm-auto" id="email"
								name="email" value="<%=user.getEmail()%>" disabled>
						</div>
						<div class="col-sm-auto">
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="checkbox" id="email_check">
								<label class="form-check-label" for="email_check">modify</label>
							</div>
						</div>
					</div>

					<div class="form-group row border">
						<label for="first_name" class="col-sm-2 col-form-label">First
							Name</label>
						<div class="col-sm-auto">
							<input type="text" class="form-control col-sm-auto"
								id="first_name" name="first_name"
								value="<%=user.getFirstName()%>" disabled>
						</div>
						<div class="col-sm-auto">
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="checkbox"
									id="first_name_check"> <label class="form-check-label"
									for="first_name_check">modify</label>
							</div>
						</div>
					</div>

					<div class="form-group row border">
						<label for="last_name" class="col-sm-2 col-form-label">Last
							Name</label>
						<div class="col-sm-auto">
							<input type="text" class="form-control col-sm-auto"
								id="last_name" name="last_name" value="<%=user.getLastName()%>"
								disabled>
						</div>
						<div class="col-sm-auto">
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="checkbox"
									id="last_name_check"> <label class="form-check-label"
									for="last_name_check">modify</label>
							</div>
						</div>
					</div>

					<!-- Role -->
					<div class="form-group row border">
						<label for="role" class="col-sm-2 col-form-label">Role</label>
						<div class="col-sm-auto">
							<input type="text" class="form-control col-sm-auto" id="role"
								name="role" value="<%=user.getRole().getRole()%>" disabled>
						</div>
						<div class="col-sm-auto">
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="checkbox" id="role_check">
								<label class="form-check-label" for="role_check">modify</label>
							</div>
						</div>
					</div>
					<button type="submit" class="btn btn-primary">Submit</button>


				</form>
			</div>
		</div>
	</div>


	<script>
		$('#user_form_check').click(function() {
			if ($(this).is(':checked')) {
				$("#user_name_input").prop('disabled', false);
			} else {
				$("#user_name_input").prop('disabled', true);
			}
		});

		$('#email_check').click(function() {
			if ($(this).is(':checked')) {
				$("#email").prop('disabled', false);
			} else {
				$("#email").prop('disabled', true);
			}
		});

		$('#first_name_check').click(function() {
			if ($(this).is(':checked')) {
				$("#first_name").prop('disabled', false);
			} else {
				$("#first_name").prop('disabled', true);
			}
		});

		$('#last_name_check').click(function() {
			if ($(this).is(':checked')) {
				$("#last_name").prop('disabled', false);
			} else {
				$("#last_name").prop('disabled', true);
			}
		});

/*		$('#role_check').click(function() {
			if ($(this).is(':checked')) {
				$("#role").prop('disabled', false);
			} else {
				$("#role").prop('disabled', true);
			}
		});
		*/

		$('#user_form_check').click(function() {
			if ($(this).is(':checked')) {
				$("#user_name_input").prop('disabled', false);
			} else {
				$("#user_name_input").prop('disabled', true);
			}
		});
	</script>
</body>
</html>