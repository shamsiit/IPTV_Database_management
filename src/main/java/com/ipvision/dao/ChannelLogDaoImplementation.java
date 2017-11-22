package com.ipvision.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ipvision.domain.ChannelLog;

@Repository
public class ChannelLogDaoImplementation implements ChannelLogDao {

	@Autowired
	SessionFactory sessionFactory;
	
    Session session;
	
	protected Session getSession() {
	    return  sessionFactory.getCurrentSession();
	}
	
	@Override
	public void saveChannelLog(ChannelLog channelLog)
			throws Exception {
		
		Integer id=0;
		try {
			id = (Integer) getSession().save(channelLog);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<ChannelLog> returnChannelLogByChannelId(int channelId) {
		
        List channelLogs = new ArrayList<ChannelLog>();
		try {
			channelLogs = getSession().createQuery("FROM ChannelLog C WHERE C.channelId = "+channelId+" ORDER BY logId DESC").list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return channelLogs;
	}

}
