package com.revature.mavenbanking.model;

public class Role {
	private int roleId;
	private String role;
	private byte effectivePermissions;
	
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
	public byte getEffectivePermissions() {
		return effectivePermissions;
	}

	/**
	 * @param effectivePermissions the effectivePermissions to set
	 */
	public void setEffectivePermissions(byte effectivePermissions) {
		this.effectivePermissions = effectivePermissions;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", role=" + role + ", effectivePermissions=" + effectivePermissions + "]";
	}
	
	

}
