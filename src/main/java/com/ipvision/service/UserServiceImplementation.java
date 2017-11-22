package com.ipvision.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipvision.dao.UserDao;
import com.ipvision.domain.User;
import com.ipvision.domain.UserRole;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	@Transactional
	public boolean checkUserExist(User user) {
		return userDao.checkUserExist(user);
	}

	@Override
	@Transactional
	public Set<UserRole> returnUserRoles(User user) {
		return userDao.returnUserRoles(user);
	}

	@Override
	@Transactional
	public User returnUser(User user) {
		return userDao.returnUser(user);
	}

	@Override
	@Transactional
	public User returnUserById(String userId) {
		
		return userDao.returnUserById(userId);
	}

	@Override
	@Transactional
	public List<User> returnAllUser(int startUser, int selectedUserPerPage) {
		
		return userDao.returnAllUser(startUser, selectedUserPerPage);
	}

	@Override
	@Transactional
	public int returnNumberOfUser() {
		
		return userDao.returnNumberOfUser();
	}

	@Override
	@Transactional
	public void saveUser(User user) throws Exception {
		
		userDao.saveUser(user);
		
	}

	@Override
	@Transactional
	public void updateUser(User user, String userId) throws Exception {
		
		userDao.updateUser(user, userId);
		
	}
	
	@Override
	@Transactional
	public void deleteUser(User user) throws Exception {
		userDao.deleteUser(user);
		
	}

}
