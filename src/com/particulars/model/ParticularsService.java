package com.particulars.model;

import java.util.List;
import java.util.Map;

public class ParticularsService {
private ParticularsDAO_interface dao;
	
	public ParticularsService() {
		dao = new ParticularsDAO();		
	}
	
	public ParticularsVO insert(Integer cat_no, Integer part_no, String part_name) {
		ParticularsVO particulars = new ParticularsVO();
		particulars.setCat_no(cat_no);
		particulars.setPart_no(part_no);
		particulars.setPart_name(part_name);
		dao.insert(particulars);

		return particulars;
	}
	
	public ParticularsVO update(Integer cat_no, Integer part_no, String part_name) {
		ParticularsVO particulars = new ParticularsVO();
		particulars.setCat_no(cat_no);
		particulars.setPart_no(part_no);
		particulars.setPart_name(part_name);
		dao.insert(particulars);

		return particulars;
	}
	
	public void delete(Integer part_no) {
		dao.delete(part_no);
	}
	
	public ParticularsVO findByPK(Integer cat_no, Integer part_no) {
		return dao.findByPK(cat_no, part_no);
	}
	
	public List<ParticularsVO> getAll() {
		return dao.getAll();
	}
	
	public Map<Integer, String> getNameByNO(){
		return dao.getNameByNO();
	}
	
	public List<ParticularsVO> getAllByCagNo(Integer cat_no) {
		return dao.getByCagNoAll(cat_no);
	}
	
}
