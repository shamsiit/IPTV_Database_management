package com.ipvision.service;

import java.util.List;

import com.ipvision.domain.LiveChannel;
import com.ipvision.domain.User;

public interface LiveChannelService {

    public LiveChannel returnLiveChannelById(String liveChannelId);
	
	public List<LiveChannel> returnAllLiveChannel(int startChannel,int selectedLiveChannelPerPage);
	
	public List<LiveChannel> returnAllLiveChannelByUserId(int startChannel,int liveChannelPerPage,int userId);
	
    public List<LiveChannel> returnUpChannelsByUserId(int userId);
	
	public List<LiveChannel> returnDownChannelsByUserId(int userId);
	
    public int returnSingleChannelsCountryId(int channelId);
	
	public String returnSingleChannelsCountryName(int channelId);
	
    public int returnSingleChannelsCategoryId(int channelId);
	
	public String returnSingleChannelsCategoryName(int channelId);
	
    public int returnSingleChannelsLanguageId(int channelId);
	
	public String returnSingleChannelsLanguageName(int channelId);
	
	public List<LiveChannel> getLatestFiveChannels();
	
	public int returnNumberOfLiveChannel();
	
	public int returnNumberOfLiveChannelByUserId(int userId);
	
	public void saveLiveChannel(LiveChannel liveChannel) throws Exception;
	
	public int saveLiveChannel(LiveChannel liveChannel, String logo,
			User user, String link720, String link480,String link360,
			String link180, String linkReturnToUser, String state) throws Exception;
	
	public void updateLiveChannel(LiveChannel liveChannel) throws Exception;
	
	public void deleteChannel(LiveChannel channel) throws Exception;
	
	public List<LiveChannel> getSearchedChannelList(String[] tagsArray);
	
	public int getSearchedChannelNumber(String[] tagsArray);
	
	public List<LiveChannel> returnSearchedChannelsByTag(String [] tagsArray,int start,int perPage);
	
	public List<LiveChannel> returnSearchedChannelByFilter(String category,String country,String language,int start,int perPage);
	
	public int returnTotalSearchedChannelByFilter(String category,String country,String language);
	
	/**
	 * for web service
	 */
	public List<LiveChannel> returnChannelsByCountryId(int countryId);
	
	
	
	/**
	 * Generate playlist
	 */
	
	public void createPlayList(String fileName, String link720, String link480, String link360, String link180);
}
