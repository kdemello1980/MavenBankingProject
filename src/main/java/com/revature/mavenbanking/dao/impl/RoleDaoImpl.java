package com.revature.mavenbanking.dao.impl;
import com.revature.mavenbanking.dao.RoleDao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.mavenbanking.dao.oracle.DAOUtilities;
import com.revature.mavenbanking.model.Role;
import com.revature.mavenbanking.model.Permission;

import oracle.ucp.common.LoadBalancer.Stats.CloseResultsCounter;

public class RoleDaoImpl implements RoleDao {
	private Connection connection = null;
	private PreparedStatement stmt = null;
	
	public RoleDaoImpl() {
		try {
			connection = DAOUtilities.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	}
	
	protected void finalize() {
		closeResources();
	}
	
	@Override
	public List<Role> getAllRoles() {
		String sql = "SELECT r.role_id \"r.role_id\", r.name \"r.name\" \n"+
				"FROM roles r";
		List<Role> result = null;
		
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			result = new ArrayList<Role>();
			while (rs.next()) {
				Role r = new Role();
				r.setRoleId(rs.getInt("r.role_id"));
				r.setRole(rs.getString("r.name"));
				result.add(r);
			}
			return result;
		} catch (SQLException e)  {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public Role getRoleById(int id) {
		String sql = "SELECT r.role_id \"r.role_id\", r.name \"r.name\"\n" +
				"FROM roles r\n" +
				"WHERE r.role_id = ?";
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			Role r = new Role();
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				r.setRoleId(rs.getInt("r.role_id"));
				r.setRole(rs.getString("r.name"));
			}
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public Role getRoleByName(String name) {
		String sql = "SELECT r.role_id \"r.role_id\", r.name \"r.name\"\n" +
				"FROM roles r WHERE r.name =?";
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			Role r = new Role();
			while (rs.next()) {
				r.setRoleId(rs.getInt("r.role_id"));
				r.setRole(rs.getString("r.name"));
			}
			rs.close();
			/*
			 * Get the effective permissions from the PermissionDaoImpl object.
			 */
			PermissionDaoImpl perm = new PermissionDaoImpl();
			r.setEffectivePermissions(perm.getPermissionsByRoleId(r.getRoleId()));
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	/*
	 * After adding the role_id and name to the role table, we need to iterate 
	 * over all of the permission in the effectivePermissions field and attempt to
	 * add them to the role_permissions table.  This could throw an exception if we
	 * attempt to add a permission that already exists, so if we encounter one, we should 
	 * continue.
	 */
	@Override
	public boolean addRole(Role role) {
		String sql = "INSERT into roles (name) VALUES (?)";
		
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, role.getRole());
			
			if (stmt.executeUpdate() != 0) {
				PermissionDaoImpl permDao = new PermissionDaoImpl();
				Role newRole = this.getRoleByName(role.getRole());
				ArrayList<Permission> addedPermissions = new ArrayList<Permission>();
				for (Permission perm : role.getEffectivePermissions()) {
						permDao.addPermission(perm.getPermissionName());
						addedPermissions.add(perm);
					}
				for (Permission p : addedPermissions) {
					permDao.addRolePermission(newRole, p);
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	/*
	 * Here we need to update the role record in the role table, then iterate over the
	 * permissions list and add/remove them as required.
	 */
	@Override
	public boolean updateRole(Role role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRoleById(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}

}
