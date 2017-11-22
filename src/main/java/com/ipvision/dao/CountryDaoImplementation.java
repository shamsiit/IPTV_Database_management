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

import com.ipvision.domain.Country;

@Repository
public class CountryDaoImplementation implements CountryDao {

	@Autowired
	SessionFactory sessionFactory;
	
    Session session;
	
	protected Session getSession() {
	    return  sessionFactory.getCurrentSession();
	}
	
	@Override
	public Country returnCountryById(String countryId) {
		
		Country ct = null;
		
        try {
            ct = (Country) getSession().createCriteria(Country.class)
                    .add( Restrictions.eq("countryId", Integer.parseInt(countryId)) ).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
                      
        }
		
		return ct;
	}

	@Override
	public List<Country> returnAllCountry(int startCountry,int selectedCountryPerPage) {
		
		List<Country> countries = new ArrayList<Country>();
		try {
			Query query = (Query) getSession().createQuery("FROM Country");
			query.setFirstResult(startCountry);
			query.setMaxResults(selectedCountryPerPage);
			countries = query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return countries;
	}
	
	@Override
	public List<Country> returnAllCountry() {
		
		List countries = new ArrayList<Country>();
		
		try {
			Criteria cr = getSession().createCriteria(Country.class)
				    .setProjection(Projections.projectionList() 
				      .add(Projections.property("countryId"), "countryId") 
				      .add(Projections.property("countryName"), "countryName")) 
				    .setResultTransformer(Transformers.aliasToBean(Country.class));
			countries = cr.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return countries;
	}

	@Override
	public int returnNumberOfCountry() {
		
		int totalCountry = 0;
		
		try {
			Long count = ((Long) getSession().createQuery(
					"select count(*) from Country").uniqueResult());
			totalCountry = count.intValue();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return totalCountry;
	}

	@Override
	public void saveCountry(Country country) throws Exception {
		Integer id=0;
		try {
			id = (Integer) getSession().save(country);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}

	@Override
	public void updateCountry(Country country, String countryId) throws Exception {
		
		try {
			Country editCountry = (Country) getSession().get(Country.class,
					Integer.parseInt(countryId));
			editCountry.setCountryName(country.getCountryName());
			getSession().update(editCountry);	
			
		} catch (HibernateException e) {
			throw e;
		}		
	}
	
	@Override
	public void deleteCountry(Country country) throws Exception {
		try {
			getSession().delete(country);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}

	

}
