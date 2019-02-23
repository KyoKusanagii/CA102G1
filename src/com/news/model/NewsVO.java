package com.news.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class NewsVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer news_no;
	private Timestamp news_date;	//有Timestamp類型的格式
	private String news_title;
	private String news_content;
	
	public Integer getNews_no() {
		return news_no;
	}
	public void setNews_no(Integer news_no) {
		this.news_no = news_no;
	}
	public Timestamp getNews_date() {
		return news_date;
	}
	public void setNews_date(Timestamp news_date) {
		this.news_date = news_date;
	}
	public String getNews_title() {
		return news_title;
	}
	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}
	public String getNews_content() {
		return news_content;
	}
	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}
	
	
}
