package com.fborder.model;

import java.sql.Timestamp;
import java.util.List;

import com.android.model.BuyerOrderVO;
import com.fbitem.model.FbItemVO;

public class FbOrderService {
	private FbOrderDAO_interface dao;

	public FbOrderService() {
		dao = new FbOrderDAO();
	}

	public FbOrderVO addOrder(String fb_buyer_no, Timestamp fb_order_time, Integer fb_order_prc, String fb_order_trans,
			String fb_order_status, String fb_pay_status, String fb_order_remark, String fb_transport) {

		FbOrderVO fbOrderVO = new FbOrderVO();

		fbOrderVO.setFb_buyer_no(fb_buyer_no);
		fbOrderVO.setFb_order_time(fb_order_time);
		fbOrderVO.setFb_order_prc(fb_order_prc);
		fbOrderVO.setFb_order_trans(fb_order_trans);
		fbOrderVO.setFb_order_status(fb_order_status);
		fbOrderVO.setFb_pay_status(fb_pay_status);
		fbOrderVO.setFb_order_remark(fb_order_remark);
		fbOrderVO.setFb_transport(fb_transport);
		dao.insert(fbOrderVO);

		return fbOrderVO;
	}

	public FbOrderVO updateOrder(String fb_order_no, String fb_buyer_no, Timestamp fb_order_time, Integer fb_order_prc,
			String fb_order_trans, String fb_order_status, String fb_pay_status, String fb_order_remark,String fb_transport) {

		FbOrderVO fbOrderVO = new FbOrderVO();

		fbOrderVO.setFb_order_no(fb_order_no);
		fbOrderVO.setFb_buyer_no(fb_buyer_no);
		fbOrderVO.setFb_order_time(fb_order_time);
		fbOrderVO.setFb_order_prc(fb_order_prc);
		fbOrderVO.setFb_order_trans(fb_order_trans);
		fbOrderVO.setFb_order_status(fb_order_status);
		fbOrderVO.setFb_pay_status(fb_pay_status);
		fbOrderVO.setFb_order_remark(fb_order_remark);
		fbOrderVO.setFb_transport(fb_transport);
		dao.update(fbOrderVO);

		return fbOrderVO;
	}

	public void deleteOrder(String fb_order_no) {
		dao.delete(fb_order_no);
	}

	public FbOrderVO getOneOrder(String fb_order_no) {
		return dao.findByPrimaryKey(fb_order_no);
	}

	public List<FbOrderVO> getAll() {
		return dao.getAll();
	}
	
	public List<FbOrderVO> getMyFBOrder(String item_owner){
		return dao.getMyFBOrder(item_owner);
		
	}
	public List<BuyerOrderVO> getAllOrderItem(String fb_order_no){
		return dao.getAllOrderItem(fb_order_no);
		
	}
	
	//新增訂單及同時新增訂單細項
	public FbOrderVO insertFbOrder(String fb_buyer_no, Timestamp fb_order_time, Integer fb_order_prc,
			String fb_order_trans, String fb_order_status, String fb_pay_status, String fb_order_remark,String fb_transport, List<FbItemVO> list) {
		FbOrderVO fbOrderVO = new FbOrderVO();
		fbOrderVO.setFb_buyer_no(fb_buyer_no);
		fbOrderVO.setFb_order_time(fb_order_time);
		fbOrderVO.setFb_order_prc(fb_order_prc);
		fbOrderVO.setFb_order_trans(fb_order_trans);
		fbOrderVO.setFb_order_status(fb_order_status);
		fbOrderVO.setFb_pay_status(fb_pay_status);
		fbOrderVO.setFb_order_remark(fb_order_remark);
		fbOrderVO.setFb_transport(fb_transport);
		
		dao.insertFbOrder(fbOrderVO, list);
		return fbOrderVO;
	}
	//取得一個會員的所有fb訂單(買家)
	public List<FbOrderVO> getBuyFbOrder(String fb_buyer_no){
		return dao.getMyOrderForBuyer(fb_buyer_no);
	}

	//找一張訂單的賣家編號
	public String getOrderSeller(String fb_order_no) {
		return dao.getOrderSeller(fb_order_no);
	}
}
