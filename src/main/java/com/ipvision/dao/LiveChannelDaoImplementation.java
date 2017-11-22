package com.ipvision.dao;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ipvision.domain.Category;
import com.ipvision.domain.Country;
import com.ipvision.domain.Language;
import com.ipvision.domain.LiveChannel;
import com.ipvision.domain.PrimaryTag;
import com.ipvision.domain.Tag;
import com.ipvision.domain.User;
import com.ipvision.domain.VOD;

@Repository
public class LiveChannelDaoImplementation implements LiveChannelDao {

	@Autowired
	SessionFactory sessionFactory;

    Session session;
	
	protected Session getSession() {
	    return  sessionFactory.getCurrentSession();
	}
	
	@Override
	public LiveChannel returnLiveChannelById(String liveChannelId) {
		
		LiveChannel ch = null;
		
		
		//Session dbsession = sessionFactory.getCurrentSession();
		//dbsession.beginTransaction();
        try {
            ch = (LiveChannel) getSession().createCriteria(LiveChannel.class).add( Restrictions.eq("channelId", Integer.parseInt(liveChannelId)) ).uniqueResult();         
            
//            Criteria cr = dbsession.createCriteria(LiveChannel.class).add(Restrictions.eq("channelId",Integer.parseInt(liveChannelId)))
//            		.setProjection(Projections.projectionList()
//            		.add(Projections.property("channelId"), "channelId")
//            		.add(Projections.property("channelName"), "channelName")
//            		.add(Projections.property("tag"), "tag"))
//            		.setResultTransformer(Transformers.aliasToBean(LiveChannel.class));
//            		List<LiveChannel> list = cr.list();
//            		System.out.println("list.size():" +list.size());
//            		ch = list.get(0);
//            		for(LiveChannel chnl : list){
//            			if(chnl.getChannelId() == Integer.parseInt(liveChannelId)){
//            				ch = chnl;
//            				break;
//            			}
//            		}
   //         dbsession.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();     
        } 
        
		return ch;
	}

