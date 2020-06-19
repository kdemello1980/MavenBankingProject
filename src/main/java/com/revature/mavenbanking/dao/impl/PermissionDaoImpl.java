package com.revature.mavenbanking.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.revature.mavenbanking.dao.PermissionDao;
import com.revature.mavenbanking.model.Permission;
import com.revature.mavenbanking.model.Role;
import com.revature.mavenbanking.dao.postgresql.DAOUtilities;

public class PermissionDaoImpl implements PermissionDao {
	private Connection connection = null;
	private PreparedStatement stmt = null;
	
	@Override
	public ArrayList<Permission> getAllPermissions() {
		ArrayList<Permission> permList = new ArrayList<Permission>();

		String sql = new String("SELECT p.permission_id \"p.permission_id\", p.permission_name \"p.permission_name\"\n" + 
				"FROM kmdm_permissions p");
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Permission temp = new Permission();
				temp.setPermissionId(rs.getInt("p.permission_id"));
				temp.setPermissionName(rs.getString("p.permission_name"));
				permList.add(temp);
			}
			rs.close();
			return permList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public Permission getPermissionByID(int id) {
		String sql = new String("SELECT p.permission_id \"p.permission_id\", p.permission_name \"p.permission_name\"\n" + 
				"FROM kmdm_permissions p WHERE p.permission_id= ?");
		Permission perm = null;

		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);	
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				perm =  new Permission();
				perm.setPermissionId(rs.getInt("p.permission_id"));
				perm.setPermissionName(rs.getString("p.permission_name"));
			}
			rs.close();
			return perm;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	/*
	 * this worked yesterday
	 */
	@Override
	public ArrayList<Permission> getPermissionsByRoleId(int id) {
		ArrayList<Permission> permList = new ArrayList<Permission>();

//		String sql = new String("SELECT p.permission_name \"p.permission_name\", p.permission_id \"p.permission_id\", r.role_id \"r.role_id\"\n" + 
//				"FROM kmdm_permissions p, kmdm_roles r, kmdm_role_permissions rp\n" + 
//				"WHERE rp.role_id = r.role_id AND p.permission_id = rp.permission_id AND rp.role_id = ?");
		
		String sql = new String("SELECT p.permission_name AS \"p.permission_name\", p.permission_id AS \"p.permission_id\", r.role_id AS \"r.role_id\"\n" + 
				"FROM kmdm_permissions p\n" + 
				"JOIN kmdm_role_permissions rp ON rp.permission_id = p.permission_id\n" + 
				"JOIN kmdm_roles r ON rp.role_id = r.role_id\n" + 
				"WHERE r.role_id=?");
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
//				System.out.println("made it");
				Permission temp = new Permission();
				temp.setPermissionId(rs.getInt("p.permission_id"));
				temp.setPermissionName(rs.getString("p.permission_name"));
				permList.add(temp);
			}
			rs.close();
			return permList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean addPermission(String name) {
		String sql = "INSERT INTO kmdm_permissions (permission_name) VALUES (?)";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, name);
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
	public boolean addRolePermission(Role role, Permission permission) {
		String sql = "INSERT INTO kmdm_role_permissions (role_id, permission_id) VALUES (?,?)";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, role.getRoleId());
			stmt.setInt(2, permission.getPermissionId());
			
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
	public boolean removeRolePermission(Role role, Permission permission) {
		String sql = "DELETE FROM kmdm_role_permissions WHERE role_id = ? AND permission_id = ?";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, role.getRoleId());
			stmt.setInt(2, permission.getPermissionId());
			
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

	/* (non-Javadoc)
	 * @see com.revature.mavenbanking.dao.PermissionDao#getPermissionByPermissionName(java.lang.String)
	 */
	@Override
	public Permission getPermissionByPermissionName(String name) {
		String sql = "SELECT * FROM kmdm_permissions WHERE permission_name = ?";
		
		try {
			Permission perm = new Permission();
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, name);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				perm.setPermissionId(rs.getInt("permission_id"));
				perm.setPermissionName(rs.getString("permission_name"));
			}
			return perm;
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	// Closing all resources is important, to prevent memory leaks. 
	// Ideally, you really want to close them in the reverse-order you open them
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
