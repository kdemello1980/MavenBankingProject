package com.revature.mavenbanking.dao;

import java.util.List;

import com.revature.mavenbanking.model.Role;

public interface RoleDao {
	
	public List<Role> getAllRoles();
	public Role getRoleById(int id);
	public Role getRoleByName(String name);
	
	public boolean addRole(Role role);
	
	/*
	 * This will update the record in the role ID and then iterate 
	 * over all of the effective permissions in the role object passed
	 * as an argument and add/delete them as necessary.  If the effective
	 * permissions list is null, then all permissions that already exist for that 
	 * role are removed.  This is probably not what you want, so be sure to
	 * pass the effective permissions list as explicitly intended in the
	 * role object to be updated.
	 */
	public boolean updateRole(Role role);
	
	/*
	 * In addition to deleting the role from the role table.  All records
	 * in the role_permissions table referencing the deleted role will be removed.
	 * ON DELETE CASCADE is present in the foreign key constraint.
	 */
	public boolean deleteRoleById(int id);
	
}
