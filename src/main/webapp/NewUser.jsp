<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<title>Register!</title>
</head>
<body>
<jsp:include page="NavBar.html"></jsp:include>
	<div class="container col col-sm-6">
	<div class="card col col-sm-auto">
	<div class="card-body">
	<h2 class="card-title">Register</h2>
	<form id="new_user" method="post" action="/MavenBankingProject/AddUserServlet">
		<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" class="form-control" id="username" name="username"
				aria-describedBy="usernameHelp" required> 
				<small id="usernameHelp"
				class="form-text text-muted">Enter a username for logging
				in.</small>
		</div>
		<div class="form-group">
			<label for="username">Email</label> <input type="email"
				class="form-control" id="email" name="email"
				aria-describedBy="emailHelp" required> <small id="emailHelp"
				class="form-text text-muted">Enter an email address.</small>
		</div>
		<div class="form-group">
			<label for="username">Password</label> <input type="password"
				class="form-control" id="password" name="password"
				aria-describedBy="passwordHelp" required> <small id="passwordHelp"
				class="form-text text-muted">Enter a password for logging
				in.</small>
		</div>
		<div class="form-group">
			<label for="username">First Name</label> <input type="text"
				class="form-control" id="first_name" name="first_name" required>
		</div>
		<div class="form-group">
			<label for="username">Last Name</label> <input type="text"
				class="form-control" id="last_name" name="last_name" required>
		</div>
		<div class="col text-center">
		<button type="submit" class="btn btn-primary">Create</button>
		</div>
	</form>
	</div>
</div>
</div>

	<!-- javascript sources -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
		integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
		crossorigin="anonymous"></script>
</body>
</html>