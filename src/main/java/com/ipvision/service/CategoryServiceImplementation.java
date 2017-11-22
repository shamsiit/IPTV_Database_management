package com.ipvision.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipvision.dao.CategoryDao;
import com.ipvision.domain.Category;

@Service
public class CategoryServiceImplementation implements CategoryService {

	@Autowired
	CategoryDao categoryDao;
	
	@Override
	@Transactional
	public Category returnCategoryById(String categoryId) {
		
		return categoryDao.returnCategoryById(categoryId);
	}
	
	@Override
	@Transactional
	public List<Category> returnAllCategory(int startCategory,
			int selectedCategoryPerPage) {
		
		return categoryDao.returnAllCategory(startCategory, selectedCategoryPerPage);
	}
	
	@Override
	@Transactional
	public List<Category> returnAllCategory() {
		
		return categoryDao.returnAllCategory();
	}

	@Override
	@Transactional
	public int returnNumberOfCategory() {
		
		return categoryDao.returnNumberOfCategory();
	}

	@Override
	@Transactional
	public void saveCategory(Category category) throws Exception {
		
		categoryDao.saveCategory(category);
		
	}

	@Override
	@Transactional
	public void updateCategory(Category category, String categoryId)
			throws Exception {
		
		categoryDao.updateCategory(category, categoryId);
		
	}
	
	@Override
	@Transactional
	public void deleteCategory(Category category) throws Exception {
		categoryDao.deleteCategory(category);
		
	}


}
