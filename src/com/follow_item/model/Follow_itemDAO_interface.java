package com.follow_item.model;

import java.util.List;

public interface Follow_itemDAO_interface {
	public void insert(Follow_itemVO follow_itemVO);
	public void delete(String mem_no, String item_no);
	public Follow_itemVO findByPrimaryKey(String mem_no, String item_no);
	//取得一個會員的所有關注商品
	public List<Follow_itemVO> getAll(String mem_no);
}
