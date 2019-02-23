package com.particulars.model;

import java.util.List;
import java.util.Map;

import com.category.model.CategoryVO;

public interface ParticularsDAO_interface {
	void insert(ParticularsVO particulars);
	void update(ParticularsVO particulars);
	void delete(Integer part_no);
	ParticularsVO findByPK(Integer cat_no, Integer part_no);
	List<ParticularsVO> getAll();
	Map<Integer, String> getNameByNO();
	List<ParticularsVO> getByCagNoAll(Integer cat_no);

}
