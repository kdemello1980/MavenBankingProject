package com.revature.mavenbanking.service;

import java.util.ArrayList;

import com.revature.mavenbanking.dao.impl.UserDaoImpl;
import com.revature.mavenbanking.model.Role;
import com.revature.mavenbanking.model.User;

public class UserService {

	private UserDaoImpl udi = new UserDaoImpl();
	
	public boolean addUser(User user){
		return udi.addUser(user);
	}
	
	public boolean deleteUserById(int id){
		return udi.deleteUserById(id);
	}
	
	public boolean deleteUserByUserName(String name){
		return udi.deleteUserByUserName(name);
	}
	
	public ArrayList<User> getAllUsers(){
		return udi.getAllUsers();
	}
	
	public User getUserByEmail(String email){
		return udi.getUserByEmail(email);
	}
	
	public User getUserById(int id){
		return udi.getUserById(id);
	}
	
	public User getUserByUserName(String name){
		return udi.getUserByUserName(name);
	}
	
	public ArrayList<User> getUsersByRole(Role role){
		return udi.getUsersByRole(role);
	}
	
	public boolean updateUser(User user){
		return udi.updateUser(user);
	}
}
