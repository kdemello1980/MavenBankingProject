package com.revature.mavenbanking.model;

public class Employee extends AbstractUser {
	private int employeeId;

	public Employee(int userId, String username, String password, String firstName, String lastName, String email,
			Role role, int employeeId) {
		super(userId, username, password, firstName, lastName, email, role);
		this.employeeId = employeeId;
	}

	/**
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

}
