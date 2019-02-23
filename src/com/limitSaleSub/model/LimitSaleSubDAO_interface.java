package com.limitSaleSub.model;


import java.util.Set;

public interface LimitSaleSubDAO_interface {
	// 此介面定義對資料庫的相關存取抽象方法
	void add(LimitSaleSubVO ls);
	void update(LimitSaleSubVO ls);
	void delete(Integer sale_no);
	LimitSaleSubVO findByPK(Integer sale_no);
	
	LimitSaleSubVO findByItemNo(String item_no);
	LimitSaleSubVO getRandomOne();
	Set<LimitSaleSubVO> findBySeller(String memNo);
	Set<LimitSaleSubVO> findByKeyword(String search);
	Set<LimitSaleSubVO> getRandomFive();
	Set<LimitSaleSubVO> getAll();

}