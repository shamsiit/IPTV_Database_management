package com.ipvision.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipvision.dao.TagDao;
import com.ipvision.domain.LiveChannel;
import com.ipvision.domain.PrimaryTag;
import com.ipvision.domain.Tag;

@Service
public class TagServiceImplementation implements TagService {

	@Autowired
	TagDao tagDao;

	@Override
	@Transactional
	public Tag returnTagById(String tagId) {

		return tagDao.returnTagById(tagId);
	}

	@Override
	@Transactional
	public List<Tag> returnAllTag(int startTag, int selectedTagPerPage) {

		return tagDao.returnAllTag(startTag, selectedTagPerPage);
	}

	@Override
	@Transactional
	public int returnNumberOfTag() {

		return tagDao.returnNumberOfTag();
	}

	@Override
	@Transactional
	public void saveTag(Tag tag) throws Exception {

		tagDao.saveTag(tag);

	}

	@Override
	@Transactional
	public void savePrimaryTag(PrimaryTag primaryTag) throws Exception {

		tagDao.savePrimaryTag(primaryTag);

	}

	@Override
	@Transactional
	public List<PrimaryTag> getAllPrimaryTags() throws Exception {

		return tagDao.getAllPrimaryTags();

	}

	@Override
	@Transactional
	public void updatePrimaryTag(String oldPrimaryTag, String newPrimaryTag)
			throws Exception {

		tagDao.updatePrimaryTag(oldPrimaryTag, newPrimaryTag);

	}
	
	@Override
	@Transactional
	public void replaceTag(String oldTag, String newTag)
			throws Exception {

		tagDao.replaceTag(oldTag, newTag);

	}

	@Override
	@Transactional
	public void updateTag(Tag tag, String tagId) throws Exception {

		tagDao.updateTag(tag, tagId);

	}

	@Override
	@Transactional
	public Map<Integer, String> getTagMap() {

		return tagDao.getTagMap();
	}

	@Override
	@Transactional
	public List<String> getAllTags() {

		return tagDao.getAllTags();
	}

	@Override
	@Transactional
	public List<Tag> getSpecificTags(String[] tagsArray) {

		return tagDao.getSpecificTags(tagsArray);
	}

	@Override
	@Transactional
	public List<PrimaryTag> getSpecificPrimaryTags(String[] tagsArray) {

		return tagDao.getSpecificPrimaryTags(tagsArray);
	}

	@Override
	@Transactional
	public String getFilteredTagsName(String[] tagsArray) {

		return tagDao.getFilteredTagsName(tagsArray);
	}
	
	@Override
	@Transactional
	public void deleteTag(Tag tag) throws Exception {
		tagDao.deleteTag(tag);
	}
	
	@Override
	@Transactional
	public void deletePrimaryTag(PrimaryTag pTag) throws Exception {
		tagDao.deletePrimaryTag(pTag);
		
	}

}