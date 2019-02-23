package com.news.model;

import java.util.List;

public interface NewsDAO_interface {
	// 此介面定義對資料庫的相關存取抽象方法
	void insert(NewsVO news);
	void update(NewsVO news);
	void delete(int news_no);
	NewsVO findByPK(int news_no);
	List<NewsVO> getAll();
	List<NewsVO> showCustomer();

}