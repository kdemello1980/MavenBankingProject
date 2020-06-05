package com.revature.mavenbanking.model;

import java.util.ArrayList;

public class Role {
	private int roleId;
	private String role;
	private ArrayList<Permission> effectivePermissions = null;

	
	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}
	
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the effectivePermissions
	 */
	public ArrayList<Permission> getEffectivePermissions() {
		return effectivePermissions;
	}

	/**
	 * @param effectivePermissions the effectivePermissions to set
	 */
	public void setEffectivePermissions(ArrayList<Permission> effectivePermissions) {
		this.effectivePermissions = effectivePermissions;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", role=" + role + ", effectivePermissions=" + effectivePermissions + "]";
	}
	
	

}
