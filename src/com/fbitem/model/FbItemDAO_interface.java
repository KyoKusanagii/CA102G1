package com.fbitem.model;

import java.util.List;


public interface FbItemDAO_interface {
	public void insert(FbItemVO fbItemVO);
	public void update(FbItemVO fbItemVO);
	public void delete(String fb_order_no, String item_no);
	public FbItemVO findByPrimaryKey(String fb_order_no,String item_no);
	public List<FbItemVO> getAll();

}
