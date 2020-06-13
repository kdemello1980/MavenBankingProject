package com.revature.mavenbanking.service;

import java.util.ArrayList;

import com.revature.mavenbanking.dao.impl.RoleDaoImpl;
import com.revature.mavenbanking.dao.impl.UserDaoImpl;
import com.revature.mavenbanking.exceptions.AddUserException;
import com.revature.mavenbanking.exceptions.RetrieveUserException;
import com.revature.mavenbanking.exceptions.UpdateUserException;
import com.revature.mavenbanking.model.Role;
import com.revature.mavenbanking.model.User;

public class UserService {

	private UserDaoImpl udi = new UserDaoImpl();
	
	/*
	 * Business logic. These throw exceptions.
	 */
	
	/*
	 * upgradeToPremium: verifies user's role is "Standard" and updates the user with the 
	 * "Premium" role.  Throws an exception if the user is not in the "Standard" role.
	 */
	public boolean upgradeToPremium (User user) throws UpdateUserException {
		if ( ! user.getRole().getRole().equals("Standard")){
			throw new UpdateUserException("Users of role " + user.getRole().getRole() + " are not eligible to upgrade to Premium");
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
	 * Login.  Need to flesh this out to use some sort of password hashing.
	 */
	public boolean login(String userName, String password) throws Exception {
		User user = this.getUserByUserName(userName);
		if (user.getPassword().equals(password))
			return true;
		else
			throw new Exception("Invalid password.");
	}
	
	/*
	 * DAO methods.   
	 */
	public boolean addUser(User user) throws AddUserException {
		if (udi.addUser(user)){
			return true;
		} else {
			throw new AddUserException("Failed to add user " + user.getUsername());
		}
	}
	
	public boolean deleteUserById(int id) throws UpdateUserException {
		if (udi.deleteUserById(id)){
			return true;
		} else {
			throw new UpdateUserException("Failed to delet userid" + id);
		}
	}
	
	public boolean deleteUserByUserName(String name) throws UpdateUserException {
		if (udi.deleteUserByUserName(name)){
			return true;
		} else {
			throw new UpdateUserException("Failed to delete user " + name);
		}
	}
	
	public ArrayList<User> getAllUsers() throws RetrieveUserException {
		ArrayList<User> list = udi.getAllUsers();
		if (list != null){
			return list;
		} else {
			throw new RetrieveUserException("Failed to get all users.");
		}
	}
	
	public User getUserByEmail(String email) throws RetrieveUserException {
		User user =  udi.getUserByEmail(email);
		if (user != null){
			return user;
		} else {
			throw new RetrieveUserException("Failed to retrieve user: " + email);
		}
	}
	
	public User getUserById(int id) throws RetrieveUserException {
		User user  = udi.getUserById(id);
		if (user != null)
			return user;
		else
			throw new RetrieveUserException("Failed to retrieve user: " + id);
	}
	
	public User getUserByUserName(String name) throws RetrieveUserException {
		User user = udi.getUserByUserName(name);
		if (user != null)
			return user;
		else
			throw new RetrieveUserException("Invalid username: " + name);
	}
	
	public ArrayList<User> getUsersByRole(Role role) throws RetrieveUserException {
		ArrayList<User> list = udi.getUsersByRole(role);
		if (list != null)
			return list;
		else
			throw new RetrieveUserException("Failed to retrieve users of role: " + role.getRole());
	}
	
	public boolean updateUser(User user) throws UpdateUserException {
		if (udi.updateUser(user))
			return true;
		else
			throw new UpdateUserException("Failed to update user " + user.getUsername());
	}
}
