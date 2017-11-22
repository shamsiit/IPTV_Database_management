package com.ipvision.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipvision.dao.CountryDao;
import com.ipvision.domain.Country;

@Service
public class CountryServiceImplementation implements CountryService {

	@Autowired
	CountryDao countryDao;
	
	@Override
	@Transactional
	public Country returnCountryById(String countryId) {
		return countryDao.returnCountryById(countryId);
	}

	@Override
	@Transactional
	public List<Country> returnAllCountry(int startCountry,int selectedCountryPerPage) {
		return countryDao.returnAllCountry(startCountry, selectedCountryPerPage);
	}
	
	@Override
	@Transactional
	public List<Country> returnAllCountry() {
		
		return countryDao.returnAllCountry();
	}

	@Override
	@Transactional
	public int returnNumberOfCountry() {
		return countryDao.returnNumberOfCountry();
	}

	@Override
	@Transactional
	public void saveCountry(Country country) throws Exception {
		countryDao.saveCountry(country);
	}

	@Override
	@Transactional
	public void updateCountry(Country country, String countryId)
			throws Exception {
		countryDao.updateCountry(country, countryId);
		
	}
	
	@Override
	@Transactional
	public void deleteCountry(Country country) throws Exception {
		countryDao.deleteCountry(country);
		
	}


}
