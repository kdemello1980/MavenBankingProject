package com.kdemello.mavenbanking.service;

import java.util.ArrayList;

import com.kdemello.mavenbanking.dao.impl.PermissionDaoImpl;
import com.kdemello.mavenbanking.exceptions.AddPermissionException;
import com.kdemello.mavenbanking.exceptions.RetrievePermissionException;
import com.kdemello.mavenbanking.exceptions.UpdatePermissionException;
import com.kdemello.mavenbanking.model.Permission;
import com.kdemello.mavenbanking.model.Role;

public class PermissionService {

	private PermissionDaoImpl pdi = new PermissionDaoImpl();
	
	public boolean addPermission(String name) throws AddPermissionException {
		if (pdi.addPermission(name))
			return true;
		else
			throw new AddPermissionException("Failed to add permission: " + name);
	}
	
	public boolean addRolePermission(Role role, Permission permission) throws AddPermissionException {
		if (pdi.addRolePermission(role, permission))
			return true;
		else
			throw new AddPermissionException("Failed to add " + permission.getPermissionName() + " to " + role.getRole());
	}
	
	public ArrayList<Permission> getAllPermissions() throws RetrievePermissionException {
		ArrayList<Permission> list = pdi.getAllPermissions();
		if (list != null)
			return list;
		else
			throw new RetrievePermissionException("Failed to retrieve all permissions.");
	}
	
	public Permission getPermissionByID(int id) throws RetrievePermissionException {
		Permission perm = pdi.getPermissionByID(id);
		if (perm != null)
			return perm;
		else
			throw new RetrievePermissionException("Failed to retrieve permission id: " + id);
	}
	
	public ArrayList<Permission> getPermissionsByRoleId(int id) throws RetrievePermissionException {
		ArrayList<Permission> perms = pdi.getPermissionsByRoleId(id);
		if (perms != null)
			return perms;
		else
			throw new RetrievePermissionException("Failed to get permission for role: " + id);
	}
	
	public boolean removeRolePermission(Role role, Permission permission) throws UpdatePermissionException {
		if (pdi.removeRolePermission(role, permission))
			return true;
		else
			throw new UpdatePermissionException("Failed to remove " + permission.getPermissionName() + " from " + role.getRole());
	}
	
	public Permission getPermissionByPermissionName(String name) throws RetrievePermissionException {
		Permission p = pdi.getPermissionByPermissionName(name);
		if (p != null)
			return p;
		else
			throw new RetrievePermissionException("Failed to retrieve permission: " + name);
	}
}
