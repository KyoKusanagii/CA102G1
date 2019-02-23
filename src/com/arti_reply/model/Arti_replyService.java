package com.arti_reply.model;

import java.sql.Timestamp;
import java.util.List;

import com.article.model.ArticleVO;



public class Arti_replyService {
	
	private Arti_replyDAO_interface dao;
	
	public Arti_replyService() {
		dao = new Arti_replyDAO();
	}
	
	public Arti_replyVO addRep(String mem_no, String rep_content, Timestamp rep_time, String arti_no) {
		
		Arti_replyVO arti_replyVO = new Arti_replyVO();
		
		arti_replyVO.setMem_no(mem_no);
		arti_replyVO.setRep_content(rep_content);
		arti_replyVO.setRep_time(rep_time);
		arti_replyVO.setArti_no(arti_no);
	
		dao.insert(arti_replyVO);
		
		return arti_replyVO;
	}
	
	public Arti_replyVO updateRep(String rep_content, Timestamp rep_time, String arti_no, Integer rep_no) {
		
		Arti_replyVO arti_replyVO = new Arti_replyVO();
		
		arti_replyVO.setRep_content(rep_content);
		arti_replyVO.setRep_time(rep_time);
		arti_replyVO.setArti_no(arti_no);
		arti_replyVO.setRep_no(rep_no);
		dao.update(arti_replyVO);
		
		return arti_replyVO;
	}
	
	public void deleteRep(String arti_no, Integer rep_no) {
		dao.delete(arti_no, rep_no);
	}
	
	public List<Arti_replyVO> getAll(){
		return dao.getAll();
	}
	
	public List<Arti_replyVO> getRepByArtiNO(String arti_no){
		return dao.getRepByArtiNO(arti_no);
	}
	
	public Arti_replyVO getOneRep(Integer rep_no) {
		return dao.findByPrimaryKey(rep_no);	
	}
	
	public void deleteAllRep(String arti_no) {
		dao.deleteAllRep(arti_no);
	}
	
}
