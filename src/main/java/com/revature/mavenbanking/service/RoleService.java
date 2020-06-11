package com.revature.mavenbanking.service;

import java.util.ArrayList;

import com.revature.mavenbanking.dao.impl.RoleDaoImpl;
import com.revature.mavenbanking.model.Role;

public class RoleService {
	private RoleDaoImpl rdi = new RoleDaoImpl();
	
	public boolean addRole(Role r){
		return rdi.addRole(r);
	}
	
	public boolean deleteRoleById(int id){
		return rdi.deleteRoleById(id);
	}
	
	public ArrayList<Role> getAllRoles(){
		return rdi.getAllRoles();
	}
	
	public Role getRoleById(int id){
		return rdi.getRoleById(id);
	}
	
	public Role getRoleByName(String name){
		return rdi.getRoleByName(name);
	}
	
	public boolean udpateRole(Role r){
		return rdi.updateRole(r);
	}
}
