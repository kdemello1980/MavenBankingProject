package com.kdemello.mavenbanking.service;

import java.util.ArrayList;

import com.kdemello.mavenbanking.dao.impl.RoleDaoImpl;
import com.kdemello.mavenbanking.exceptions.AddRoleException;
import com.kdemello.mavenbanking.exceptions.RetrieveRoleException;
import com.kdemello.mavenbanking.exceptions.UpdateRoleException;
import com.kdemello.mavenbanking.model.Role;

public class RoleService {
	private RoleDaoImpl rdi = new RoleDaoImpl();
	
	public boolean addRole(Role r) throws AddRoleException {
		if (rdi.addRole(r))
			return true;
		else
			throw new AddRoleException("Failed to add role: " + r.getRole());
	}
	
	public boolean deleteRoleById(int id) throws UpdateRoleException {
		if (rdi.deleteRoleById(id))
			return true;
		else
			throw new UpdateRoleException("Failed to delete role id: " + id);
	}
	
	public ArrayList<Role> getAllRoles() throws RetrieveRoleException {
		ArrayList<Role> list = rdi.getAllRoles();
		if (list != null)
			return list;
		else
			throw new RetrieveRoleException("Failed to retrieve all roles.");
	}
	
	public Role getRoleById(int id) throws RetrieveRoleException {
		Role r = rdi.getRoleById(id);
		if (r != null)
			return r;
		else
			throw new RetrieveRoleException("Failed to retrieve role id: " + id);
	}
	
	public Role getRoleByName(String name) throws RetrieveRoleException {
		Role r = rdi.getRoleByName(name);
		if (r != null)
			return r;
		else
			throw new RetrieveRoleException("Failed to retrieve role " + name); 
	}
	
	public boolean udpateRole(Role r) throws UpdateRoleException {
		if (rdi.updateRole(r))
			return true;
		else
			throw new UpdateRoleException("Failed to update role " + r.getRole());
	}
}
