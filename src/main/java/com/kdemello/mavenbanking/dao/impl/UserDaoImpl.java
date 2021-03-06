package com.kdemello.mavenbanking.dao.impl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;

import com.kdemello.mavenbanking.dao.UserDao;
import com.kdemello.mavenbanking.dao.postgresql.DAOUtilities;
import com.kdemello.mavenbanking.model.Role;
import com.kdemello.mavenbanking.model.User;

public class UserDaoImpl implements UserDao {
	private Connection connection = null;
	private PreparedStatement stmt = null;

	@Override
	public ArrayList<User> getAllUsers() {
		String sql = "SELECT user_id, username, email, user_pwd, firstname, lastname, role FROM kmdm_users";
		
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<User> users = new ArrayList<User>();

			while (rs.next()) {
//				System.out.println("found users");
				User u = new User();
				u.setUserId(rs.getInt("user_id"));
				u.setUsername(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("user_pwd"));
				u.setFirstName(rs.getString("firstname"));
				u.setLastName(rs.getString("lastname"));
				u.setRoleId(rs.getInt("role"));
				users.add(u);
			}
			rs.close();
			for (User u : users)
				u.setRole(new RoleDaoImpl().getRoleById(u.getRoleId()));
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}		
	}


	@Override
	public User getUserById(int id) {
		String sql = "SELECT * FROM kmdm_users WHERE user_id=?";
		
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();
			User u = new User();
			while (rs.next()) {
				u.setUserId(rs.getInt("user_id"));
				u.setUsername(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("user_pwd"));
				u.setFirstName(rs.getString("firstname"));
				u.setLastName(rs.getString("lastname"));
				u.setRoleId(rs.getInt("role"));
			}
			u.setRole(new RoleDaoImpl().getRoleById(u.getRoleId()));
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public User getUserByUserName(String username) {
		String sql = "SELECT * FROM kmdm_users WHERE username=?";
		
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next() != false) {
				User u = new User();
				do {
					u.setUserId(rs.getInt("user_id"));
					u.setUsername(rs.getString("username"));
					u.setEmail(rs.getString("email"));
					u.setPassword(rs.getString("user_pwd"));
					u.setFirstName(rs.getString("firstname"));
					u.setLastName(rs.getString("lastname"));
					u.setRoleId(rs.getInt("role"));
				} while (rs.next());
				u.setRole(new RoleDaoImpl().getRoleById(u.getRoleId()));
				return u;
			} else {
				return null;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public User getUserByEmail(String email) {
		String sql = "SELECT * FROM kmdm_users WHERE email=?";
		
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, email);
			RoleDaoImpl rdi = new RoleDaoImpl();
			
			ResultSet rs = stmt.executeQuery();
			User u = new User();
			while (rs.next()) {
				u.setUserId(rs.getInt("user_id"));
				u.setUsername(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("user_pwd"));
				u.setFirstName(rs.getString("firstname"));
				u.setLastName(rs.getString("lastname"));
				u.setRoleId(rs.getInt("role"));
//				u.setRole(rdi.getRoleById(rs.getInt("role")));
			}
			u.setRole(rdi.getRoleById(u.getRoleId()));
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}
	}

	@Override
	public ArrayList<User> getUsersByRole(Role role) {
		String sql = "SELECT * FROM kmdm_users WHERE role_id=?";
		ArrayList<User> users = null;
		
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, role.getRoleId());
			ResultSet rs = stmt.executeQuery();
			users = new ArrayList<User>();
			RoleDaoImpl rdi = new RoleDaoImpl();

			while (rs.next()) {
				User u = new User();
				u.setUserId(rs.getInt("user_id"));
				u.setUsername(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("user_pwd"));
				u.setFirstName(rs.getString("firstname"));
				u.setLastName(rs.getString("lastname"));
				u.setRole(rdi.getRoleById(rs.getInt("role")));
				users.add(u);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeResources();
		}	
	}

	@Override
	public boolean addUser(User user) {
		String sql = "INSERT INTO kmdm_users (username, user_pwd, email, firstname, lastname, role) \n" +
				"VALUES (?, ?, ?, ?, ?, ?)";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getFirstName());
			stmt.setString(5, user.getLastName());
			stmt.setInt(6, user.getRole().getRoleId());
			
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
	public boolean updateUser(User user) {
		String sql = "UPDATE kmdm_users SET username=?, user_pwd=?, email=?, firstname=?, lastname=?, role=?\n" +
				"WHERE user_id=?";
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getFirstName());
			stmt.setString(5, user.getLastName());
			stmt.setInt(6, user.getRole().getRoleId());
			stmt.setInt(7, user.getUserId());

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
	public boolean deleteUserById(int id) {
		String sql = "DELETE FROM kmdm_users WHERE user_id=?";
		try {
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

	@Override
	public boolean deleteUserByUserName(String name) {
		String sql = "DELETE FROM kmdm_users WHERE username=?";
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
