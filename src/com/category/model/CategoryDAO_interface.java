package com.category.model;

import java.util.List;
import java.util.Map;

public interface CategoryDAO_interface {
	void insert(CategoryVO category);
	void update(CategoryVO category);
	void delete(Integer cat_no);
	CategoryVO findByPK(Integer cat_no);
	List<CategoryVO> getAll();
	Map<Integer, String> getNameByNO();

}
