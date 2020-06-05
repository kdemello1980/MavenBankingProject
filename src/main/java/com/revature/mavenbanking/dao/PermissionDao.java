package com.revature.mavenbanking.dao;
import java.util.ArrayList;
import com.revature.mavenbanking.model.*;

public interface PermissionDao {
	
	/*
	 * Get all permissions, ordered by binaryPermission descending.
	 */
	public ArrayList<Permission> getAllPermissions();
	public Permission getPermissionByID();
	
	/*
	 * Add permission, need to sort bitstrings desc, get the first element
	 * of the list, increment it, and add that as the new permission.
	 */
	public boolean addPermission(String name);
	
	/*
	 * Delete permission: not possible. Always return true.
	 */
	public static boolean deletePermission() { return true; }
}
