package com.revature.mavenbanking.service;

import java.util.ArrayList;

import com.revature.mavenbanking.dao.impl.PermissionDaoImpl;
import com.revature.mavenbanking.model.Permission;
import com.revature.mavenbanking.model.Role;

public class PermissionService {

	private PermissionDaoImpl pdi = new PermissionDaoImpl();
	
	public boolean addPermission(String name) throws Exception {
		if (pdi.addPermission(name))
			return true;
		else
			throw new Exception("Failed to add permission: " + name);
	}
	
	public boolean addRolePermission(Role role, Permission permission) throws Exception {
		if (pdi.addRolePermission(role, permission))
			return true;
		else
			throw new Exception("Failed to add " + permission.getPermissionName() + " to " + role.getRole());
	}
	
	public ArrayList<Permission> getAllPermissions() throws Exception {
		ArrayList<Permission> list = pdi.getAllPermissions();
		if (list != null)
			return list;
		else
			throw new Exception("Failed to retrieve all permissions.");
	}
	
	public Permission getPermissionByID(int id) throws Exception {
		Permission perm = pdi.getPermissionByID(id);
		if (perm != null)
			return perm;
		else
			throw new Exception("Failed to retrieve permission id: " + id);
	}
	
	public ArrayList<Permission> getPermissionsByRoleId(int id) throws Exception {
		ArrayList<Permission> perms = pdi.getPermissionsByRoleId(id);
		if (perms != null)
			return perms;
		else
			throw new Exception("Failed to get permission for role: " + id);
	}
	
	public boolean removeRolePermission(Role role, Permission permission) throws Exception {
		if (pdi.removeRolePermission(role, permission))
			return true;
		else
			throw new Exception("Failed to remove " + permission.getPermissionName() + " from " + role.getRole());
	}
}
