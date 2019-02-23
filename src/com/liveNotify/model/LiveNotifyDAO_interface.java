package com.liveNotify.model;


import java.util.List;

public interface LiveNotifyDAO_interface {
	// 此介面定義對資料庫的相關存取抽象方法
	void add(LiveNotifyVO ln);
	void update(LiveNotifyVO ln);
	void delete(String live_No);
	LiveNotifyVO findByPK(String live_No);
	List<LiveNotifyVO> getAll();

}