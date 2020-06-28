package com.kdemello.mavenbanking.threads.permissions;

import java.util.ArrayList;

import com.kdemello.mavenbanking.dao.PermissionDao;
import com.kdemello.mavenbanking.model.Permission;
import com.kdemello.mavenbanking.model.Role;

public class GetAllPermissions implements PermissionDao, Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Permission> getAllPermissions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission getPermissionByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Permission> getPermissionsByRoleId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission getPermissionByPermissionName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addPermission(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addRolePermission(Role role, Permission permission) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeRolePermission(Role role, Permission permission) {
		// TODO Auto-generated method stub
		return false;
	}

}
