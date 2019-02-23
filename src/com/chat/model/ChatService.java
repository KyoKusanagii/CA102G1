package com.chat.model;

import java.util.List;

import com.member.model.MemVO;

public class ChatService {
	
	private ChatDAO_interface dao;
	
	public ChatService() {
		
		dao = new ChatDAO();
	}
	
	public void addFrined(String chat_master_no,String chat_friends) {	//兩方都要互加
		
		ChatVO chat_master = new ChatVO();
		chat_master.setChat_master_no(chat_master_no);
		chat_master.setChat_friends(chat_friends);
		dao.insert(chat_master);
	}
	
	public List<MemVO> getAllFriends(String chat_master_no){
		
		return dao.getAll(chat_master_no);
	}
	
}
