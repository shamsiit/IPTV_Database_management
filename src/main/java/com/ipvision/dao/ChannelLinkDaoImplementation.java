package com.ipvision.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ipvision.domain.ChannelLink;

@Repository
public class ChannelLinkDaoImplementation implements ChannelLinkDao {

	@Autowired
	SessionFactory sessionFactory;
	
    Session session;
	
	protected Session getSession() {
	    return  sessionFactory.getCurrentSession();
	}

	@Override
	public ChannelLink returnChannelLinkByChannelId(int channelId) {
		
		ChannelLink cat = null;
		try {
			cat = (ChannelLink) getSession()
					.createCriteria(ChannelLink.class)
					.add(Restrictions.eq("channelId", channelId))
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cat;
	}
	
	@Override
	public void saveChannelLink(ChannelLink channelLink,int channelId,
			String liveStreamerLink720,String liveStreamerLink480,
			String liveStreamerLink360,String liveStreamerLink180,String sboxLink720,
			String sboxLink480,String sboxLink360,String sboxLink180) throws Exception {
		
		
		Integer channelLinkId=0;
		channelLink.setChannelId(channelId);
		channelLink.setLiveStreamerLink720(liveStreamerLink720);
		channelLink.setLiveStreamerLink480(liveStreamerLink480);
		channelLink.setLiveStreamerLink360(liveStreamerLink360);
		channelLink.setLiveStreamerLink180(liveStreamerLink180);
		channelLink.setSboxLink720(sboxLink720);
		channelLink.setSboxLink480(sboxLink480);
		channelLink.setSboxLink360(sboxLink360);
		channelLink.setSboxLink360(sboxLink180);
		try {
			channelLinkId = (Integer) getSession().save(channelLink);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
		
	}

	@Override
	public void updateChannelLink(int channelId,
			String liveStreamerLink720,String liveStreamerLink480,
			String liveStreamerLink360,String liveStreamerLink180,String sboxLink720,
			String sboxLink480,String sboxLink360,String sboxLink180)
			throws Exception {
		
		try {
			ChannelLink editChannelLink = (ChannelLink) getSession().get(ChannelLink.class,channelId);
			editChannelLink.setLiveStreamerLink720(liveStreamerLink720);
			editChannelLink.setLiveStreamerLink480(liveStreamerLink480);
			editChannelLink.setLiveStreamerLink360(liveStreamerLink360);
			editChannelLink.setLiveStreamerLink180(liveStreamerLink180);
			editChannelLink.setSboxLink720(sboxLink720);
			editChannelLink.setSboxLink480(sboxLink480);
			editChannelLink.setSboxLink360(sboxLink360);
			editChannelLink.setSboxLink180(sboxLink180);
			getSession().update(editChannelLink);

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;

		}
		
	}
	
	@Override
	public void deleteChannelLink(ChannelLink channelLink) throws Exception {
		
		try {
			getSession().delete(channelLink);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}

	
}
