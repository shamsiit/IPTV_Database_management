package com.ipvision.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ipvision.domain.Role;


@Repository
public class RoleDaoImplementation implements RoleDao {

	@Autowired
	SessionFactory sessionFactory;
	
    Session session;
	
	protected Session getSession() {
	    return  sessionFactory.getCurrentSession();
	}
	
	@Override
	public Role returnRoleById(String roleId) {
		
		Role rl = null;
        try {
            rl = (Role) getSession().createCriteria(Role.class)
                    .add( Restrictions.eq("roleId", Integer.parseInt(roleId)) ).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();         
        }
		
		return rl;
	}

	@Override
	public List<Role> returnAllRole(String pageNumber, int rolePerPage) {
		
		List<Role> roles = new ArrayList<Role>();
		int pageNo = Integer.parseInt(pageNumber);
		try {
			Query query = (Query) getSession().createQuery("FROM Role");
			query.setFirstResult((pageNo - 1) * 10);
			query.setMaxResults(rolePerPage);
			roles = query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return roles;
	}
	
	@Override
	public List<Role> returnAllRole() {
		
		List roles = new ArrayList<Role>();
		
		try {
			roles = getSession().createQuery("FROM Role").list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return roles;
	}

	@Override
	public int returnNumberOfRole() {
		
		Integer totalRole = 0;
		try {
			Long count = ((Long) getSession().createQuery(
					"select count(*) from Role").uniqueResult());
			totalRole = count.intValue();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return totalRole;
	}

	@Override
	public void saveRole(Role role) throws Exception {
		
		Integer id=0;
		try {
			id = (Integer) getSession().save(role);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
		
	}

	@Override
	public void updateRole(Role role, String roleId) throws Exception {
		
		try {
			Role editRole = (Role) getSession().get(Role.class,
					Integer.parseInt(roleId));
			editRole.setRoleName(role.getRoleName());
			getSession().update(editRole);
			
			
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
			
		}
		
	}

	@Override
	public void deleteRole(Role role) throws Exception {
		try {
			getSession().delete(role);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}

}
