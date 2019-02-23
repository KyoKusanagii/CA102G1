package com.article.model;

import java.util.List;

import com.article.model.ArticleVO;

public interface ArticleDAO_interface {
	public void insert(ArticleVO articleVO);
	public void update(ArticleVO articleVO);
	public void delete(String arti_no);
	public ArticleVO findByPrimaryKey(String arti_no);
	public List<ArticleVO> getAll();
	
	
	public List<ArticleVO> findByTopic(String arti_topic);
}
