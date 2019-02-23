package com.news.model;

import java.sql.Timestamp;
import java.util.List;

public class NewsService {
private NewsDAO_interface dao;
	
	public NewsService() {
		dao = new NewsDAO();		
	}
	
	public NewsVO insert(Timestamp news_date, String news_title, String news_content) {
		NewsVO news = new NewsVO();
		news.setNews_date(news_date);
		news.setNews_title(news_title);
		news.setNews_content(news_content);
		dao.insert(news);

		return news;
	}
	
	public NewsVO update(Integer news_no, Timestamp news_date, String news_title, String news_content) {
		NewsVO news = new NewsVO();
		news.setNews_no(news_no);
		news.setNews_date(news_date);
		news.setNews_title(news_title);
		news.setNews_content(news_content);
		dao.update(news);

		return news;
	}
	
	public void delete(Integer news_no) {
		dao.delete(news_no);
	}
	
	public NewsVO findByPK(Integer news_no) {
		return dao.findByPK(news_no);
	}
	
	public NewsVO getOneNews(Integer news_no) {
		return dao.findByPK(news_no);
	}
	
	public List<NewsVO> getAll() {
		return dao.getAll();
	}
	
	public List<NewsVO> showCustomer() {
		return dao.showCustomer();
	}
}
