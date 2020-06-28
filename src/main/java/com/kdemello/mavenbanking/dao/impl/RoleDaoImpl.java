package com.kdemello.mavenbanking.dao.impl;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kdemello.mavenbanking.dao.RoleDao;
import com.kdemello.mavenbanking.dao.postgresql.DAOUtilities;
import com.kdemello.mavenbanking.model.Permission;
import com.kdemello.mavenbanking.model.Role;


public class RoleDaoImpl implements RoleDao {
	private Connection connection = null;
	private PreparedStatement stmt = null;

	
	@Override
	public ArrayList<Role> getAllRoles() {
		String sql = "SELECT r.role_id \"r.role_id\", r.name \"r.name\" \n"+
				"FROM kmdm_roles r";
		ArrayList<Role> result = null;
		
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();;
			result = new ArrayList<Role>();
		
			while (rs.next()) {
				Role r = new Role();
				r.setRoleId(rs.getInt("r.role_id"));
				r.setRole(rs.getString("r.name"));
				//r.setEffectivePermissions(new PermissionDaoImpl().getAllPermissions());
				result.add(r);
			}
			rs.close();
			PermissionDaoImpl pdi = new PermissionDaoImpl();
			for (Role r : result)
				r.setEffectivePermissions(pdi.getPermissionsByRoleId(r.getRoleId()));

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
				"FROM kmdm_roles r\n" +
				"WHERE r.role_id = ?";
		try {
			connection = DAOUtilities.getConnection();

			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			Role r = new Role();
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				r.setRoleId(rs.getInt("r.role_id"));
				r.setRole(rs.getString("r.name"));
			}
			rs.close();
			PermissionDaoImpl p = new PermissionDaoImpl();
			r.setEffectivePermissions(p.getPermissionsByRoleId(r.getRoleId()));
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
				"FROM kmdm_roles r WHERE r.name =?";
		try {
			connection = DAOUtilities.getConnection();

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
		String sql = "INSERT into kmdm_roles (name) VALUES (?)";
		
		try {
			connection = DAOUtilities.getConnection();

			stmt = connection.prepareStatement(sql);
			stmt.setString(1, role.getRole());
			
//			connection.setAutoCommit(false);
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
//				connection.setAutoCommit(true);
				return true;
			} else {
//				connection.setAutoCommit(true);
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
		String sql = new String("UPDATE roles SET name = ? WHERE role_id = ?");
		try {
			PermissionDaoImpl pdi = new PermissionDaoImpl();
			ArrayList<Permission> perms = pdi.getPermissionsByRoleId(role.getRoleId());
				
			for (Permission p : perms) {
				if (role.getEffectivePermissions().contains(p))
					continue;
				else
					pdi.removeRolePermission(role, p);
			}
			
			for (Permission p : role.getEffectivePermissions()) {
				if (perms.contains(p))
					continue;
				else
					pdi.addRolePermission(role, p);
			}		
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, role.getRole());
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean deleteRoleById(int id) {
		PermissionDaoImpl pdi = new PermissionDaoImpl();
		Role r = this.getRoleById(id);
		for (Permission p : r.getEffectivePermissions()) {
			pdi.removeRolePermission(r, p);
		}
		
		try {
			String sql = new String("DELETE FROM kmdm_roles WHERE role_id = ?");
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
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
