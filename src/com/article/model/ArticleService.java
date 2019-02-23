package com.article.model;

import java.sql.Timestamp;
import java.util.List;

public class ArticleService {
	
	private ArticleDAO_interface dao;
	
	public ArticleService() {
		dao = new ArticleDAO();
	}
	
	public ArticleVO addArticle(String arti_topic,String mem_no ,String arti_content, Timestamp po_time) {
		
		ArticleVO articleVO = new ArticleVO();
		
		articleVO.setArti_topic(arti_topic);
		articleVO.setMem_no(mem_no);
		articleVO.setArti_content(arti_content);
		articleVO.setPo_time(po_time);
	
		dao.insert(articleVO);
		
		return articleVO;
	}
	
	public ArticleVO getOneArticle(String arti_no) {
		
		return dao.findByPrimaryKey(arti_no);
	
	}
	
	
	
	public ArticleVO updateArticle(String arti_topic, String arti_content, java.sql.Timestamp po_time, String arti_no) {
		
		ArticleVO articleVO = new ArticleVO();
		
		articleVO.setArti_topic(arti_topic);
		articleVO.setArti_content(arti_content);
		articleVO.setPo_time(po_time);
		articleVO.setArti_no(arti_no);
		dao.update(articleVO);
		
		return articleVO;
	}
	
	public void deleteArticle(String arti_no) {
		dao.delete(arti_no);
	}
	
	
	
	public List<ArticleVO> getArticle(String arti_topic) { //·j´M¥Î
		return dao.findByTopic(arti_topic);
	}
	
	public List<ArticleVO> getAll(){
		return dao.getAll();
	}
	
	
}
