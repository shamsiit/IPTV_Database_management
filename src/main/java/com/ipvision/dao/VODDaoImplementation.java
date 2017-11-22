package com.ipvision.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ipvision.domain.Category;
import com.ipvision.domain.Country;
import com.ipvision.domain.Language;
import com.ipvision.domain.LiveChannel;
import com.ipvision.domain.PrimaryTag;
import com.ipvision.domain.Tag;
import com.ipvision.domain.VOD;

@Repository
public class VODDaoImplementation implements VODDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	Session session;
	
	protected Session getSession() {
	    return  sessionFactory.getCurrentSession();
	}

	@Override
	public VOD returnVODById(String vodId) {
		
		VOD vd = null;			
		
        try {
            vd = (VOD) getSession().createCriteria(VOD.class).add( Restrictions.eq("videoId", Integer.parseInt(vodId)) ).uniqueResult();
           
        } catch (Exception e) {
            e.printStackTrace();
                        
        }
		
		return vd;
	}

	@Override
	public List<VOD> returnAllVOD(String pageNumber, int vodPerPage) {
		
		List<VOD> vods = new ArrayList<VOD>();
		
	      try{
	    	  int pageNo = Integer.parseInt(pageNumber);
	         Query query = (Query) getSession().createQuery("FROM VOD");
	         query.setFirstResult(pageNo);
	         query.setMaxResults(vodPerPage);
	         vods = query.list();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
		
		return vods;
	}
	
	@Override
	public String returnSingleVodsCategoryName(int vodId) {
		
       VOD vd = null;			
		
        try {
            vd = (VOD) getSession().createCriteria(VOD.class).add( Restrictions.eq("videoId", vodId) ).uniqueResult();
           
        } catch (Exception e) {
            e.printStackTrace();
                        
        }
		
		return vd.getCategory().getCategoryName();
	}
	
	@Override
	public int returnSingleVodsCategoryId(int vodId) {
		
        VOD vd = null;			
		
        try {
            vd = (VOD) getSession().createCriteria(VOD.class).add( Restrictions.eq("videoId", vodId) ).uniqueResult();
           
        } catch (Exception e) {
            e.printStackTrace();
                        
        }
		
		return vd.getCategory().getCategoryId();
	}
	
	@Override
	public String returnSingleVodsCountryName(int vodId) {
		
        VOD vd = null;			
		
        try {
            vd = (VOD) getSession().createCriteria(VOD.class).add( Restrictions.eq("videoId", vodId) ).uniqueResult();
           
        } catch (Exception e) {
            e.printStackTrace();
                        
        }
		
		return vd.getCountry().getCountryName();
	}

	@Override
	public int returnSingleVodsCountryId(int vodId) {
        VOD vd = null;			
		
        try {
            vd = (VOD) getSession().createCriteria(VOD.class).add( Restrictions.eq("videoId", vodId) ).uniqueResult();
           
        } catch (Exception e) {
            e.printStackTrace();
                        
        }
		
		return vd.getCountry().getCountryId();
	}

	@Override
	public String returnSingleVodsLanguageName(int vodId) {
       VOD vd = null;			
		
        try {
            vd = (VOD) getSession().createCriteria(VOD.class).add( Restrictions.eq("videoId", vodId) ).uniqueResult();
           
        } catch (Exception e) {
            e.printStackTrace();
                        
        }
		return vd.getLanguage().getLanguageName();
	}

	@Override
	public int returnSingleVodsLanguageId(int vodId) {
        VOD vd = null;			
		
        try {
            vd = (VOD) getSession().createCriteria(VOD.class).add( Restrictions.eq("videoId", vodId) ).uniqueResult();
           
        } catch (Exception e) {
            e.printStackTrace();
                        
        }
		return vd.getLanguage().getLanguageId();
	}

	
	@Override
	public List<VOD> returnAllVODByUserId(String pageNumber, int vodPerPage,
			int userId) {
		
		List<VOD> vods = new ArrayList<VOD>();
		
	      try{
	    	  int pageNo = Integer.parseInt(pageNumber);
	         Query query = (Query) getSession().createQuery("FROM VOD WHERE userId="+userId);
	         query.setFirstResult(pageNo);
	         query.setMaxResults(vodPerPage);
	         vods = query.list();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
		
		return vods;
	}

	@Override
	public int returnNumberOfVOD() {
		Integer totalVod = 0;
	      try{
	         Long count = ((Long) getSession().createQuery(
						"select count(*) from VOD").uniqueResult());
	         totalVod = count.intValue();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
		
		return totalVod;
	}
	
	@Override
	public int returnNumberOfVODByUserId(int userId) {
		
		Integer totalVod = 0;
	      try{
	         Long count = ((Long) getSession().createQuery(
						"select count(*) from VOD WHERE userId="+userId).uniqueResult());
	         totalVod = count.intValue();
	      }catch (HibernateException e) {;
	         e.printStackTrace(); 
	      }
		
		return totalVod;
	}

	@Override
	public void saveVOD(VOD vod) throws Exception {
		try {
			getSession().save(vod);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}

	}
	
	@Override
	public void deleteVOD(VOD vod) throws Exception {
		try {
			getSession().delete(vod);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}

	@Override
	public void updateVOD(VOD vod) throws Exception {
		
		try {
			getSession().update(vod);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
		
	}
	
	@Override
	public List<VOD> getLatestFiveVods() {
		List<VOD> vods = new ArrayList<VOD>();
		
	      try{
	         Query query = (Query) getSession().createQuery("FROM VOD ORDER BY videoId DESC");
	         query.setMaxResults(5);
	         vods = query.list();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
		return vods;
	}
	
	@Override
	public List<VOD> getSearchedVodList(String[] tagsArray) {
		List<VOD> vodList = new ArrayList<VOD>();

		try {
			for (int i = 0; i < tagsArray.length; i++) {
				String singleTag = "%" + tagsArray[i] + "%";
				Query primaryTagQuery = (Query) getSession().createQuery("FROM PrimaryTag PT WHERE PT.primaryTagName like :primaryTagName");
				List<PrimaryTag> primaryTags = primaryTagQuery.setParameter("primaryTagName", singleTag).list();
				if (primaryTags.size() > 0) {
					for(PrimaryTag pTag : primaryTags){
						Set<VOD> primaryTagVodSet = pTag.getVodSet();
						for (VOD vod : primaryTagVodSet) {
							if (!vodList.contains(vod)) {
								vodList.add(vod);
							}
						}
					}
					
				} 
			
					 Query secondaryTagQuery = (Query) getSession().createQuery("FROM Tag T WHERE T.tagName like :tagName");
					 List<Tag> tags = secondaryTagQuery.setParameter("tagName", singleTag).list();
					 if(tags.size() > 0){
						 for(Tag tag : tags){
							 Set<VOD> secondaryTagVodSet = tag.getVodSet();
								for (VOD vod : secondaryTagVodSet) {
									if (!vodList.contains(vod)) {
										vodList.add(vod);
									}
								}
						 }
						 
					 }

			}

		} catch (HibernateException ex) {
			
			ex.printStackTrace();
		}
		return vodList;
	}
	
	@Override
	public int getSearchedVodNumber(String[] tagsArray) {
		
		List<VOD> vodList = new ArrayList<VOD>();

		try {
			for (int i = 0; i < tagsArray.length; i++) {
				String singleTag = "%" + tagsArray[i] + "%";
				Query primaryTagQuery = (Query) getSession().createQuery("FROM PrimaryTag PT WHERE PT.primaryTagName like :primaryTagName");
				List<PrimaryTag> primaryTags = primaryTagQuery.setParameter("primaryTagName", singleTag).list();
				if (primaryTags.size() > 0) {
					for(PrimaryTag pTag : primaryTags){
						Set<VOD> primaryTagVodSet = pTag.getVodSet();
						for (VOD vod : primaryTagVodSet) {
							if (!vodList.contains(vod)) {
								vodList.add(vod);
							}
						}
					}
					
				} 
			
					 Query secondaryTagQuery = (Query) getSession().createQuery("FROM Tag T WHERE T.tagName like :tagName");
					 List<Tag> tags = secondaryTagQuery.setParameter("tagName", singleTag).list();
					 if(tags.size() > 0){
						 for(Tag tag : tags){
							 Set<VOD> secondaryTagVodSet = tag.getVodSet();
								for (VOD vod : secondaryTagVodSet) {
									if (!vodList.contains(vod)) {
										vodList.add(vod);
									}
								}
						 }
						 
					 }

			}

		} catch (HibernateException ex) {
			
			ex.printStackTrace();
		}
		
		int total = vodList.size();
		
		vodList = null;
		
		return total;
	}

	@Override
	public List<VOD> returnSearchedVodsByTag(String [] tagsArray,int start,int perPage) {
		
		List<VOD> vodList = new ArrayList<VOD>();
		
		List<Country> countryList = new ArrayList<Country>();
		
		List<Category> categoryList = new ArrayList<Category>();
		
		List<Language> languageList = new ArrayList<Language>();
		
		try {
			for (int i = 0; i < tagsArray.length; i++) {
				String singleTag = "%" + tagsArray[i] + "%";
				Query vodQuery = (Query) getSession().createQuery("FROM VOD V WHERE V.tag like :tagName OR V.videoName like :tagName");
				vodQuery.setFirstResult(start);
				vodQuery.setMaxResults(perPage);
				List<VOD> vodFromQuery = vodQuery.setParameter("tagName", singleTag).list();
				
					for(VOD vod : vodFromQuery){
						if (!vodList.contains(vod)) {
								vodList.add(vod);
						}
					}
			}
			
			for (int i = 0; i < tagsArray.length; i++) {
				String singleTag = "%" + tagsArray[i] + "%";
				Query countryQuery = (Query) getSession().createQuery("FROM Country C WHERE C.countryName like :tagName");
				List<Country> countryFromQuery = countryQuery.setParameter("tagName", singleTag).list();
				
					for(Country country : countryFromQuery){
						if (!countryList.contains(country)) {
							countryList.add(country);
						}
					}
			}
			
			for (int i = 0; i < tagsArray.length; i++) {
				String singleTag = "%" + tagsArray[i] + "%";
				Query categoryQuery = (Query) getSession().createQuery("FROM Category C WHERE C.categoryName like :tagName");
				List<Category> categoryFromQuery = categoryQuery.setParameter("tagName", singleTag).list();
				
					for(Category category : categoryFromQuery){
						if (!categoryList.contains(category)) {
							categoryList.add(category);
						}
					}
			}
			
			for (int i = 0; i < tagsArray.length; i++) {
				String singleTag = "%" + tagsArray[i] + "%";
				Query languageQuery = (Query) getSession().createQuery("FROM Language L WHERE L.languageName like :tagName");
				List<Language> languageFromQuery = languageQuery.setParameter("tagName", singleTag).list();
				
					for(Language language : languageFromQuery){
						if (!languageList.contains(language)) {
							languageList.add(language);
						}
					}
			}
			
			for(Country country : countryList){
				Criteria cr = getSession().createCriteria(VOD.class);
				cr.add(Restrictions.eq("country", country));
				cr.setFirstResult(start);
				cr.setMaxResults(perPage);
				List<VOD> vodFromQuery = cr.list();
				
				for(VOD vod : vodFromQuery){
					if (!vodList.contains(vod)) {
						vodList.add(vod);
					}
				}
			}
			
			for(Category category : categoryList){
				Criteria cr = getSession().createCriteria(VOD.class);
				cr.add(Restrictions.eq("category", category));
				cr.setFirstResult(start);
				cr.setMaxResults(perPage);
                List<VOD> vodFromQuery = cr.list();
				
				for(VOD vod : vodFromQuery){
					if (!vodList.contains(vod)) {
						vodList.add(vod);
					}
				}
			}
			
			for(Language language : languageList){
				Criteria cr = getSession().createCriteria(VOD.class);
				cr.add(Restrictions.eq("language", language));
				cr.setFirstResult(start);
				cr.setMaxResults(perPage);
                List<VOD> vodFromQuery = cr.list();
				
				for(VOD vod : vodFromQuery){
					if (!vodList.contains(vod)) {
						vodList.add(vod);
					}
				}
			}

		} catch (HibernateException ex) {
			
			ex.printStackTrace();
		}
		
		return vodList;
	}

	@Override
	public List<VOD> returnSearchedVodByFilter(String category,
			String country, String language, int start, int perPage) {
		List<VOD> list = new ArrayList<VOD>();
		
		if(!category.equals("") && !country.equals("") && !language.equals("")){
			
			String [] categoryIdArray = category.split(",");
			String [] countryIdArray = country.split(",");
			String [] languageIdArray = language.split(",");
			List categoryList = new ArrayList<Category>();
			List countryList = new ArrayList<Country>();
			List languageList = new ArrayList<Language>();
			
			for (int i = 0; i < categoryIdArray.length; i++) {
				categoryList.add((Category) getSession()
						.createCriteria(Category.class)
						.add(Restrictions.eq("categoryId",
								Integer.parseInt(categoryIdArray[i])))
						.uniqueResult());
			}
			
			for (int i = 0; i < countryIdArray.length; i++) {
				countryList.add((Country) getSession()
						.createCriteria(Country.class)
						.add(Restrictions.eq("countryId",
								Integer.parseInt(countryIdArray[i])))
						.uniqueResult());
			}
			
			for (int i = 0; i < languageIdArray.length; i++) {
				languageList.add((Language) getSession()
						.createCriteria(Language.class)
						.add(Restrictions.eq("languageId",
								Integer.parseInt(languageIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			
			Criterion categoryCriterion = Restrictions.in("category", categoryList.toArray());
			Criterion countryCriterion = Restrictions.in("country", countryList.toArray());
			Criterion languageCriterion = Restrictions.in("language", languageList.toArray());
			
			LogicalExpression exp1 = Restrictions.and(countryCriterion, categoryCriterion);
			LogicalExpression exp2 = Restrictions.and(categoryCriterion,languageCriterion);
			cr.setFirstResult(start);
			cr.setMaxResults(perPage);
			cr.add(exp1);
			cr.add(exp2);
			
			list = cr.list();
			
			
		}else if(category.equals("") && !country.equals("") && !language.equals("")){
			
			String [] countryIdArray = country.split(",");
			String [] languageIdArray = language.split(",");
			List countryList = new ArrayList<Country>();
			List languageList = new ArrayList<Language>();
			
			for (int i = 0; i < countryIdArray.length; i++) {
				countryList.add((Country) getSession()
						.createCriteria(Country.class)
						.add(Restrictions.eq("countryId",
								Integer.parseInt(countryIdArray[i])))
						.uniqueResult());
			}
			
			for (int i = 0; i < languageIdArray.length; i++) {
				languageList.add((Language) getSession()
						.createCriteria(Language.class)
						.add(Restrictions.eq("languageId",
								Integer.parseInt(languageIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			Criterion countryCriterion = Restrictions.in("country", countryList.toArray());
			Criterion languageCriterion = Restrictions.in("language", languageList.toArray());
			cr.setFirstResult(start);
			cr.setMaxResults(perPage);
			LogicalExpression exp1 = Restrictions.and(countryCriterion, languageCriterion);
			cr.add(exp1);
			
			list = cr.list();
			
		}else if(!category.equals("") && country.equals("") && !language.equals("")){
			
			String [] categoryIdArray = category.split(",");
			String [] languageIdArray = language.split(",");
			List categoryList = new ArrayList<Category>();
			List languageList = new ArrayList<Language>();
			
			for (int i = 0; i < categoryIdArray.length; i++) {
				categoryList.add((Category) getSession()
						.createCriteria(Category.class)
						.add(Restrictions.eq("categoryId",
								Integer.parseInt(categoryIdArray[i])))
						.uniqueResult());
			}
			
			for (int i = 0; i < languageIdArray.length; i++) {
				languageList.add((Language) getSession()
						.createCriteria(Language.class)
						.add(Restrictions.eq("languageId",
								Integer.parseInt(languageIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			
			Criterion categoryCriterion = Restrictions.in("category", categoryList.toArray());
			Criterion languageCriterion = Restrictions.in("language", languageList.toArray());
			
			LogicalExpression exp1 = Restrictions.and(categoryCriterion, languageCriterion);
			cr.setFirstResult(start);
			cr.setMaxResults(perPage);
			cr.add(exp1);
			
			list = cr.list();
			
		}else if(!category.equals("") && !country.equals("") && language.equals("")){
			
			String [] categoryIdArray = category.split(",");
			String [] countryIdArray = country.split(",");
			List categoryList = new ArrayList<Category>();
			List countryList = new ArrayList<Country>();
			
			for (int i = 0; i < categoryIdArray.length; i++) {
				categoryList.add((Category) getSession()
						.createCriteria(Category.class)
						.add(Restrictions.eq("categoryId",
								Integer.parseInt(categoryIdArray[i])))
						.uniqueResult());
			}
			
			for (int i = 0; i < countryIdArray.length; i++) {
				countryList.add((Country) getSession()
						.createCriteria(Country.class)
						.add(Restrictions.eq("countryId",
								Integer.parseInt(countryIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			
			Criterion categoryCriterion = Restrictions.in("category", categoryList.toArray());
			Criterion countryCriterion = Restrictions.in("country", countryList.toArray());
			
			LogicalExpression exp1 = Restrictions.and(countryCriterion, categoryCriterion);
			cr.setFirstResult(start);
			cr.setMaxResults(perPage);
			cr.add(exp1);
			
			list = cr.list();
			
		}else if(category.equals("") && country.equals("") && !language.equals("")){
			;
			String [] languageIdArray = language.split(",");
			List languageList = new ArrayList<Language>();
			
			for (int i = 0; i < languageIdArray.length; i++) {
				languageList.add((Language) getSession()
						.createCriteria(Language.class)
						.add(Restrictions.eq("languageId",
								Integer.parseInt(languageIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			
			Criterion languageCriterion = Restrictions.in("language", languageList.toArray());
			cr.setFirstResult(start);
			cr.setMaxResults(perPage);
			cr.add(languageCriterion);
			
			list = cr.list();
			
		}else if(!category.equals("") && country.equals("") && language.equals("")){
			
			String [] categoryIdArray = category.split(",");
			List categoryList = new ArrayList<Category>();
			
			for (int i = 0; i < categoryIdArray.length; i++) {
				categoryList.add((Category) getSession()
						.createCriteria(Category.class)
						.add(Restrictions.eq("categoryId",
								Integer.parseInt(categoryIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			
			Criterion categoryCriterion = Restrictions.in("category", categoryList.toArray());
			cr.setFirstResult(start);
			cr.setMaxResults(perPage);
			cr.add(categoryCriterion);
			
			list = cr.list();
			
		}else if(category.equals("") && !country.equals("") && language.equals("")){
			
			String [] countryIdArray = country.split(",");
			List countryList = new ArrayList<Country>();
			
			for (int i = 0; i < countryIdArray.length; i++) {
				countryList.add((Country) getSession()
						.createCriteria(Country.class)
						.add(Restrictions.eq("countryId",
								Integer.parseInt(countryIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			
			Criterion countryCriterion = Restrictions.in("country", countryList.toArray());
			cr.setFirstResult(start);
			cr.setMaxResults(perPage);
			cr.add(countryCriterion);
			
			list = cr.list();
			
		}else if(category.equals("") && country.equals("") && language.equals("")){
			
		}
		
		return list;
	}

	@Override
	public int returnTotalSearchedVodByFilter(String category, String country,
			String language) {
        List<VOD> list = new ArrayList<VOD>();
        
        int totalVod = 0;
		
		if(!category.equals("") && !country.equals("") && !language.equals("")){
			
			String [] categoryIdArray = category.split(",");
			String [] countryIdArray = country.split(",");
			String [] languageIdArray = language.split(",");
			List categoryList = new ArrayList<Category>();
			List countryList = new ArrayList<Country>();
			List languageList = new ArrayList<Language>();
			
			for (int i = 0; i < categoryIdArray.length; i++) {
				categoryList.add((Category) getSession()
						.createCriteria(Category.class)
						.add(Restrictions.eq("categoryId",
								Integer.parseInt(categoryIdArray[i])))
						.uniqueResult());
			}
			
			for (int i = 0; i < countryIdArray.length; i++) {
				countryList.add((Country) getSession()
						.createCriteria(Country.class)
						.add(Restrictions.eq("countryId",
								Integer.parseInt(countryIdArray[i])))
						.uniqueResult());
			}
			
			for (int i = 0; i < languageIdArray.length; i++) {
				languageList.add((Language) getSession()
						.createCriteria(Language.class)
						.add(Restrictions.eq("languageId",
								Integer.parseInt(languageIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			
			Criterion categoryCriterion = Restrictions.in("category", categoryList.toArray());
			Criterion countryCriterion = Restrictions.in("country", countryList.toArray());
			Criterion languageCriterion = Restrictions.in("language", languageList.toArray());
			
			LogicalExpression exp1 = Restrictions.and(countryCriterion, categoryCriterion);
			LogicalExpression exp2 = Restrictions.and(categoryCriterion,languageCriterion);
			cr.add(exp1);
			cr.add(exp2);
			cr.setProjection(Projections.rowCount());
			list = cr.list();
			try{
				totalVod = Integer.parseInt(""+list.get(0));
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}else if(category.equals("") && !country.equals("") && !language.equals("")){
			
			String [] countryIdArray = country.split(",");
			String [] languageIdArray = language.split(",");
			List countryList = new ArrayList<Country>();
			List languageList = new ArrayList<Language>();
			
			for (int i = 0; i < countryIdArray.length; i++) {
				countryList.add((Country) getSession()
						.createCriteria(Country.class)
						.add(Restrictions.eq("countryId",
								Integer.parseInt(countryIdArray[i])))
						.uniqueResult());
			}
			
			for (int i = 0; i < languageIdArray.length; i++) {
				languageList.add((Language) getSession()
						.createCriteria(Language.class)
						.add(Restrictions.eq("languageId",
								Integer.parseInt(languageIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			Criterion countryCriterion = Restrictions.in("country", countryList.toArray());
			Criterion languageCriterion = Restrictions.in("language", languageList.toArray());
			LogicalExpression exp1 = Restrictions.and(countryCriterion, languageCriterion);
			cr.add(exp1);
			cr.setProjection(Projections.rowCount());
			list = cr.list();
			try{
				totalVod = Integer.parseInt(""+list.get(0));
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(!category.equals("") && country.equals("") && !language.equals("")){
			
			String [] categoryIdArray = category.split(",");
			String [] languageIdArray = language.split(",");
			List categoryList = new ArrayList<Category>();
			List languageList = new ArrayList<Language>();
			
			for (int i = 0; i < categoryIdArray.length; i++) {
				categoryList.add((Category) getSession()
						.createCriteria(Category.class)
						.add(Restrictions.eq("categoryId",
								Integer.parseInt(categoryIdArray[i])))
						.uniqueResult());
			}
			
			for (int i = 0; i < languageIdArray.length; i++) {
				languageList.add((Language) getSession()
						.createCriteria(Language.class)
						.add(Restrictions.eq("languageId",
								Integer.parseInt(languageIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			
			Criterion categoryCriterion = Restrictions.in("category", categoryList.toArray());
			Criterion languageCriterion = Restrictions.in("language", languageList.toArray());
			
			LogicalExpression exp1 = Restrictions.and(categoryCriterion, languageCriterion);
			cr.add(exp1);
			cr.setProjection(Projections.rowCount());
			list = cr.list();
			try{
				totalVod = Integer.parseInt(""+list.get(0));
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(!category.equals("") && !country.equals("") && language.equals("")){
			
			String [] categoryIdArray = category.split(",");
			String [] countryIdArray = country.split(",");
			List categoryList = new ArrayList<Category>();
			List countryList = new ArrayList<Country>();
			
			for (int i = 0; i < categoryIdArray.length; i++) {
				categoryList.add((Category) getSession()
						.createCriteria(Category.class)
						.add(Restrictions.eq("categoryId",
								Integer.parseInt(categoryIdArray[i])))
						.uniqueResult());
			}
			
			for (int i = 0; i < countryIdArray.length; i++) {
				countryList.add((Country) getSession()
						.createCriteria(Country.class)
						.add(Restrictions.eq("countryId",
								Integer.parseInt(countryIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			
			Criterion categoryCriterion = Restrictions.in("category", categoryList.toArray());
			Criterion countryCriterion = Restrictions.in("country", countryList.toArray());
			
			LogicalExpression exp1 = Restrictions.and(countryCriterion, categoryCriterion);
			cr.add(exp1);
			cr.setProjection(Projections.rowCount());
			list = cr.list();
			try{
				totalVod = Integer.parseInt(""+list.get(0));
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(category.equals("") && country.equals("") && !language.equals("")){
			;
			String [] languageIdArray = language.split(",");
			List languageList = new ArrayList<Language>();
			
			for (int i = 0; i < languageIdArray.length; i++) {
				languageList.add((Language) getSession()
						.createCriteria(Language.class)
						.add(Restrictions.eq("languageId",
								Integer.parseInt(languageIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			
			Criterion languageCriterion = Restrictions.in("language", languageList.toArray());
			cr.add(languageCriterion);
			cr.setProjection(Projections.rowCount());
			list = cr.list();
			try{
				totalVod = Integer.parseInt(""+list.get(0));
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(!category.equals("") && country.equals("") && language.equals("")){
			
			String [] categoryIdArray = category.split(",");
			List categoryList = new ArrayList<Category>();
			
			for (int i = 0; i < categoryIdArray.length; i++) {
				categoryList.add((Category) getSession()
						.createCriteria(Category.class)
						.add(Restrictions.eq("categoryId",
								Integer.parseInt(categoryIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			
			Criterion categoryCriterion = Restrictions.in("category", categoryList.toArray());
			cr.add(categoryCriterion);
			cr.setProjection(Projections.rowCount());
			list = cr.list();
			try{
				totalVod = Integer.parseInt(""+list.get(0));
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(category.equals("") && !country.equals("") && language.equals("")){
			
			String [] countryIdArray = country.split(",");
			List countryList = new ArrayList<Country>();
			
			for (int i = 0; i < countryIdArray.length; i++) {
				countryList.add((Country) getSession()
						.createCriteria(Country.class)
						.add(Restrictions.eq("countryId",
								Integer.parseInt(countryIdArray[i])))
						.uniqueResult());
			}
			
			Criteria cr = getSession().createCriteria(VOD.class);
			
			Criterion countryCriterion = Restrictions.in("country", countryList.toArray());
			cr.add(countryCriterion);
			cr.setProjection(Projections.rowCount());
			list = cr.list();
			try{
				totalVod = Integer.parseInt(""+list.get(0));
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(category.equals("") && country.equals("") && language.equals("")){
			totalVod = 0;
		}
		
		return totalVod; 
	}


}