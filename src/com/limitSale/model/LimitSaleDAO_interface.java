package com.limitSale.model;


import java.util.List;

public interface LimitSaleDAO_interface {
	// 此介面定義對資料庫的相關存取抽象方法
	public void add(LimitSaleVO ls);
	public void update(LimitSaleVO ls);
	public void delete(Integer sale_no);
	public LimitSaleVO findByPK(Integer sale_no);
	public List<LimitSaleVO> getAll();
	public LimitSaleVO findOneSale(String item_no);
	public List<LimitSaleVO> findHomePage();
	public void offLimit(Integer sale_no);

}