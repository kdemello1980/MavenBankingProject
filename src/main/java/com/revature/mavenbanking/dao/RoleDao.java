package com.revature.mavenbanking.dao;

import java.util.List;

import com.revature.mavenbanking.model.Role;

public interface RoleDao {
	
	public List<Role> getAllRoles();
	public Role getRoleById(int id);
	public Role getRoleByName(String name);
	
	public boolean addRole(Role role);
	public boolean updateRole(Role role);
	public boolean deleteRoleById(int id);
}
