package com.revature.mavenbanking.service;

import java.util.ArrayList;

import com.revature.mavenbanking.dao.impl.RoleDaoImpl;
import com.revature.mavenbanking.dao.impl.UserDaoImpl;
import com.revature.mavenbanking.model.Role;
import com.revature.mavenbanking.model.User;

public class UserService {

	private UserDaoImpl udi = new UserDaoImpl();
	
	/*
	 * Business logic.
	 */
	
	/*
	 * upgradeToPremium: verifies user's role is "Standard" and updates the user with the 
	 * "Premium" role.  Throws an exception if the user is not in the "Standard" role.
	 */
	public boolean upgradeToPremium (User user) throws Exception {
		if ( ! user.getRole().getRole().equals("Standard")){
			throw new Exception("Users of role " + user.getRole().getRole() + " are not eligible to upgrade to Premium");
		} else {
			Role role = new RoleDaoImpl().getRoleByName("Premium");
			user.setRole(role);
			if(udi.updateUser(user)) 
				return true;
			else
				return false;
		}
	}
	
	/*
	 * DAO methods.  
	 * 
	 * Not sure if I should add exceptions to these.
	 */
	public boolean addUser(User user) throws Exception {
		if (udi.addUser(user)){
			return true;
		} else {
			throw new Exception("Failed to add user " + user.getUsername());
		}
	}
	
	public boolean deleteUserById(int id) throws Exception {
		if (udi.deleteUserById(id)){
			return true;
		} else {
			throw new Exception("Failed to delet userid" + id);
		}
	}
	
	public boolean deleteUserByUserName(String name) throws Exception {
		if (udi.deleteUserByUserName(name)){
			return true;
		} else {
			throw new Exception("Failed to delete user " + name);
		}
	}
	
	public ArrayList<User> getAllUsers() throws Exception {
		ArrayList<User> list = udi.getAllUsers();
		if (list != null){
			return list;
		} else {
			throw new Exception("Failed to get all users.");
		}
	}
	
	public User getUserByEmail(String email) throws Exception {
		User user =  udi.getUserByEmail(email);
		if (user != null){
			return user;
		} else {
			throw new Exception("Failed to retrieve user: " + email);
		}
	}
	
	public User getUserById(int id) throws Exception {
		User user  = udi.getUserById(id);
		if (user != null)
			return user;
		else
			throw new Exception("Failed to retrieve user: " + id);
	}
	
	public User getUserByUserName(String name) throws Exception {
		User user = udi.getUserByUserName(name);
		if (user != null)
			return user;
		else
			throw new Exception("Failed to retrieve user " + name);
	}
	
	public ArrayList<User> getUsersByRole(Role role) throws Exception {
		ArrayList<User> list = udi.getUsersByRole(role);
		if (list != null)
			return list;
		else
			throw new Exception("Failed to retrieve users of role: " + role.getRole());
	}
	
	public boolean updateUser(User user) throws Exception {
		if (udi.updateUser(user))
			return true;
		else
			throw new Exception("Failed to update user " + user.getUsername());
	}
}
