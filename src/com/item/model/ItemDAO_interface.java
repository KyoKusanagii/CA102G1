package com.item.model;

import java.util.List;

public interface ItemDAO_interface {
	// 此介面定義對資料庫的相關存取抽象方法
	String insert(ItemVO item);
	void update(ItemVO item);
	void delete(String item_no);
	ItemVO findByPK(String item_no);
	List<String> findItemPics(String item_no);
	List<ItemVO> getAll(String login_mem);
	void updateLaunch(ItemVO item);
	List<ItemVO> findByCat(Integer item_primary_class);
	List<ItemVO> findByPart(Integer item_secondary_class);
	List<ItemVO> findByKeyWord(String item_name);

}