package com.revature.mavenbanking.service;

import java.util.ArrayList;

import com.revature.mavenbanking.dao.impl.RoleDaoImpl;
import com.revature.mavenbanking.model.Role;

public class RoleService {
	private RoleDaoImpl rdi = new RoleDaoImpl();
	
	public boolean addRole(Role r) throws Exception {
		if (rdi.addRole(r))
			return true;
		else
			throw new Exception("Failed to add role: " + r.getRole());
	}
	
	public boolean deleteRoleById(int id) throws Exception {
		if (rdi.deleteRoleById(id))
			return true;
		else
			throw new Exception("Failed to delete role id: " + id);
	}
	
	public ArrayList<Role> getAllRoles() throws Exception {
		ArrayList<Role> list = rdi.getAllRoles();
		if (list != null)
			return list;
		else
			throw new Exception("Failed to retrieve all roles.");
	}
	
	public Role getRoleById(int id) throws Exception {
		Role r = rdi.getRoleById(id);
		if (r != null)
			return r;
		else
			throw new Exception("Failed to retrieve role id: " + id);
	}
	
	public Role getRoleByName(String name) throws Exception {
		Role r = rdi.getRoleByName(name);
		if (r != null)
			return r;
		else
			throw new Exception("Failed to retrieve role " + name); 
	}
	
	public boolean udpateRole(Role r) throws Exception {
		if (rdi.updateRole(r))
			return true;
		else
			throw new Exception("Failed to update role " + r.getRole());
	}
}
