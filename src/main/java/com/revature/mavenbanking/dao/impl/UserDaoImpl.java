package com.revature.mavenbanking.dao.impl;
import com.revature.mavenbanking.dao.UserDao;

import java.util.List;

import com.revature.mavenbanking.dao.oracle.DAOUtilities;
import com.revature.mavenbanking.model.AbstractUser;
import com.revature.mavenbanking.model.AccountStatus;
import com.revature.mavenbanking.model.Role;

public class UserDaoImpl implements UserDao {

	@Override
	public List<AbstractUser> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractUser getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractUser getUserByUserName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractUser getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AbstractUser> getUsersByRole(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AbstractUser> getUsersByStatus(AccountStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addUser(AbstractUser user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(AbstractUser user) {
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
