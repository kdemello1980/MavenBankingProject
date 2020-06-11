package com.revature.mavenbanking.service;

import java.util.ArrayList;

import com.revature.mavenbanking.dao.impl.PermissionDaoImpl;
import com.revature.mavenbanking.model.Permission;
import com.revature.mavenbanking.model.Role;

public class PermissionService {

	private PermissionDaoImpl pdi = new PermissionDaoImpl();
	
	public boolean addPermission(String name){
		return pdi.addPermission(name);
	}
	
	public boolean addRolePermission(Role role, Permission permission){
		return pdi.addRolePermission(role, permission);
	}
	
	public ArrayList<Permission> getAllPermissions(){
		return pdi.getAllPermissions();
	}
	
	public Permission getPermissionByID(int id){
		return pdi.getPermissionByID(id);
	}
	
	public ArrayList<Permission> getPermissionsByRoleId(int id){
		return pdi.getPermissionsByRoleId(id);
	}
	
	public boolean removeRolePermission(Role role, Permission permission){
		return pdi.removeRolePermission(role, permission);
	}
}
