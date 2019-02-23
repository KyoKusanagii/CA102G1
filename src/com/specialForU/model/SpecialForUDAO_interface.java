package com.specialForU.model;


import java.util.List;

import com.member.model.MemVO;

public interface SpecialForUDAO_interface {
	// �������w�q���Ʈw�������s����H��k
	void add(SpecialForUVO sf);
	void update(SpecialForUVO sf);
	void delete(String mem_no);
	SpecialForUVO findByPK(String mem_no);
	List<SpecialForUVO> getAll();
	void updateMemPreferenceCode(Integer code, String mem_no);
}