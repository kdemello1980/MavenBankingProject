<%@page import="java.util.HashMap"%>
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
<title>Accounts</title>
</head>
<body>


	<%
		if (session == null) {
	%>
	<jsp:forward page="index.jsp" />
	<%
		}
	%>
	<%
		Gson gson = new Gson().newBuilder().enableComplexMapKeySerialization().create();
	Type type = new TypeToken<HashMap<User, ArrayList<Account>>>() {
	}.getType();

	HashMap<User, ArrayList<Account>> map = gson.fromJson((String) request.getAttribute("accounts"), type);
	Type aType = new TypeToken<ArrayList<AccountType>>() {
	}.getType();
	ArrayList<AccountType> types = gson.fromJson((String) request.getAttribute("account_types"), aType);
	%>
	<jsp:include page="NavBar.html"></jsp:include>


	<%
		for (User u : map.keySet()) {
	%>
	<div class="container border border-primary rounded godown-60">
		<div class="card">
			<%
				String name = u.getLastName() + ", " + u.getFirstName();
			%>
			<h3><a href="/MavenBankingProject/JSONUserServlet?id=<%=u.getUserId() %>"><%=name%></a></h3>
			<table class="table table-striped table-bordered">
				<thead class="thead-light">
					<tr>
						<th scope="col">Account Number</th>
						<th scope="col">Type</th>
						<th scope="col" class="text-right">Balance</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (Account a : map.get(u)) {
					%>
					<tr>
						<th scope="row"><a
							href="JSONAccountServlet?id=<%=a.getAccountId()%>"><%=a.getAccountId()%></a></th>
						<td><%=a.getType().getAccountType()%></td>
						<td class="text-right">$<%=a.getBalance().toString()%></td>
					</tr>
					<%
						}
					%>

				</tbody>
			</table>
			</div>
			<div class="card rounded col-lg-12">
				<div class="card-body">
					<h4>Add Account</h4>
					<form class="form-horizontal" method="post" action="/MavenBankingProject/AddAccountServlet"
						name="new_account_form">
						<input name="form_name" type="hidden" value="new_account_form" />
						<input name="user_id" type="hidden" value="<%=u.getUserId() %>" />

						<div class="form-group row">
							<label for="initial_deposit" class="col-sm-2 col-form-label">Initial
								Deposit: </label>
							<div class="col-sm-4">
								<input type="number" value="100.00" min="100.00"
									data-decimals="2" step="0.01" name="initial_deposit" required />
							</div>
							<div class="form-group-row row">
								<label for="account_type" class="col-sm-auto col-form-label">Account
									Type:</label>
								<div class="col-sm-4">
									<select class="selectpicker" data-live-search="true"
										name="type_select">
										<%
											for (AccountType t : types) {
										%>
										<option value="<%=t.getTypeId()%>"><%=t.getAccountType()%></option>
										<%
											}
										%>
									</select>
								</div>
							</div>
							</div>
							<button type="submit" class="btn btn-primary">Add</button>
					</form>
					</div>
				</div>
			</div>
		<br>
		<%
			}
		%>
	
</body>
</html>