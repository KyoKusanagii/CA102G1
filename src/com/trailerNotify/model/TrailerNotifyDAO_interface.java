package com.trailerNotify.model;


import java.util.List;

public interface TrailerNotifyDAO_interface {
	// 此介面定義對資料庫的相關存取抽象方法
	void add(TrailerNotifyVO tn);
	void update(TrailerNotifyVO tn);
	void delete(String trailer_No);
	TrailerNotifyVO findByPK(String trailer_No);
	List<TrailerNotifyVO> getAll();

}