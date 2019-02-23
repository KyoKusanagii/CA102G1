package com.specialForU.model;


import java.util.List;

import com.member.model.MemVO;

public interface SpecialForUDAO_interface {
	// 此介面定義對資料庫的相關存取抽象方法
	void add(SpecialForUVO sf);
	void update(SpecialForUVO sf);
	void delete(String mem_no);
	SpecialForUVO findByPK(String mem_no);
	List<SpecialForUVO> getAll();
	void updateMemPreferenceCode(Integer code, String mem_no);
}