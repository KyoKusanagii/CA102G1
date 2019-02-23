package com.mallitem.model;

import java.sql.Connection;
import java.util.List;


public interface MallItemDAO_interface {

	public void insert(MallItemVO mallItemVO);
	public void update(MallItemVO mallItemVO);
	public void delete(String mall_order_no, String item_no);
	public MallItemVO findByPrimaryKey(String mall_order_no,String item_no);
	public List<MallItemVO> getAll();
	void insertOrder(MallItemVO mallItemVO, Connection con2);

}
