package com.kdemello.mavenbanking.service;

import java.util.ArrayList;

import com.kdemello.mavenbanking.dao.impl.RoleDaoImpl;
import com.kdemello.mavenbanking.dao.impl.UserDaoImpl;
import com.kdemello.mavenbanking.exceptions.AddUserException;
import com.kdemello.mavenbanking.exceptions.RetrievePermissionException;
import com.kdemello.mavenbanking.exceptions.RetrieveUserException;
import com.kdemello.mavenbanking.exceptions.UpdateUserException;
import com.kdemello.mavenbanking.model.Permission;
import com.kdemello.mavenbanking.model.Role;
import com.kdemello.mavenbanking.model.User;

public class UserService {

	private UserDaoImpl udi = new UserDaoImpl();
	private PermissionService pService = new PermissionService();
	
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
	public User login(String userName, String password) throws RetrieveUserException {
		User user = this.getUserByUserName(userName);
		if (user == null) {
			// this shouldn't happen, but I'm throwing the exception I would be getting
			// upstream instead of catching it.
			throw new RetrieveUserException("Login failed -- Invalid user name.");
		}
		if (user.getPassword().equals(password)){
			user.setPassword("ah ah ah. no peeking");
			return user;
		}
		else{
			throw new RetrieveUserException("Login failed.");
		}
	}
	
	/**
	 * Test the Permissions associated with a User-contained Role object.
	 * 
	 * 
	 * @param User user, String permission
	 * @return boolean true if the user has the permission, false otherwise.
	 */
	public boolean hasPermission(User user, String permission) {
		boolean hasPermission = false;
		try {
			Permission addUserPermission = pService.getPermissionByPermissionName(permission);
			for (Permission p : user.getRole().getEffectivePermissions()) {
				if (p.getPermissionName().equals(addUserPermission.getPermissionName())){
					hasPermission = true;
				}
			}
		} catch (RetrievePermissionException e) {
			e.printStackTrace();
		}
		return hasPermission;
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
