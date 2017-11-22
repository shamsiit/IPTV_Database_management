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
import com.ipvision.domain.Language;

@Repository
public class LanguageDaoImplementation implements LanguageDao {

	@Autowired
	SessionFactory sessionFactory;
	
    Session session;
	
	protected Session getSession() {
	    return  sessionFactory.getCurrentSession();
	}
	
	@Override
	public Language returnLanguageById(String languageId) {
		
		Language ln = null;
        try {
            ln = (Language) getSession().createCriteria(Language.class)
                    .add( Restrictions.eq("languageId", Integer.parseInt(languageId)) ).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();            
        }
		
		return ln;
	}

	@Override
	public List<Language> returnAllLanguage(int startLanguage,int selectedLanguagePerPage) {
		
		List<Language> languages = new ArrayList<Language>();
		try {
			Query query = (Query) getSession().createQuery("FROM Language");
			query.setFirstResult(startLanguage);
			query.setMaxResults(selectedLanguagePerPage);
			languages = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return languages;
	}
	
	@Override
	public List<Language> returnAllLanguage() {
		
		List languages = new ArrayList<Language>();
	      try{
	    	  Criteria cr = getSession().createCriteria(Language.class)
					    .setProjection(Projections.projectionList() 
					      .add(Projections.property("languageId"), "languageId") 
					      .add(Projections.property("languageName"), "languageName")) 
					    .setResultTransformer(Transformers.aliasToBean(Language.class));
	    	  languages = cr.list();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
		
		return languages;
	}

	@Override
	public int returnNumberOfLanguage() {
		
		int totalLanguage = 0;
		try {
			Long count = ((Long) getSession().createQuery(
					"select count(*) from Language").uniqueResult());
			totalLanguage = count.intValue();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return totalLanguage;
	}

	@Override
	public void saveLanguage(Language language) throws Exception {
		
		Integer id=0;
		try {
			id = (Integer) getSession().save(language);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
		
	}

	@Override
	public void updateLanguage(Language language, String languageId)
			throws Exception {
		
		try {
			Language editLanguage = (Language) getSession().get(Language.class,
					Integer.parseInt(languageId));
			editLanguage.setLanguageName(language.getLanguageName());
			getSession().update(editLanguage);
	
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
			
		}
		
	}

	
	@Override
	public void deleteLanguage(Language language) throws Exception {
		
		try {
			getSession().delete(language);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}
	

}
