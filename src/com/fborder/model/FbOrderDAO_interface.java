package com.fborder.model;

import java.util.List;

import com.android.model.BuyerOrderVO;
import com.fbitem.model.FbItemVO;


public interface FbOrderDAO_interface {
	public void insert(FbOrderVO fbOrderVO);
	public void update(FbOrderVO fbOrderVO);
	public void delete(String fb_order_no);
	public FbOrderVO findByPrimaryKey(String fb_order_no);
	public List<FbOrderVO> getAll();
	public List<FbOrderVO> getMyFBOrder(String item_owner);
	public List<BuyerOrderVO> getAllOrderItem(String fb_order_no);
	public void insertFbOrder(FbOrderVO fbOrderVO, List<FbItemVO> list);
	
	//取得會員所有fb訂單(買家)
	public List<FbOrderVO> getMyOrderForBuyer(String buyerNo);
	//取得訂單的賣家編號
	public String getOrderSeller(String fb_order_no);

}
