package com.revature.mavenbanking.model;

public class Permission {
	private int permissionId;
	private byte binaryPermission;
	
	public Permission(int permissionId, byte binaryPermission) {
		super();
		this.permissionId = permissionId;
		this.binaryPermission = binaryPermission;
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
	public byte getBinaryPermission() {
		return binaryPermission;
	}
	/**
	 * @param binaryPermission the binaryPermission to set
	 */
	public void setBinaryPermission(byte binaryPermission) {
		this.binaryPermission = binaryPermission;
	}
	@Override
	public String toString() {
		return "Permission [permissionId=" + permissionId + ", binaryPermission=" + binaryPermission + "]";
	}
	
}
