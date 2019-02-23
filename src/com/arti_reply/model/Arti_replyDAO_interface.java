package com.arti_reply.model;

import java.util.List;

public interface Arti_replyDAO_interface {
	public void insert(Arti_replyVO arti_replyVO);
	public void update(Arti_replyVO arti_replyVO);
	public void delete(String arti_no, Integer rep_no);
	public Arti_replyVO findByPrimaryKey(Integer rep_no);
	public List<Arti_replyVO> getAll();
	
	public List<Arti_replyVO> getRepByArtiNO(String arti_no);
	public void deleteAllRep(String arti_no);
	
}
