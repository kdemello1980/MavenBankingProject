<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<title>Login</title>
</head>
<body>
	<div class="container">
		<h1>Welcome! Please login:</h1>
		<div class="card col-sm-7">
			<form method="post" action="/MavenBankingProject/login">

				<div class="form-group row">
					<label for="username" class="col-sm-2 col-form-label">Username:
					</label>
					<div class="col-sm-7">
						<input name="username" required placeholder="Enter Username" />
					</div>
				</div>

				<div class="form-group row">
					<label for="username" class="col-sm-2 col-form-label">Password:
					</label>
					<div class="col-sm-3">
						<input name="password" type="password" required placeholder="Enter Password" />
					</div>
				</div>


				
				<button type="submit" class="btn btn-primary col-sm-2">Login</button>
				<a href="/MavenBankingProject/AddUserServlet">Click here to
					register!</a>
			</form>
		</div>
	</div>
</body>
</html>