package com.category.model;

import java.util.List;
import java.util.Map;

public class CategoryService {
private CategoryDAO_interface dao;
	
	public CategoryService() {
		dao = new CategoryDAO();		
	}
	
	public CategoryVO insert(Integer cat_no, String cat_name) {
		CategoryVO category = new CategoryVO();
		category.setCat_no(cat_no);
		category.setCat_name(cat_name);
		dao.insert(category);

		return category;
	}
	
	public CategoryVO update(Integer cat_no, String cat_name) {
		CategoryVO category = new CategoryVO();
		category.setCat_no(cat_no);
		category.setCat_name(cat_name);
		dao.insert(category);

		return category;
	}
	
	public void delete(Integer cat_no) {
		dao.delete(cat_no);
	}
	
	public CategoryVO findByPK(Integer cat_no) {
		return dao.findByPK(cat_no);
	}
	
	public List<CategoryVO> getAll() {
		return dao.getAll();
	}
	
	public Map<Integer, String> getNameByNO(){
		return dao.getNameByNO();
	}
	
	
}
