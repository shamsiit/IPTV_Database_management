package com.ipvision.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ipvision.domain.Server;

@Repository
public class ServerDaoImplementation implements ServerDao{

	@Autowired
	SessionFactory sessionFactory;
	
    Session session;
	
	protected Session getSession() {
	    return  sessionFactory.getCurrentSession();
	}

	@Override
	public Server returnServerById(String serverId) {
        Server server = null;
		try {
			server = (Server) getSession()
					.createCriteria(Server.class)
					.add(Restrictions.eq("serverId", Integer.parseInt(serverId)))
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return server;
	}
	
	@Override
	public Server returnServerByIp(String serverIp) {
        Server server = null;
		try {
			server = (Server) getSession()
					.createCriteria(Server.class)
					.add(Restrictions.eq("privateIp", serverIp))
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return server;
	}
	
	@Override
	public List<Server> returnAllServer(String pageNumber, int serverPerPage) {
		
		List<Server> servers = new ArrayList<Server>();
		int pageNo = Integer.parseInt(pageNumber);
		try {
			Query query = (Query) getSession().createQuery("FROM Server");
			query.setFirstResult((pageNo - 1) * 10);
			query.setMaxResults(serverPerPage);
			servers = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return servers;
	}
	
	@Override
	public List<Server> returnAllTranscoderServerOrderByIdleCpu() {
		
		List<Server> servers = new ArrayList<Server>();
		try {
			Query query = (Query) getSession().createQuery("FROM Server WHERE serverType='Transcoder' ORDER BY cpuUsage DESC");
			servers = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return servers;
	}
	
	@Override
	public List<Server> returnAllStreamerServerOrderByTotalNumberOfStream() {
		
		List<Server> servers = new ArrayList<Server>();
		try {
			Query query = (Query) getSession().createQuery("FROM Server WHERE serverType='Streamer' ORDER BY totalNumberOfStream ASC");
			servers = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		
		return servers;
	}
	
	@Override
	public int returnNumberOfServer() {
		
		Integer totalServer = 0;
		try {			
			Long count = ((Long) getSession().createQuery(
					"select count(*) from Server").uniqueResult());
			totalServer = count.intValue();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return totalServer;
	}
	
	@Override
	public int returnNumberOfTotalStream(int serverId) {
		
        Server server = null;
		int totalNumberOfStream = 0;
		try {
			server = (Server) getSession()
					.createCriteria(Server.class)
					.add(Restrictions.eq("serverId", serverId))
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(server != null){
			totalNumberOfStream = server.getTotalNumberOfStream();
		}
		
		return totalNumberOfStream;
	}

	@Override
	public void saveServer(Server server) throws Exception {
		
		Integer id = 0;
		try {
			id = (Integer) getSession().save(server);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
		
	}

	@Override
	public void updateServer(Server server, String serverId) throws Exception {
		
		try {
			Server editServer = (Server) getSession().get(
					Server.class, Integer.parseInt(serverId));
			editServer.setServerName(server.getServerName());
			editServer.setPrivateIp(server.getPrivateIp());
			editServer.setPublicIp(server.getPublicIp());
			editServer.setServerType(server.getServerType());
			editServer.setCpuUsage(server.getCpuUsage());
			editServer.setRamUsage(server.getRamUsage());
			editServer.setBandwidth(server.getBandwidth());
			editServer.setEdge(server.getEdge());
			getSession().update(editServer);

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;

		}
		
	}

	@Override
	public void updateServerMemoryCpu(int serverId,
			double cpuIdle, double memoryIdle)
			throws Exception {
		
		try {
			Server editServer = (Server) getSession().get(
					Server.class, serverId);
			editServer.setCpuUsage(cpuIdle);
			editServer.setRamUsage(memoryIdle);
			getSession().update(editServer);

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;

		}
		
	}

	@Override
	public void updateServerTotalStream(int serverId, int totalStream)
			throws Exception {
		try {
			Server editServer = (Server) getSession().get(
					Server.class, serverId);
			editServer.setTotalNumberOfStream(totalStream);
			getSession().update(editServer);

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;

		}
		
	}
	
	@Override
	public void deleteServer(Server server) throws Exception {
		
		try {
			getSession().delete(server);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}

}
