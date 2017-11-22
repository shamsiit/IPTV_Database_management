package com.ipvision.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ipvision.domain.Category;
import com.ipvision.domain.Language;

@Repository
public class CategoryDaoImplementation implements CategoryDao {

	@Autowired
	SessionFactory sessionFactory;
	
    Session session;
	
	protected Session getSession() {
	    return  sessionFactory.getCurrentSession();
	}
	
	@Override
	public Category returnCategoryById(String categoryId) {
		
		Category cat = null;
		try {
			cat = (Category) getSession()
					.createCriteria(Category.class)
					.add(Restrictions.eq("categoryId", Integer.parseInt(categoryId)))
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return cat;
	}
	
	@Override
	public List<Category> returnAllCategory(int startCategory,
			int selectedCategoryPerPage) {
		
		List<Category> categories = new ArrayList<Category>();
		try {
			Query query = (Query) getSession().createQuery("FROM Category");
			query.setFirstResult(startCategory);
			query.setMaxResults(selectedCategoryPerPage);
			categories = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		
		return categories;
	}
	
	@Override
	public List<Category> returnAllCategory() {
		
		List categories = new ArrayList<Category>();
		
	      try{
	    	  Criteria cr = getSession().createCriteria(Category.class)
					    .setProjection(Projections.projectionList() 
					      .add(Projections.property("categoryId"), "categoryId") 
					      .add(Projections.property("categoryName"), "categoryName")) 
					    .setResultTransformer(Transformers.aliasToBean(Category.class));
	    	  categories = cr.list();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
		
		return categories;
	}

	@Override
	public int returnNumberOfCategory() {
		
		Integer totalCategory = 0;
		try {		
			Long count = ((Long) getSession().createQuery(
					"select count(*) from Category").uniqueResult());
			totalCategory = count.intValue();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return totalCategory;
	}

	@Override
	public void saveCategory(Category category) throws Exception {
		
		Integer id = 0;
		try {
			id = (Integer) getSession().save(category);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
		
	}

	@Override
	public void updateCategory(Category category, String categoryId)
			throws Exception {
		
		try {
			Category editCategory = (Category) getSession().get(
					Category.class, Integer.parseInt(categoryId));
			editCategory.setCategoryName(category.getCategoryName());
			getSession().update(editCategory);

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;

		}
		
	}
	
	@Override
	public void deleteCategory(Category category) throws Exception {
		try {
			getSession().delete(category);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}



}
