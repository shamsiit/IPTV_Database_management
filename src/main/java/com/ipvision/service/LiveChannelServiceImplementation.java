package com.ipvision.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipvision.dao.LiveChannelDao;
import com.ipvision.domain.LiveChannel;
import com.ipvision.domain.User;

@Service
public class LiveChannelServiceImplementation implements LiveChannelService {

	@Autowired
	LiveChannelDao liveChannelDao;

	@Override
	@Transactional
	public LiveChannel returnLiveChannelById(String liveChannelId) {
		
		return liveChannelDao.returnLiveChannelById(liveChannelId);
	}

	@Override
	@Transactional
	public List<LiveChannel> returnAllLiveChannel(int startChannel,int selectedLiveChannelPerPage) {
		
		return liveChannelDao.returnAllLiveChannel(startChannel , selectedLiveChannelPerPage);
	}
	
	@Override
	@Transactional
	public List<LiveChannel> returnAllLiveChannelByUserId(int startChannel,
			int liveChannelPerPage, int userId) {
		
		return liveChannelDao.returnAllLiveChannelByUserId(startChannel, liveChannelPerPage, userId);
	}
	
	@Override
	@Transactional
	public List<LiveChannel> returnUpChannelsByUserId(int userId) {
		
		return liveChannelDao.returnUpChannelsByUserId(userId);
	}

	@Override
	@Transactional
	public List<LiveChannel> returnDownChannelsByUserId(int userId) {
		
		return liveChannelDao.returnDownChannelsByUserId(userId);
	}
	
	@Override
	@Transactional
	public int returnSingleChannelsCountryId(int channelId) {
		
		return liveChannelDao.returnSingleChannelsCountryId(channelId);
	}

	@Override
	@Transactional
	public String returnSingleChannelsCountryName(int channelId) {
		
		return liveChannelDao.returnSingleChannelsCountryName(channelId);
	}

	@Override
	@Transactional
	public int returnSingleChannelsCategoryId(int channelId) {
		
		return liveChannelDao.returnSingleChannelsCategoryId(channelId);
	}

	@Override
	@Transactional
	public String returnSingleChannelsCategoryName(int channelId) {
		
		return liveChannelDao.returnSingleChannelsCategoryName(channelId);
	}

	@Override
	@Transactional
	public int returnSingleChannelsLanguageId(int channelId) {
		
		return liveChannelDao.returnSingleChannelsLanguageId(channelId);
	}

	@Override
	@Transactional
	public String returnSingleChannelsLanguageName(int channelId) {
		
		return liveChannelDao.returnSingleChannelsLanguageName(channelId);
	}
	
	@Override
	@Transactional
	public List<LiveChannel> getLatestFiveChannels() {
		
		return liveChannelDao.getLatestFiveChannels();
	}

	@Override
	@Transactional
	public int returnNumberOfLiveChannel() {
		
		return liveChannelDao.returnNumberOfLiveChannel();
	}
	
	@Override
	@Transactional
	public int returnNumberOfLiveChannelByUserId(int userId) {
		
		return liveChannelDao.returnNumberOfLiveChannelByUserId(userId);
	}

	@Override
	@Transactional
	public void saveLiveChannel(LiveChannel liveChannel) throws Exception {
		
		liveChannelDao.saveLiveChannel(liveChannel);
		
	}
	
	@Override
	@Transactional
	public int saveLiveChannel(LiveChannel liveChannel, String logo,
			User user, String link720, String link480,String link360,
			String link180, String linkReturnToUser, String state)
			throws Exception {
		
		return liveChannelDao.saveLiveChannel(liveChannel, logo, user, link720, link480, link360, link180, linkReturnToUser, state);
		
	}

	@Override
	@Transactional
	public void updateLiveChannel(LiveChannel liveChannel)
			throws Exception {
		
		liveChannelDao.updateLiveChannel(liveChannel );
		
	}
	
	@Override
	@Transactional
	public void deleteChannel(LiveChannel channel) throws Exception {
		liveChannelDao.deleteChannel(channel);
		
	}
	
	@Override
	@Transactional
	public List<LiveChannel> getSearchedChannelList(String[] tagsArray) {

		return liveChannelDao.getSearchedChannelList(tagsArray);
	}
	
	@Override
	@Transactional
	public int getSearchedChannelNumber(String[] tagsArray) {
		
		return liveChannelDao.getSearchedChannelNumber(tagsArray);
	}

	@Override
	@Transactional
	public List<LiveChannel> returnSearchedChannelsByTag(String[] tagsArray,int start,int perPage) {
		
		return liveChannelDao.returnSearchedChannelsByTag(tagsArray,start,perPage);
	}
	
	@Override
	@Transactional
	public List<LiveChannel> returnSearchedChannelByFilter(String category,
			String country, String language,int start,int perPage) {
		
		return liveChannelDao.returnSearchedChannelByFilter(category, country, language,start,perPage);
	}
	
	@Override
	@Transactional
	public int returnTotalSearchedChannelByFilter(String category,
			String country, String language) {
		
		return liveChannelDao.returnTotalSearchedChannelByFilter(category, country, language);
	}
	
	@Override
	public void createPlayList(String fileName, String link720, String link480,
			String link360, String link180) {
		 
		 String toWrite = "#EXTM3U\n"
		 				+ "#EXT-X-VERSION:3\n";
		 
		 String res="";
		 if(!link720.equals("")){
			 res = "#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=2000000,RESOLUTION=1280x720\n";
			 toWrite = toWrite+res+link720+"\n";
		 }
		 
		 if(!link480.equals("")){
			 res = "#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=800000,RESOLUTION=854x480\n";
			 toWrite = toWrite+res+link480+"\n";
		 }
		 
		 if(!link360.equals("")){
			 res = "#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=400000,RESOLUTION=640x360\n";
			 toWrite = toWrite+res+link360+"\n";
		 }
		 
		 if(!link180.equals("")){
			 res = "#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=200000,RESOLUTION=320x180\n";
			 toWrite = toWrite+res+link180+"\n";
		 }
		 
		 
		 try {
			 
			 String rootPath = System.getProperty("catalina.base")
						+ "/webapps/images";
			 String path = rootPath + "/"+ fileName + ".m3u8";
			
			File file = new File(path);
				
			FileWriter fileWriter = new FileWriter(file);
				
				
			/*PrintWriter out = new PrintWriter(path);
			out.println(toWrite);*/
			fileWriter.write(toWrite);
			fileWriter.flush();
			fileWriter.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	/**
	 * 
	 * for webservice
	 */
	
	@Override
	@Transactional
	public List<LiveChannel> returnChannelsByCountryId(int countryId) {
		
		return liveChannelDao.returnChannelsByCountryId(countryId);
	}


}
