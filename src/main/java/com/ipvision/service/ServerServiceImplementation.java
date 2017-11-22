package com.ipvision.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipvision.dao.ServerDao;
import com.ipvision.domain.Server;

@Service
public class ServerServiceImplementation implements ServerService{

	@Autowired
	ServerDao serverDao;

	@Override
	@Transactional
	public Server returnServerById(String serverId) {
		
		return serverDao.returnServerById(serverId);
	}
	
	@Override
	@Transactional
	public Server returnServerByIp(String serverIp) {
		
		return serverDao.returnServerByIp(serverIp);
	}
	
	@Override
	@Transactional
	public List<Server> returnAllServer(String pageNumber, int serverPerPage) {
		
		return serverDao.returnAllServer(pageNumber, serverPerPage);
	}
	
	@Override
	@Transactional
	public List<Server> returnAllTranscoderServerOrderByIdleCpu() {
		
		return serverDao.returnAllTranscoderServerOrderByIdleCpu();
	}

	@Override
	@Transactional
	public List<Server> returnAllStreamerServerOrderByTotalNumberOfStream() {
		
		return serverDao.returnAllStreamerServerOrderByTotalNumberOfStream();
	}

	@Override
	@Transactional
	public int returnNumberOfServer() {
		
		return serverDao.returnNumberOfServer();
	}
	
	@Override
	@Transactional
	public int returnNumberOfTotalStream(int serverId) {
		
		return serverDao.returnNumberOfTotalStream(serverId);
	}

	@Override
	@Transactional
	public void saveServer(Server server) throws Exception {
		
		serverDao.saveServer(server);
		
	}

	@Override
	@Transactional
	public void updateServer(Server server, String serverId) throws Exception {
		
		serverDao.updateServer(server, serverId);
		
	}

	@Override
	@Transactional
	public void updateServerMemoryCpu(int serverId,
			double cpuIdle, double memoryIdle)
			throws Exception {
		
		serverDao.updateServerMemoryCpu(serverId, cpuIdle, memoryIdle);
		
	}

	@Override
	@Transactional
	public void updateServerTotalStream(int serverId, int totalStream)
			throws Exception {
		
		serverDao.updateServerTotalStream(serverId, totalStream);
		
	}
	
	@Override
	@Transactional
	public void deleteServer(Server server) throws Exception {
		serverDao.deleteServer(server);
		
	}

	
}
