package com.revature.mavenbanking.dao;

import java.util.List;

import com.revature.mavenbanking.model.*;

public interface AbstractUserDao {
	public List<User> getAllUsers();
	public User getUserById(int id);
	public User getUserByUserName(String username);
	public User getUserByEmail(String email);
	public List<User> getUsersByRole(Role role);
	public List<User> getUsersByStatus(AccountStatus status);
	
	public boolean addUser(User user);
	public boolean updateUser(User user);
	public boolean deleteUserById(String id);
	public boolean deleteUserByUserName(String name);
}
