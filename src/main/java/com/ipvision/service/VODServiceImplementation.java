package com.ipvision.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipvision.dao.VODDao;
import com.ipvision.domain.LiveChannel;
import com.ipvision.domain.VOD;

@Service
public class VODServiceImplementation implements VODService {

	@Autowired
	VODDao vodDao;

	@Override
	@Transactional
	public VOD returnVODById(String vodId) {
		
		return vodDao.returnVODById(vodId);
	}

	@Override
	@Transactional
	public List<VOD> returnAllVOD(String pageNumber, int vodPerPage) {
		
		return vodDao.returnAllVOD(pageNumber, vodPerPage);
	}
	
	@Override
	@Transactional
	public String returnSingleVodsCategoryName(int vodId) {
		
		return vodDao.returnSingleVodsCategoryName(vodId);
	}

	@Override
	@Transactional
	public int returnSingleVodsCategoryId(int vodId) {
		
		return vodDao.returnSingleVodsCategoryId(vodId);
	}
	
	@Override
	@Transactional
	public String returnSingleVodsCountryName(int vodId) {
		
		return vodDao.returnSingleVodsCountryName(vodId);
	}

	@Override
	@Transactional
	public int returnSingleVodsCountryId(int vodId) {
		
		return vodDao.returnSingleVodsCountryId(vodId);
	}

	@Override
	@Transactional
	public String returnSingleVodsLanguageName(int vodId) {
		
		return vodDao.returnSingleVodsLanguageName(vodId);
	}

	@Override
	@Transactional
	public int returnSingleVodsLanguageId(int vodId) {
		
		return vodDao.returnSingleVodsLanguageId(vodId);
	}
	
	@Override
	@Transactional
	public List<VOD> returnAllVODByUserId(String pageNumber, int vodPerPage,
			int userId) {
		
		return vodDao.returnAllVODByUserId(pageNumber, vodPerPage, userId);
	}

	@Override
	@Transactional
	public int returnNumberOfVOD() {
		
		return vodDao.returnNumberOfVOD();
	}
	
	@Override
	@Transactional
	public int returnNumberOfVODByUserId(int userId) {
		
		return vodDao.returnNumberOfVODByUserId(userId);
	}

	@Override
	@Transactional
	public void saveVOD(VOD vod) throws Exception {
		
		vodDao.saveVOD(vod);
		
	}

	@Override
	@Transactional
	public void deleteVOD(VOD vod) throws Exception {
		
		vodDao.deleteVOD(vod);
		
	}
	
	@Override
	@Transactional
	public void updateVOD(VOD vod) throws Exception {
		
		vodDao.updateVOD(vod);
		
	}
	
	@Override
	@Transactional
	public List<VOD> getLatestFiveVods() {
		
		return vodDao.getLatestFiveVods();
	}
	
	@Override
	@Transactional
	public List<VOD> getSearchedVodList(String[] tagsArray) {
		
		return vodDao.getSearchedVodList(tagsArray);
	}
	
	@Override
	@Transactional
	public int getSearchedVodNumber(String[] tagsArray) {
		
		return vodDao.getSearchedVodNumber(tagsArray);
	}

	@Override
	@Transactional
	public List<VOD> returnSearchedVodsByTag(String [] tagsArray,int start,int perPage){
		
		return vodDao.returnSearchedVodsByTag(tagsArray,start,perPage);
	}

	@Override
	@Transactional
	public List<VOD> returnSearchedVodByFilter(String category, String country,
			String language, int start, int perPage) {
		
		return vodDao.returnSearchedVodByFilter(category, country, language, start, perPage);
	}

	@Override
	@Transactional
	public int returnTotalSearchedVodByFilter(String category, String country,
			String language) {
		
		return vodDao.returnTotalSearchedVodByFilter(category, country, language);
	}

	
}