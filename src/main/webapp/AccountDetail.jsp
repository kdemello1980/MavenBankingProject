<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.kdemello.mavenbanking.model.*"%>
<%@ page import="com.google.gson.*"%>
<%@ page import="com.google.gson.reflect.TypeToken"%>
<%@ page import="java.lang.reflect.Type"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<meta charset="UTF-8">
<title>Account Detail</title>
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
	<%
		Gson gson = new Gson().newBuilder().enableComplexMapKeySerialization().create();
	Type aType = new TypeToken<Account>() {
	}.getType();
	Account account = gson.fromJson((String) request.getAttribute("account"), aType);
	Type uType = new TypeToken<ArrayList<User>>() {
	}.getType();
	ArrayList<User> users = gson.fromJson((String) request.getAttribute("users"), uType);
	System.out.println((String) request.getAttribute("users"));
	%>
	<div class="container">


				<div class="row">
					<div class="col-lg-6">
						<div class="card border border-primary rounded">
							<div class="card-body">
								<!-- Account info table -->
								<h4>
									Account Details for
									<%=account.getAccountId()%>
									(<%=account.getType().getAccountType()%>)
								</h4>
								<table class="table table-striped table-bordered">

									<tbody>
										<tr>
											<th scope="row">Type:</th>
											<td><%=account.getType().getType()%></td>
										</tr>
										<tr>
											<th scope="row">Interest Rate:</th>
											<td class="text-right"><%=account.getType().getInterestRate()%></td>
										</tr>
										<tr>
											<th scope="row">Monthly Fee:</th>
											<td class="text-right">$<%=account.getType().getMonthlyFee()%></td>
										</tr>
										<tr>
											<th scope="row">Balance:</th>
											<td class="text-right"><b>$<%=account.getBalance()%></b></td>
										</tr>
									</tbody>
								</table>

							</div>
						</div>
					</div>

					<!--  Deposit form -->
					<div class="col-lg-6">
						<div class="row">
							<div class="card border border-primary rounded">
								<div class="card-body">
									<h4>Deposit:</h4>
									<form method="post"
										action="/MavenBankingProject/JSONAccountServlet"
										name="deposit_form">
										<input name="id" type="hidden"
											value="<%=account.getAccountId()%>" /> <input
											name="form_name" type="hidden" value="deposit_form" />

										<div class="form-group row">
											<label for="deposit_amount"
												class="col-sm-auto col-form-label">Amount: </label>
											<div class="col-sm-auto">
												<input type="number" value="0.00" min="0" data-decimals="2"
													step="0.01" name="deposit_amount" required
													placeholder="Enter Amount" />
											</div>
										</div>
										<button type="submit" class="btn btn-primary">Submit</button>
									</form>
								</div>
							</div>
						</div>

						<!-- Withdraw form -->
						<div class="row">
							<div class="card border border-primary rounded">
								<div class="card-body">
									<h4>Withdraw:</h4>
									<form method="post"
										action="/MavenBankingProject/JSONAccountServlet"
										name="withdraw_form">
										<input name="id" type="hidden"
											value="<%=account.getAccountId()%>" /> <input
											name="form_name" type="hidden" value="withdraw_form" />

										<div class="form-group row">
											<label for="withdraw_amount"
												class="col-sm-auto col-form-label">Amount:</label>
											<div class="col-sm-auto">
												<input type="number" value="0.00" min="0" data-decimals="2"
													step="0.01" name="withdraw_amount" required
													placeholder="Enter Amount" />
											</div>
										</div>
										<button type="submit" class="btn btn-primary">Submit</button>
									</form>
								</div>
							</div>
						</div>

						<!-- Transfer form -->
						<div class="row">
							<div class="card border border-primary rounded">
								<div class="card-body">
									<h4>Transfer:</h4>
									<form method="post"
										action="/MavenBankingProject/JSONAccountServlet"
										name="transfer_form">
										<input name="id" type="hidden"
											value="<%=account.getAccountId()%>" /> <input
											name="form_name" type="hidden" value="transfer_form" />

										<div class="form-group row">
											<label for="transfer_amount"
												class="col-sm-auto col-form-label">Amount:</label>
											<div class="col-sm-auto">
												<input type="number" value="0.00" min="0" data-decimals="2"
													step="0.01" name="transfer_amount" required
													placeholder="Enter Amount" />
											</div>
										</div>
										<div class="form-group row">
											<label for="transfer_account"
												class="col-sm-auto col-form-label">Account:</label>
											<div class="col-sm-auto">
												<input type="number" step="1" name="transfer_account"
													required placeholder="Enter Account #" />
											</div>
										</div>
										<button type="submit" class="btn btn-primary">Submit</button>
									</form>
								</div>
							</div>
						</div>
						<!-- Add user form. -->
								<%
								if (users != null){
								%>
						<div class="row">
							<div class="card border border-primary rounded">
								<div class="card-body">
									<h4>Add User:</h4>
									<form method="post"
										action="/MavenBankingProject/JSONAccountServlet"
										name="add_user_form">
										<input name="id" type="hidden"
											value="<%=account.getAccountId()%>" /> <input
											name="form_name" type="hidden" value="add_user_form" />

										<div class="form-group row">
											<label for="user_to_add"
												class="col-sm-auto col-form-label">User:</label>
											<div class="col-sm-auto">
												<select class="selectpicker" data-live-search="true" name="user_to_add">
													<%
													for (User u : users){													
													%>
													<option value="<%=u.getUserId() %>"><%=u.getLastName() %>, <%=u.getFirstName()%></option>
													<%} %>
												</select>
											</div>
										</div>
										<button type="submit" class="btn btn-primary">Submit</button>
									</form>
								</div>
							</div>
						</div>
						<%} %>



					</div>
				</div>
			</div>

</body>
</html>