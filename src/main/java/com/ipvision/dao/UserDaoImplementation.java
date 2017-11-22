package com.ipvision.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.ipvision.domain.User;
import com.ipvision.domain.UserRole;

@Repository
public class UserDaoImplementation implements UserDao {

	@Autowired
	SessionFactory sessionFactory;
	
    Session session;
	
	protected Session getSession() {
	    return  sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean checkUserExist(User user) {
		
		boolean check = false;
		
		User userFromDb = null;
		try {
			Query query = (Query) getSession()
					.createQuery("FROM User u where u.userName=:name and u.password=:pass");

			query.setParameter("name", user.getUserName());
			query.setParameter("pass", user.getPassword());
			List<User> list = query.list();
			if (list.size() > 0) {
				check = true;
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		
		return check;
	}

	@Override
	public Set<UserRole> returnUserRoles(User user) {
        Set<UserRole> userRoles = new HashSet<UserRole>();
		User userFromDb = null;
		try {
			Query query = (Query) getSession()
					.createQuery("FROM User u where u.userName=:name and u.password=:pass");

			query.setParameter("name", user.getUserName());
			query.setParameter("pass", user.getPassword());
			List<User> list = query.list();
			if (list.size() > 0) {
				userFromDb = list.get(0);
				userRoles = userFromDb.getUserRoles();
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return userRoles;
	}

	@Override
	public User returnUser(User user) {
		
		User userFromDb = null;
		try {
			Query query = (Query) getSession()
					.createQuery("FROM User u where u.userName=:name and u.password=:pass");

			query.setParameter("name", user.getUserName());
			query.setParameter("pass", user.getPassword());
			List<User> list = query.list();
			if (list.size() > 0) {
				userFromDb = list.get(0);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		
		return userFromDb;
	}

	@Override
	public User returnUserById(String userId) {
		
		User usr = null;
		try {
			usr = (User) getSession().createCriteria(User.class)
					.add(Restrictions.eq("userId", Integer.parseInt(userId)))
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return usr;
	}

	@Override
	public List<User> returnAllUser(int startUser, int selectedUserPerPage) {
		
		List<User> users = new ArrayList<User>();
		try {
			Query query = (Query) getSession().createQuery("FROM User");
			query.setFirstResult(startUser);
			query.setMaxResults(selectedUserPerPage);
			users = query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return users;
	}

	@Override
	public int returnNumberOfUser() {
		
		Integer totalUsers = 0;
		try {
			Long count = ((Long) getSession().createQuery(
					"select count(*) from User").uniqueResult());
			totalUsers = count.intValue();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return totalUsers;
	}

	@Override
	public void saveUser(User user) throws Exception {
		
		Integer id = 0;
		try {
			id = (Integer) getSession().save(user);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
		
	}

	@Override
	public void updateUser(User user, String userId) throws Exception {
		
		try {
			User editUser = (User) getSession().get(User.class,
					Integer.parseInt(userId));
			editUser.setUserName(user.getUserName());
			editUser.setEmail(user.getEmail());
			editUser.setPassword(user.getPassword());
			editUser.setCountryName(user.getCountryName());
			getSession().update(editUser);

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;

		}
		
	}
	
	@Override
	public void deleteUser(User user) throws Exception {
		try {
			getSession().delete(user);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}

}
