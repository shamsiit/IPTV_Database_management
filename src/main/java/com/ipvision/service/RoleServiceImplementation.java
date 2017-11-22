package com.ipvision.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipvision.dao.RoleDao;
import com.ipvision.domain.Role;

@Service
public class RoleServiceImplementation implements RoleService {

	@Autowired
	RoleDao roleDao;
	
	@Override
	@Transactional
	public Role returnRoleById(String roleId) {
		
		return roleDao.returnRoleById(roleId);
	}

	@Override
	@Transactional
	public List<Role> returnAllRole(String pageNumber, int rolePerPage) {
		
		return roleDao.returnAllRole(pageNumber, rolePerPage);
	}
	
	@Override
	@Transactional
	public List<Role> returnAllRole() {
		
		return roleDao.returnAllRole();
	}

	@Override
	@Transactional
	public int returnNumberOfRole() {
		
		return roleDao.returnNumberOfRole();
	}

	@Override
	@Transactional
	public void saveRole(Role role) throws Exception {
		
		roleDao.saveRole(role);
		
	}

	@Override
	@Transactional
	public void updateRole(Role role, String roleId) throws Exception {
		
		roleDao.updateRole(role, roleId);
		
	}
	
	@Override
	@Transactional
	public void deleteRole(Role role) throws Exception {
		roleDao.deleteRole(role);
		
	}


}
