package com.ipvision.service;

import java.util.List;

import com.ipvision.domain.LiveChannel;
import com.ipvision.domain.VOD;

public interface VODService {

    public VOD returnVODById(String vodId);
	
	public List<VOD> returnAllVOD(String pageNumber,int vodPerPage);
	
    public String returnSingleVodsCategoryName(int vodId);
	
	public int returnSingleVodsCategoryId(int vodId);
	
    public String returnSingleVodsCountryName(int vodId);
	
	public int returnSingleVodsCountryId(int vodId);
	
    public String returnSingleVodsLanguageName(int vodId);
	
	public int returnSingleVodsLanguageId(int vodId);
	
	public List<VOD> returnAllVODByUserId(String pageNumber,int vodPerPage,int userId);
	
	public int returnNumberOfVOD();
	
	public int returnNumberOfVODByUserId(int userId);
	
	public void saveVOD(VOD vod) throws Exception;
	
	public void deleteVOD(VOD vod) throws Exception;
	
	public void updateVOD(VOD vod) throws Exception;
	
	public List<VOD> getLatestFiveVods();
	
	public List<VOD> getSearchedVodList(String[] tagsArray);
	
	public int getSearchedVodNumber(String[] tagsArray);
	
	public List<VOD> returnSearchedVodsByTag(String [] tagsArray,int start,int perPage);
	
    public List<VOD> returnSearchedVodByFilter(String category,String country,String language,int start,int perPage);
	
	public int returnTotalSearchedVodByFilter(String category,String country,String language);
}