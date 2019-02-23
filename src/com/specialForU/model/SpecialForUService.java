package com.specialForU.model;

public class SpecialForUService {
	
	private SpecialForUDAO_interface dao;
	
	public SpecialForUService() {
		dao = new SpecialForUJDBCDAO();
	}
	
	public void updateMemPreferenceCode(Integer code, String mem_no) {
		dao.updateMemPreferenceCode(code, mem_no);
	}
}
