package com.revature.mavenbanking.dao;

import java.util.List;

import com.revature.mavenbanking.model.*;

public interface AbstractUserDao {
	public List<AbstractUser> getAllUsers();
	public AbstractUser getUserById(int id);
	public AbstractUser getUserByUserName(String username);
	public AbstractUser getUserByEmail(String email);
	public List<AbstractUser> getUsersByRole(Role role);
	public List<AbstractUser> getUsersByStatus(AccountStatus status);
	
	public boolean addUser(AbstractUser user);
	public boolean updateUser(AbstractUser user);
	public boolean deleteUserById(String id);
	public boolean deleteUserByUserName(String name);
}
