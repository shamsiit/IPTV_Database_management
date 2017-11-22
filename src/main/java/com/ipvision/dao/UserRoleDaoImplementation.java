package com.ipvision.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ipvision.domain.UserRole;

@Repository
public class UserRoleDaoImplementation implements UserRoleDao {

	@Autowired
	SessionFactory sessionFactory;
	
    Session session;
	
	protected Session getSession() {
	    return  sessionFactory.getCurrentSession();
	}

	@Override
	public List<UserRole> returnUserRolesListByUserId(int userId) {
		
		List rolesList = new ArrayList<UserRole>();
	      try{
	         rolesList = getSession().createQuery("FROM UserRole WHERE userId="+userId).list();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
	      
		return rolesList;
	}

	@Override
	public void saveUserRole(UserRole userRole) throws Exception {
		
		try {
			Integer id = (Integer) getSession().save(userRole);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
		
	}

	@Override
	public void deleteUserRoleByUserRoleId(int userRoleId) {
		
	      try{
	         UserRole role = (UserRole)getSession().get(UserRole.class, userRoleId); 
	         getSession().delete(role);
	      }catch (HibernateException e) {
	         e.printStackTrace();;
	      }
		
	}

	
}
