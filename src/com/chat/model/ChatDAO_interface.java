package com.chat.model;

import java.util.List;

import com.member.model.MemVO;

public interface ChatDAO_interface {
	// 此介面定義對資料庫的相關存取抽象方法
	void insert(ChatVO chat_master);
	void update(ChatVO chat_master);
	void delete(String chat_maste_no);
	ChatVO findByPK(String chat_master_no);
	List<MemVO> getAll(String chat_master_no);	//回傳所有朋友的集合，即為會員的集合

}