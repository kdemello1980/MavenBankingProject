package com.kdemello.mavenbanking.dao;

import java.util.ArrayList;

import com.kdemello.mavenbanking.model.*;

public interface UserDao {
	public ArrayList<User> getAllUsers();
	public User getUserById(int id);
	public User getUserByUserName(String username);
	public User getUserByEmail(String email);
	public ArrayList<User> getUsersByRole(Role role);
	
	public boolean addUser(User user);
	public boolean updateUser(User user);
	public boolean deleteUserById(int id);
	public boolean deleteUserByUserName(String name);
}
