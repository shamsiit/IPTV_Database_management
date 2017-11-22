package com.ipvision.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.ipvision.domain.LiveChannel;
import com.ipvision.domain.PrimaryTag;
import com.ipvision.domain.Tag;

@Repository
public class TagDaoImplementation implements TagDao {

	@Autowired
	SessionFactory sessionFactory;
	
    Session session;
	
	protected Session getSession() {
	    return  sessionFactory.getCurrentSession();
	}

	@Override
	public Tag returnTagById(String tagId) {

		Tag tg = null;
		try {
			tg = (Tag) getSession().createCriteria(Tag.class)
					.add(Restrictions.eq("tagId", Integer.parseInt(tagId)))
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tg;
	}

	@Override
	public List<Tag> returnAllTag(int startTag, int selectedTagPerPage) {

		
		List<Tag> tags = new ArrayList<Tag>();
		try {
			Query query = (Query) getSession().createQuery("FROM Tag");
			query.setFirstResult(startTag);
			query.setMaxResults(selectedTagPerPage);
			tags = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return tags;
	}

	@Override
	public int returnNumberOfTag() {

		
		Integer totalTag = 0;
		try {
			Long count = ((Long) getSession().createQuery(
					"select count(*) from Tag").uniqueResult());
			totalTag = count.intValue();
		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return totalTag;
	}

	@Override
	public void saveTag(Tag tag) throws Exception {

		Integer id = 0;
		try {
			id = (Integer) getSession().save(tag);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}

	}

	@Override
	public List<PrimaryTag> getAllPrimaryTags() {

		List<PrimaryTag> primaryTags = new ArrayList<PrimaryTag>();
		try {
			primaryTags = getSession().createQuery("FROM PrimaryTag").list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return primaryTags;
	}

	@Override
	public void updatePrimaryTag(String oldPrimaryTag, String newPrimaryTag) {

		List<PrimaryTag> primaryTags;
		try {
			Query query = (Query) getSession()
					.createQuery("FROM PrimaryTag AS PT WHERE PT.primaryTagName = :primaryTagName");
			query.setString("primaryTagName", oldPrimaryTag);
			primaryTags = query.list();
			for (PrimaryTag pt : primaryTags) {
				if (pt.getPrimaryTagName().equalsIgnoreCase(oldPrimaryTag)) {
					pt.setPrimaryTagName(newPrimaryTag);
					getSession().update(pt);
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void replaceTag(String oldTag, String newTag) {

		try {
			Query query = (Query) getSession().createQuery("FROM Tag AS T WHERE T.tagName = :tagName");
			query.setString("tagName", oldTag);
			List<Tag> tags = query.list();
			for (Tag tg : tags) {
				if (tg.getTagName().equalsIgnoreCase(oldTag)) {
					tg.setTagName(newTag);
					getSession().update(tg);
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void savePrimaryTag(PrimaryTag primaryTag) throws Exception {

		Integer id = 0;
		try {
			id = (Integer) getSession().save(primaryTag);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public void updateTag(Tag tag, String tagId) throws Exception {

		try {
			Tag editTag = (Tag) getSession().get(Tag.class,
					Integer.parseInt(tagId));
			editTag.setTagName(tag.getTagName());
			getSession().update(editTag);

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;

		}

	}

	@Override
	public Map<Integer, String> getTagMap() {
		Map<Integer, String> tagMap = new HashMap<Integer, String>();
		List tags = new ArrayList<Tag>();
		try {
			Criteria cr = getSession().createCriteria(Tag.class)
				    .setProjection(Projections.projectionList() 
				      .add(Projections.property("tagId"), "tagId") 
				      .add(Projections.property("tagName"), "tagName")) 
				    .setResultTransformer(Transformers.aliasToBean(Tag.class));
    	  tags = cr.list();
			for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
				Tag temp = (Tag) iterator.next();
				tagMap.put(temp.getTagId(), temp.getTagName());
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		
		return tagMap;
	}

	@Override
	public List<String> getAllTags() {
		List<String> tagList = new ArrayList<String>();
		try {
			List tags = getSession().createQuery("FROM Tag").list();

			for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
				Tag temp = (Tag) iterator.next();
				tagList.add(temp.getTagName());
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return tagList;
	}

	public List<Tag> getSpecificTags(String[] tagsArray) {
		List<Tag> tagList = new ArrayList<Tag>();
		try {
			for (String splitTagName : tagsArray) {
				Query secondaryTagQuery = (Query) getSession().createQuery(
						"FROM Tag T WHERE T.tagName = :tagName").setString(
						"tagName", splitTagName);
				List<Tag> tags = secondaryTagQuery.list();
				if (tags.size() > 0) {
					tagList.add(tags.get(0));
				}
			}

		} catch (HibernateException e) {
			throw e;

		}
		
		return tagList;
	}

	public List<PrimaryTag> getSpecificPrimaryTags(String[] tagsArray) {
		List<PrimaryTag> primarytagList = new ArrayList<PrimaryTag>();
		try {
			for (String splitTagName : tagsArray) {
				Query primaryTagQuery = (Query) getSession()
						.createQuery(
								"FROM PrimaryTag PT WHERE PT.primaryTagName = :primaryTagName")
						.setString("primaryTagName", splitTagName);
				List<PrimaryTag> tags = primaryTagQuery.list();
				if (tags.size() > 0) {
					primarytagList.add(tags.get(0));
				}
			}

		} catch (HibernateException e) {
			throw e;

		}
		
		return primarytagList;
	}

	public String getFilteredTagsName(String[] tagsArray) {
		String filteredTagsName = "";
		try {
			for (String splitTagName : tagsArray) {
				Query primaryTagQuery = (Query) getSession().createQuery("FROM PrimaryTag PT WHERE PT.primaryTagName = :primaryTagName").setString("primaryTagName", splitTagName);
				List<PrimaryTag> primaryTags = primaryTagQuery.list();
				if (primaryTags.size() > 0) {
					continue;
				} else {
					Query secondaryTagQuery = (Query) getSession().createQuery("FROM Tag T WHERE T.tagName = :tagName").setString("tagName", splitTagName);
					List<Tag> tags = secondaryTagQuery.list();
					if (tags.size() > 0) {
						continue;
					} else {
						filteredTagsName = filteredTagsName + splitTagName + ",";
					}
				}
			}

		} catch (HibernateException e) {
			throw e;

		} 
		
		return filteredTagsName;
	}
	
	@Override
	public void deleteTag(Tag tag) throws Exception {
		try {
			getSession().delete(tag);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}
	
	@Override
	public void deletePrimaryTag(PrimaryTag pTag) throws Exception {

		try {
		    getSession().delete(pTag);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}

	}

}