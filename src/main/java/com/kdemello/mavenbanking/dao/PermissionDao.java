package com.kdemello.mavenbanking.dao;
import java.util.ArrayList;

import com.kdemello.mavenbanking.model.*;

public interface PermissionDao {
	
	/*
	 * Get all permissions, ordered by binaryPermission descending.
	 */
	public ArrayList<Permission> getAllPermissions();
	public Permission getPermissionByID(int id);
	public ArrayList<Permission> getPermissionsByRoleId(int id);
	public Permission getPermissionByPermissionName(String name);
	
	public boolean addPermission(String name);
	
	/*
	 * Delete permission: not possible. Always return true.
	 */
	public static boolean deletePermission() { return true; }
	
	/*
	 * Modify permission: messy. Not allowed for now.
	 */
	public static boolean updatePermission(int id, String permission) { return true; }
	
	/*
	 * Add permission to the role_permissions table.
	 */
	public boolean addRolePermission(Role role, Permission permission);
	
	/*
	 * Remove the permission to role mapping. This is also called when updating a 
	 * role.
	 */
	public boolean removeRolePermission(Role role, Permission permission);
}
