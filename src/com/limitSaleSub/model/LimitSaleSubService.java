package com.limitSaleSub.model;

import java.util.Set;

public class LimitSaleSubService {
	private LimitSaleSubDAO_interface dao;
	
	public LimitSaleSubService() {
		dao = new LimitSaleSubJDBCDAO();
	}
	
	public LimitSaleSubVO findByPK(Integer sale_no) {
		return dao.findByPK(sale_no);
	}
	
	public LimitSaleSubVO getRandomOne() {
		return dao.getRandomOne();
	}
	
	public LimitSaleSubVO findByItemNo(String item_no) {
		return dao.findByItemNo(item_no);
	}
	
	public Set<LimitSaleSubVO> findBySeller(String memNo){
		return dao.findBySeller(memNo);
	}
	
	public Set<LimitSaleSubVO> findByKeyword(String search){
		return dao.findByKeyword(search);
	}
	
	public Set<LimitSaleSubVO> getRandomFive(){
		return dao.getRandomFive();
	}
	
	public Set<LimitSaleSubVO> getAll(){
		return dao.getAll();
	}

}
