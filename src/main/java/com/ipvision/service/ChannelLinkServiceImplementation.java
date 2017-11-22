package com.ipvision.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipvision.dao.ChannelLinkDao;
import com.ipvision.domain.ChannelLink;

@Service
public class ChannelLinkServiceImplementation implements ChannelLinkService {

	@Autowired
	ChannelLinkDao channelLinkDao;

	@Override
	@Transactional
	public ChannelLink returnChannelLinkByChannelId(int channelId) {
		
		return channelLinkDao.returnChannelLinkByChannelId(channelId);
	}
	
	@Override
	@Transactional
	public void saveChannelLink(ChannelLink channelLink,int channelId,
			String liveStreamerLink720,String liveStreamerLink480,
			String liveStreamerLink360,String liveStreamerLink180,String sboxLink720,
			String sboxLink480,String sboxLink360,String sboxLink180) throws Exception {
		
		channelLinkDao.saveChannelLink(channelLink, channelId,
				liveStreamerLink720, liveStreamerLink480, liveStreamerLink360, liveStreamerLink180,
				sboxLink720, sboxLink480, sboxLink360,sboxLink180);
		
	}

	@Override
	@Transactional
	public void updateChannelLink(int channelId,
			String liveStreamerLink720,String liveStreamerLink480,
			String liveStreamerLink360,String liveStreamerLink180,String sboxLink720,
			String sboxLink480,String sboxLink360,String sboxLink180)
			throws Exception {
		
		channelLinkDao.updateChannelLink(channelId, liveStreamerLink720, liveStreamerLink480,
				liveStreamerLink360, liveStreamerLink180, sboxLink720, sboxLink480, sboxLink360, sboxLink180);
		
	}
	
	@Override
	@Transactional
	public void deleteChannelLink(ChannelLink channelLink) throws Exception {
		channelLinkDao.deleteChannelLink(channelLink);
		
	}
	
}
