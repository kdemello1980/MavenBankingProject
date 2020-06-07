package com.revature.mavenbanking.model;

public class Permission {
	private int permissionId;
	private String permissionName;
	
	public Permission() {}
	
	public Permission(int permissionId, String permission) {
		this.permissionId = permissionId;
		this.permissionName = permission;
	}
	
	/**
	 * @return the permissionId
	 */
	public int getPermissionId() {
		return permissionId;
	}
	/**
	 * @param permissionId the permissionId to set
	 */
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
	/**
	 * @return the binaryPermission
	 */
	public String getPermissionName() {
		return permissionName;
	}
	/**
	 * @param permission the binaryPermission to set
	 */
	public void setPermissionName(String permission) {
		this.permissionName = permission;
	}
	@Override
	public String toString() {
		return "Permission [permissionId=" + permissionId + ", permissionName=" + permissionName + "]";
	}
	
}
