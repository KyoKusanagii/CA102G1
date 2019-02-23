package com.member.model;

import java.util.List;
import java.util.Map;

public interface MemDAO_interface {
	// 此介面定義對資料庫的相關存取抽象方法
	void insert(MemVO mem);
	void update(MemVO mem);
	void delete(String mem_no);
	MemVO findByPK(String mem_no);
	List<MemVO> getAll();
	List<MemVO> getAllDesc();
	void signup(MemVO mem);
	void setAuth(MemVO mem);
	MemVO checkID(String mem_id);
	//登入
	MemVO login(String mem_id, String mem_psw);
	
	MemVO findByID(String mem_id);
	
	// 8/1 Hugh新增:尋找賣場
	List<MemVO> searchMall(String mem_martname);
	
	String findNewestMember();
	
	// 8/7 Hugh新增信箱驗證
	void activeMem(MemVO mem);
	
	Map<String, byte[]> getPicByNO();
	Map<String, String> getNameByNO();
	
	//修改賣場資料
	void updateStore(MemVO mem);
	//修改會員
	void updateMem(MemVO mem);
	//修改密碼
	void updatePsw(MemVO mem);
	
	// 8/10Hugh新增：FB登入
	void fbSignup(MemVO mem);
	
	// 8/11Hugh新增：假設FB名字、圖片有換過
	void fbNamePicture(MemVO mem);
	
	// 8/14Hugh新增：確認名字有無註冊過
	MemVO checkFBName(String mem_name);
	
	// 8/14Hugh新增：FB登入
	MemVO fbLogin(String mem_name, String mem_psw);
	
	// 8/14Hugh新增：FB登入
	void fbFullInfo(MemVO mem);
	
	void updateReceiveadd(MemVO mem);
	
}