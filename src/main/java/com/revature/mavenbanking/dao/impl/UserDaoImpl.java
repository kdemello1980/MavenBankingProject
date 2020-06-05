package com.revature.mavenbanking.dao.impl;
import com.revature.mavenbanking.dao.UserDao;

import java.util.List;

import com.revature.mavenbanking.dao.oracle.DAOUtilities;
import com.revature.mavenbanking.model.User;
import com.revature.mavenbanking.model.AccountStatus;
import com.revature.mavenbanking.model.Role;

public class UserDaoImpl implements UserDao {

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByUserName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersByRole(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersByStatus(AccountStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUserById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUserByUserName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

}
