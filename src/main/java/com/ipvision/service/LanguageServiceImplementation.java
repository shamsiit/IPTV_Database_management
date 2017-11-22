package com.ipvision.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipvision.dao.LanguageDao;
import com.ipvision.domain.Language;

@Service
public class LanguageServiceImplementation implements LanguageService {

	@Autowired
	LanguageDao languageDao;
	
	@Override
	@Transactional
	public Language returnLanguageById(String languageId) {
		return languageDao.returnLanguageById(languageId);
	}

	@Override
	@Transactional
	public List<Language> returnAllLanguage(int startLanguage,int selectedLanguagePerPage) {
		return languageDao.returnAllLanguage(startLanguage , selectedLanguagePerPage);
	}
	
	@Override
	@Transactional
	public List<Language> returnAllLanguage() {
		
		return languageDao.returnAllLanguage();
	}

	@Override
	@Transactional
	public int returnNumberOfLanguage() {
		return languageDao.returnNumberOfLanguage();
	}

	@Override
	@Transactional
	public void saveLanguage(Language language) throws Exception {
		languageDao.saveLanguage(language);
	}

	@Override
	@Transactional
	public void updateLanguage(Language language, String languageId)
			throws Exception {
		
		languageDao.updateLanguage(language, languageId);
	}
	
	@Override
	@Transactional
	public void deleteLanguage(Language language) throws Exception {
		languageDao.deleteLanguage(language);
		
	}

	

}