	@Override
	public List<LiveChannel> returnAllLiveChannel(int startChannel,int selectedLiveChannelPerPage) {
		
		List<LiveChannel> channels = new ArrayList<LiveChannel>();
		
	      try{
	         Query query = (Query) getSession().createQuery("FROM LiveChannel");
	         query.setFirstResult(startChannel);
	         query.setMaxResults(selectedLiveChannelPerPage);
	         channels = query.list();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
		
		return channels;
	}
	
	@Override
	public List<LiveChannel> returnAllLiveChannelByUserId(int startChannel,
			int liveChannelPerPage, int userId) {
		
		List<LiveChannel> channels = new ArrayList<LiveChannel>();
	      try{
	         Query query = (Query) getSession().createQuery("FROM LiveChannel WHERE userId="+userId);
	         query.setFirstResult(startChannel);
	         query.setMaxResults(liveChannelPerPage);
	         channels = query.list();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
		
		return channels;
	}
	
	@Override
	public List<LiveChannel> returnUpChannelsByUserId(int userId) {
		
		List<LiveChannel> channels = new ArrayList<LiveChannel>();
		
	      try{
	         Query query = (Query) getSession().createQuery("FROM LiveChannel L WHERE L.user.userId="+userId+" AND L.state='up'");
	         channels = query.list();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
		
		return channels;
	}

	@Override
	public List<LiveChannel> returnDownChannelsByUserId(int userId) {
		
		List<LiveChannel> channels = new ArrayList<LiveChannel>();
		
	      try{
	         Query query = (Query) getSession().createQuery("FROM LiveChannel L WHERE L.user.userId="+userId+" AND L.state='down'");
	         channels = query.list();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
		
		return channels;
	}
	
	@Override
	public int returnSingleChannelsCountryId(int channelId) {
		
        LiveChannel channel = null;			
		
        try {
            channel = (LiveChannel) getSession().createCriteria(LiveChannel.class).add( Restrictions.eq("channelId", channelId) ).uniqueResult();
           
        } catch (Exception e) {
            e.printStackTrace();
                        
        }
		
		return channel.getCountry().getCountryId();
	}

	@Override
	public String returnSingleChannelsCountryName(int channelId) {
        LiveChannel channel = null;			
		
        try {
            channel = (LiveChannel) getSession().createCriteria(LiveChannel.class).add( Restrictions.eq("channelId", channelId) ).uniqueResult();
           
        } catch (Exception e) {
            e.printStackTrace();
                        
        }
		
		return channel.getCountry().getCountryName();
	}

	@Override
	public int returnSingleChannelsCategoryId(int channelId) {
        LiveChannel channel = null;			
		
        try {
            channel = (LiveChannel) getSession().createCriteria(LiveChannel.class).add( Restrictions.eq("channelId", channelId) ).uniqueResult();
           
        } catch (Exception e) {
            e.printStackTrace();
                        
        }
		
		return channel.getCategory().getCategoryId();
	}

	@Override
	public String returnSingleChannelsCategoryName(int channelId) {
        LiveChannel channel = null;			
		
        try {
            channel = (LiveChannel) getSession().createCriteria(LiveChannel.class).add( Restrictions.eq("channelId", channelId) ).uniqueResult();
           
        } catch (Exception e) {
            e.printStackTrace();
                        
        }
		
		return channel.getCategory().getCategoryName();
	}

	@Override
	public int returnSingleChannelsLanguageId(int channelId) {
        LiveChannel channel = null;			
		
        try {
            channel = (LiveChannel) getSession().createCriteria(LiveChannel.class).add( Restrictions.eq("channelId", channelId) ).uniqueResult();
           
        } catch (Exception e) {
            e.printStackTrace();
                        
        }
		
		return channel.getLanguage().getLanguageId();
	}

	@Override
	public String returnSingleChannelsLanguageName(int channelId) {
        LiveChannel channel = null;			
		
        try {
            channel = (LiveChannel) getSession().createCriteria(LiveChannel.class).add( Restrictions.eq("channelId", channelId) ).uniqueResult();
           
        } catch (Exception e) {
            e.printStackTrace();
                        
        }
		
		return channel.getLanguage().getLanguageName();
	}

	@Override
	public List<LiveChannel> getLatestFiveChannels() {
		
		List<LiveChannel> channels = new ArrayList<LiveChannel>();
		
	      try{
	         Query query = (Query) getSession().createQuery("FROM LiveChannel ORDER BY channelId DESC");
	         query.setMaxResults(5);
	         channels = query.list();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
		
		return channels;
	}
	
	@Override
	public int returnNumberOfLiveChannel() {
		
		Integer totalChannel = 0;
	      try{
	          Long count = ((Long) getSession().createQuery(
						"select count(*) from LiveChannel").uniqueResult());
	         totalChannel = count.intValue();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
		
		return totalChannel;
	}
	
	@Override
	public int returnNumberOfLiveChannelByUserId(int userId) {
		
		Integer totalChannel = 0;
	      try{
	         Long count = ((Long) getSession().createQuery(
						"select count(*) from LiveChannel WHERE userId="+userId).uniqueResult());
	         totalChannel = count.intValue();
	      }catch (HibernateException e) {
	         e.printStackTrace(); 
	      }
		
		return totalChannel;
	}

	@Override
	public void saveLiveChannel(LiveChannel liveChannel) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int saveLiveChannel(LiveChannel liveChannel, String logo,
			User user, String link720, String link480,String link360,
			String link180, String linkReturnToUser, String state)
			throws Exception {
		
		Integer id=0;
		try {
			liveChannel.setLogo(logo);
			liveChannel.setUser(user);
			liveChannel.setLink720(link720);
			liveChannel.setLink480(link480);
			liveChannel.setLink360(link360);
			liveChannel.setLink180(link180);
			liveChannel.setLinkReturnToUser(linkReturnToUser);
			liveChannel.setState(state);
			id = (Integer) getSession().save(liveChannel);
			
			

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
		
		return id;
		
	}

	@Override
	public void updateLiveChannel(LiveChannel liveChannel)
			throws Exception {
		try {
			getSession().update(liveChannel);

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;

		} 
		
	}
	
	@Override
	public void deleteChannel(LiveChannel channel) throws Exception {
		
		try {
			getSession().delete(channel);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}
	
	@Override
	public List<LiveChannel> getSearchedChannelList(String[] tagsArray) {
		
		List<LiveChannel> liveChannelList = new ArrayList<LiveChannel>();

		try {
			for (int i = 0; i < tagsArray.length; i++) {
				String singleTag = "%" + tagsArray[i] + "%";
				Query primaryTagQuery = (Query) getSession().createQuery("FROM PrimaryTag PT WHERE PT.primaryTagName like :primaryTagName");
				List<PrimaryTag> primaryTags = primaryTagQuery.setParameter("primaryTagName", singleTag).list();
				if (primaryTags.size() > 0) {
					for(PrimaryTag pTag : primaryTags){
						Set<LiveChannel> primaryTagChannelSet = pTag.getLiveChannelSet();
						for (LiveChannel ch : primaryTagChannelSet) {
							if (!liveChannelList.contains(ch)) {
								liveChannelList.add(ch);
							}
						}
					}
					
				} 
			
					 Query secondaryTagQuery = (Query) getSession().createQuery("FROM Tag T WHERE T.tagName like :tagName");
					 List<Tag> tags = secondaryTagQuery.setParameter("tagName", singleTag).list();
					 if(tags.size() > 0){
						 for(Tag tag : tags){
							 Set<LiveChannel> secondaryTagChannelSet = tag.getLiveChannelSet();
								for (LiveChannel ch : secondaryTagChannelSet) {
									if (!liveChannelList.contains(ch)) {
										liveChannelList.add(ch);
									}
								}
						 }
					 }

			}

			Collections.sort(liveChannelList, new channelComparator(tagsArray));
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} 
		
		return liveChannelList;
	}
	
	@Override
	public int getSearchedChannelNumber(String[] tagsArray) {
		
		List<LiveChannel> liveChannelList = new ArrayList<LiveChannel>();

		try {
			for (int i = 0; i < tagsArray.length; i++) {
				String singleTag = "%" + tagsArray[i] + "%";
				Query primaryTagQuery = (Query) getSession().createQuery("FROM PrimaryTag PT WHERE PT.primaryTagName like :primaryTagName");
				List<PrimaryTag> primaryTags = primaryTagQuery.setParameter("primaryTagName", singleTag).list();
				if (primaryTags.size() > 0) {
					for(PrimaryTag pTag : primaryTags){
						Set<LiveChannel> primaryTagChannelSet = pTag.getLiveChannelSet();
						for (LiveChannel ch : primaryTagChannelSet) {
							if (!liveChannelList.contains(ch)) {
								liveChannelList.add(ch);
							}
						}
					}
					
				} 
			
					 Query secondaryTagQuery = (Query) getSession().createQuery("FROM Tag T WHERE T.tagName like :tagName");
					 List<Tag> tags = secondaryTagQuery.setParameter("tagName", singleTag).list();
					 if(tags.size() > 0){
						 for(Tag tag : tags){
							 Set<LiveChannel> secondaryTagChannelSet = tag.getLiveChannelSet();
								for (LiveChannel ch : secondaryTagChannelSet) {
									if (!liveChannelList.contains(ch)) {
										liveChannelList.add(ch);
									}
								}
						 }
					 }

			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		
		int total = liveChannelList.size();
		liveChannelList = null;
		
		return total;
	}

	@Override
	public List<LiveChannel> returnSearchedChannelsByTag(String[] tagsArray,int start,int perPage) {
		
        List<LiveChannel> channelList = new ArrayList<LiveChannel>();
		
		List<Country> countryList = new ArrayList<Country>();
		
		List<Category> categoryList = new ArrayList<Category>();
		
		List<Language> languageList = new ArrayList<Language>();
		
		try {
			for (int i = 0; i < tagsArray.length; i++) {
				String singleTag = "%" + tagsArray[i] + "%";
				Query channelQuery = (Query) getSession().createQuery("FROM LiveChannel L WHERE L.tag like :tagName OR L.channelName like :tagName");
				channelQuery.setFirstResult(start);
				channelQuery.setMaxResults(perPage);
				List<LiveChannel> channelFromQuery = channelQuery.setParameter("tagName", singleTag).list();
				
					for(LiveChannel channel : channelFromQuery){
						if (!channelList.contains(channel)) {
							channelList.add(channel);
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
				Criteria cr = getSession().createCriteria(LiveChannel.class);
				cr.add(Restrictions.eq("country", country));
				cr.setFirstResult(start);
				cr.setMaxResults(perPage);
				List<LiveChannel> channelFromQuery = cr.list();
				
				for(LiveChannel channel : channelFromQuery){
					if (!channelList.contains(channel)) {
						channelList.add(channel);
					}
				}
			}
			
			for(Category category : categoryList){
				Criteria cr = getSession().createCriteria(LiveChannel.class);
				cr.add(Restrictions.eq("category", category));
				cr.setFirstResult(start);
				cr.setMaxResults(perPage);
				List<LiveChannel> channelFromQuery = cr.list();
				
				for(LiveChannel channel : channelFromQuery){
					if (!channelList.contains(channel)) {
						channelList.add(channel);
					}
				}
			}
			
			for(Language language : languageList){
				Criteria cr = getSession().createCriteria(LiveChannel.class);
				cr.add(Restrictions.eq("language", language));
				cr.setFirstResult(start);
				cr.setMaxResults(perPage);
				List<LiveChannel> channelFromQuery = cr.list();
				
				for(LiveChannel channel : channelFromQuery){
					if (!channelList.contains(channel)) {
						channelList.add(channel);
					}
				}
			}

		} catch (HibernateException ex) {
			
			ex.printStackTrace();
		}
		
		return channelList;
	}
	
	@Override
	public List<LiveChannel> returnSearchedChannelByFilter(String category,
			String country, String language,int start,int perPage) {
		
		List<LiveChannel> list = new ArrayList<LiveChannel>();
		
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
			
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
			
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
			
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
			
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
			
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
			
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
	public int returnTotalSearchedChannelByFilter(String category,
			String country, String language) {
		
        List<LiveChannel> list = new ArrayList<LiveChannel>();
        
        int totalChannel = 0;
		
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
			
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
				totalChannel = Integer.parseInt(""+list.get(0));
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
			Criterion countryCriterion = Restrictions.in("country", countryList.toArray());
			Criterion languageCriterion = Restrictions.in("language", languageList.toArray());
			LogicalExpression exp1 = Restrictions.and(countryCriterion, languageCriterion);
			cr.add(exp1);
			cr.setProjection(Projections.rowCount());
			list = cr.list();
			try{
				totalChannel = Integer.parseInt(""+list.get(0));
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
			
			Criterion categoryCriterion = Restrictions.in("category", categoryList.toArray());
			Criterion languageCriterion = Restrictions.in("language", languageList.toArray());
			
			LogicalExpression exp1 = Restrictions.and(categoryCriterion, languageCriterion);
			cr.add(exp1);
			cr.setProjection(Projections.rowCount());
			list = cr.list();
			try{
				totalChannel = Integer.parseInt(""+list.get(0));
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
			
			Criterion categoryCriterion = Restrictions.in("category", categoryList.toArray());
			Criterion countryCriterion = Restrictions.in("country", countryList.toArray());
			
			LogicalExpression exp1 = Restrictions.and(countryCriterion, categoryCriterion);
			cr.add(exp1);
			cr.setProjection(Projections.rowCount());
			list = cr.list();
			try{
				totalChannel = Integer.parseInt(""+list.get(0));
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
			
			Criterion languageCriterion = Restrictions.in("language", languageList.toArray());
			cr.add(languageCriterion);
			cr.setProjection(Projections.rowCount());
			list = cr.list();
			try{
				totalChannel = Integer.parseInt(""+list.get(0));
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
			
			Criterion categoryCriterion = Restrictions.in("category", categoryList.toArray());
			cr.add(categoryCriterion);
			cr.setProjection(Projections.rowCount());
			list = cr.list();
			try{
				totalChannel = Integer.parseInt(""+list.get(0));
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
			
			Criteria cr = getSession().createCriteria(LiveChannel.class);
			
			Criterion countryCriterion = Restrictions.in("country", countryList.toArray());
			cr.add(countryCriterion);
			cr.setProjection(Projections.rowCount());
			list = cr.list();
			try{
				totalChannel = Integer.parseInt(""+list.get(0));
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(category.equals("") && country.equals("") && language.equals("")){
			totalChannel = 0;
		}
		
		return totalChannel;
	}
	
	/**
	 * 
	 * for web service
	 */

	@Override
	public List<LiveChannel> returnChannelsByCountryId(int countryId) {
		List liveChannels = new ArrayList<LiveChannel>();
		
		try{
			liveChannels= getSession().createQuery("From LiveChannel WHERE countryId ="+countryId).list();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return liveChannels;
	}

	

	class channelComparator implements Comparator<LiveChannel> {
		String[] tagsArray;

		public channelComparator(String[] tagsArray) {
			this.tagsArray = tagsArray;
		}

		@Override
		public int compare(LiveChannel ch1, LiveChannel ch2) {
			int matchedTagCh1 = 0;
			int matchedTagCh2 = 0;
			List<String> ch1Tags = new ArrayList<String>();
			List<String> ch2Tags = new ArrayList<String>();

			for (Tag tag : ch1.getTagSet()) {
				ch1Tags.add(tag.getTagName());
			}

			for (Tag tag : ch2.getTagSet()) {
				ch2Tags.add(tag.getTagName());
			}

			for (String tagCh1 : tagsArray) {
				if (ch1Tags.contains(tagCh1)) {
					matchedTagCh1++;
				}
			}
			for (String tagCh2 : tagsArray) {
				if (ch2Tags.contains(tagCh2)) {
					matchedTagCh2++;
				}
			}
			if (matchedTagCh1 < matchedTagCh2) {
				return 1;
			} else {
				return -1;
			}
		}
	}


}
