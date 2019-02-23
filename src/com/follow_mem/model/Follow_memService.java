package com.follow_mem.model;

import java.sql.Date;
import java.util.List;

import com.live.model.LiveVO;
import com.member.model.MemVO;


public class Follow_memService {

private  Follow_memDAO_interface dao;
	
	public Follow_memService() {
		dao = new Follow_memDAO();
	}
	
	public Follow_memVO add(String folo_mem_no, String foloed_mem_no, Date folo_time) {
		
		Follow_memVO follow_memVO = new Follow_memVO();
		
		follow_memVO.setFolo_mem_no(folo_mem_no);;
		follow_memVO.setFoloed_mem_no(foloed_mem_no);
		follow_memVO.setFolo_time(folo_time);
	
		dao.insert(follow_memVO);
		
		return follow_memVO;
	}
	
	public Follow_memVO findByPK(String folo_mem_no, String foloed_mem_no) {
		 Follow_memVO follow_memVO = new Follow_memVO();
		 follow_memVO.setFolo_mem_no(folo_mem_no);
		 follow_memVO.setFoloed_mem_no(foloed_mem_no);
		 Follow_memVO cnt = dao.findByPrimaryKey(folo_mem_no, foloed_mem_no);
		 return cnt;
		
		
	}
	
	public void delete(String folo_mem_no, String foloed_mem_no) {
		dao.delete(folo_mem_no, foloed_mem_no);
	}
	
	public List<Follow_memVO> getAll(String folo_mem_no){
		return dao.getAll(folo_mem_no);
	}
	
	public List<LiveVO> getFollowAddress(String FOLLOW_MEM_NO){
		return dao.getFollowAddress(FOLLOW_MEM_NO);
	}
	
	public List<MemVO> getAllFans(String foloed_mem_no){
		return dao.getAllFans(foloed_mem_no);
	}
	
}
