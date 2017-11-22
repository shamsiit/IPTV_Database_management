package com.ipvision.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ipvision.domain.ChannelLink;
import com.ipvision.domain.Country;
import com.ipvision.domain.LiveChannel;
import com.ipvision.domain.PrimaryTag;
import com.ipvision.domain.Tag;
import com.ipvision.domain.User;
import com.ipvision.monitorModule.threads.MonitoringThread;
import com.ipvision.service.ChannelLinkService;
import com.ipvision.service.CountryService;
import com.ipvision.service.LiveChannelService;
import com.ipvision.service.TagService;

@RestController
@RequestMapping("/api")
public class ServiceController {

	@Autowired
	CountryService countryService;
	
	@Autowired
	LiveChannelService liveChannelService;
	
	@Autowired
	ChannelLinkService channelLinkService;
	
	@Autowired
	TagService tagService;
	
	@RequestMapping(value="/allCountry", method=RequestMethod.GET)
	public ResponseEntity<Map> listAllCountries(){
		List<Country> countries = new ArrayList<Country>();
		
		countries = countryService.returnAllCountry();
		
		Map dataMap = new HashMap();
		
		if(countries.isEmpty()){
            return new ResponseEntity<Map>(HttpStatus.NOT_FOUND);//You many decide to return HttpStatus.NOT_FOUND
        }else{
        	dataMap.put("success", true);
        	dataMap.put("datarows", countries);
        	
        	return new ResponseEntity<Map>(dataMap, HttpStatus.OK);
        }    	
	}
	
	@RequestMapping(value="/liveChannel/{countryId}", method=RequestMethod.GET)
	public ResponseEntity<Map> listCountryChannels(@PathVariable String countryId){
		int id = 0;
		try{
			id = Integer.parseInt(countryId);
		}catch(Exception e){
			e.printStackTrace();
		}
		List<LiveChannel> liveChannels = new ArrayList<LiveChannel>();
		
		liveChannels = liveChannelService.returnChannelsByCountryId(id);
		
		Map dataMap = new HashMap();
		
		if(liveChannels.isEmpty()){
            return new ResponseEntity<Map>(HttpStatus.NOT_FOUND);//You many decide to return HttpStatus.NOT_FOUND
        }else{
        	dataMap.put("success", true);
        	dataMap.put("datarows", liveChannels);
        	
        	return new ResponseEntity<Map>(dataMap, HttpStatus.OK);
        }	
	}
	
	@RequestMapping(value="/channelLink/{channelId}", method=RequestMethod.GET)
	public ResponseEntity<Map> listChannelsLink(@PathVariable String channelId){
		int id = 0;
		try{
			id = Integer.parseInt(channelId);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		ChannelLink channelLink = channelLinkService.returnChannelLinkByChannelId(id);
		List channelLinkList = new ArrayList<ChannelLink>();
		channelLinkList.add(channelLink);
		
		Map dataMap = new HashMap();
		
		if(channelLink == null){
            return new ResponseEntity<Map>(HttpStatus.NOT_FOUND);//You many decide to return HttpStatus.NOT_FOUND
        }else{
        	dataMap.put("success", true);
        	dataMap.put("datarows", channelLinkList);
        	
        	return new ResponseEntity<Map>(dataMap, HttpStatus.OK);
        }	
	}
	
	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public ResponseEntity<List> tags(Tag tag, User user, final ModelMap model) {

		List<String> tags = new ArrayList<String>();
		try {
			tags = tagService.getAllTags();
			List<PrimaryTag> primarytags = tagService.getAllPrimaryTags();
			for (PrimaryTag pt : primarytags) {
				tags.add(pt.getPrimaryTagName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<List>(tags, HttpStatus.OK);
	}
}
