package com.revature.mavenbanking.model;

public class Customer extends AbstractUser {
	
	public Customer(int userId, String username, String password, String firstName, String lastName, String email,
			Role role) {
		super(userId, username, password, firstName, lastName, email, role);
		// TODO Auto-generated constructor stub
	}

}
