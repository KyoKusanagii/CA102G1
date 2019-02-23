package com.follow_item.model;

import java.sql.Date;
import java.util.List;


public class Follow_itemService {
	
	private  Follow_itemDAO_interface dao;
	
	public Follow_itemService() {
		dao = new Follow_itemDAO();
	}
	
	public Follow_itemVO add(String mem_no, String item_no, Date folo_time) {
		
		Follow_itemVO follow_itemVO = new Follow_itemVO();
		
		follow_itemVO.setMem_no(mem_no);
		follow_itemVO.setItem_no(item_no);
		follow_itemVO.setFolo_time(folo_time);
	
		dao.insert(follow_itemVO);
		
		return follow_itemVO;
	}
	
	public Follow_itemVO findByPK(String mem_no, String item_no) {
		 Follow_itemVO follow_itemVO = new Follow_itemVO();
		 follow_itemVO.setMem_no(mem_no);
		 follow_itemVO.setItem_no(item_no);
		 Follow_itemVO cnt = dao.findByPrimaryKey(mem_no, item_no);
		 return cnt;
		
		
	}
	
	public void delete(String mem_no, String item_no) {
		dao.delete(mem_no, item_no);
	}
	
	public List<Follow_itemVO> getAll(String mem_no){
		return dao.getAll(mem_no);
	}
}
