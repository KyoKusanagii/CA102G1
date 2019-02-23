package com.consulter.model;

import java.util.List;

public class ConsulterService {
	
	private ConsulterDAO_interface dao;
	
	public ConsulterService() {
		
		dao = new ConsulterDAO();
	}
	
	public void addConsulter(String consulter_no,String consulter_name,byte[] consulter_icon) {
		
		ConsulterVO consulter = new ConsulterVO();
		consulter.setConsulter_no(consulter_no);
		consulter.setConsulter_name(consulter_name);
		consulter.setConsulter_icon(consulter_icon);
		dao.insert(consulter);
	}
	
	public List<ConsulterVO> getAllConsulter(){
		
		return dao.getAll();
	}
	
}
