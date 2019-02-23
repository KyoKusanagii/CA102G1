package com.mallorder.model;

import java.util.List;

import com.android.model.BuyerOrderVO;
import com.mallitem.model.MallItemVO;

public interface MallOrderDAO_interface {
	
	public void insert(MallOrderVO mallOrderVO);
	public void update(MallOrderVO mallOrderVO);
	public void delete(String mall_order_no);
	public MallOrderVO findByPrimaryKey(String mall_order_no);
	public List<MallOrderVO> getAll();
	public List<BuyerOrderVO> getAllOrderItem(String mall_order_no);
	public List<MallOrderVO> getMyMallOrder(String item_owner);
	
	//新增訂單及同時新增訂單細項
	public void insertMallOrder(MallOrderVO mallOrderVO, List<MallItemVO> list);
	//找一個會員的所有商城訂單(買家)
	public List<MallOrderVO> getMyOrderForBuyer(String mall_buyer_no);
	//找一張訂單的賣家編號
	public String getOrderSeller(String mall_order_no);

	//找出商品大項的各分類銷售額
	public List<List<Integer>> revenuesByCat();
}
