package com.mallorder.model;

import java.sql.Timestamp;
import java.util.List;

import com.android.model.BuyerOrderVO;
import com.mallitem.model.MallItemVO;

public class MallOrderService {
	private MallOrderDAO_interface dao;

	public MallOrderService() {
		dao = new MallOrderDAO();
	}


	public MallOrderVO addOrder(String mall_buyer_no, Timestamp mall_order_time, Integer mall_order_prc, String mall_order_trans,
			String mall_order_status, String mall_pay_status, String mall_order_remark, String mall_transport) {

		MallOrderVO mallOrderVO = new MallOrderVO();

		mallOrderVO.setMall_buyer_no(mall_buyer_no);
		mallOrderVO.setMall_order_time(mall_order_time);
		mallOrderVO.setMall_order_prc(mall_order_prc);
		mallOrderVO.setMall_order_trans(mall_order_trans);
		mallOrderVO.setMall_order_status(mall_order_status);
		mallOrderVO.setMall_pay_status(mall_pay_status);
		mallOrderVO.setMall_order_remark(mall_order_remark);
		mallOrderVO.setMall_transport(mall_transport);
		dao.insert(mallOrderVO);
	
		return mallOrderVO;
	}

	public MallOrderVO updateOrder(String mall_order_no, String mall_buyer_no, Timestamp mall_order_time, Integer mall_order_prc,
			String mall_order_trans, String mall_order_status, String mall_pay_status, String mall_order_remark,String mall_transport) {

		MallOrderVO mallOrderVO = new MallOrderVO();

		mallOrderVO.setMall_order_no(mall_order_no);
		mallOrderVO.setMall_buyer_no(mall_buyer_no);
		mallOrderVO.setMall_order_time(mall_order_time);
		mallOrderVO.setMall_order_prc(mall_order_prc);
		mallOrderVO.setMall_order_trans(mall_order_trans);
		mallOrderVO.setMall_order_status(mall_order_status);
		mallOrderVO.setMall_pay_status(mall_pay_status);
		mallOrderVO.setMall_order_remark(mall_order_remark);
		mallOrderVO.setMall_transport(mall_transport);
		dao.update(mallOrderVO);
		
		return mallOrderVO;

	}

	public void deleteOrder(String mall_order_no) {
		dao.delete(mall_order_no);
	}

	public MallOrderVO getOneOrder(String mall_order_no) {
		return dao.findByPrimaryKey(mall_order_no);
	}

	public List<MallOrderVO> getAll() {
		return dao.getAll();
	}
	
	public List<MallOrderVO> getMyMallOrder(String item_owner){
		return dao.getMyMallOrder(item_owner);
		
	}
	public List<BuyerOrderVO> getAllOrderItem(String mall_order_no){
		return dao.getAllOrderItem(mall_order_no);
		
	}
	
	//新增訂單及同時新增訂單細項
	public MallOrderVO insertMallOrder(String mall_buyer_no, Timestamp mall_order_time, Integer mall_order_prc,
			String mall_order_trans, String mall_order_status, String mall_pay_status, String mall_order_remark,String mall_transport, List<MallItemVO> list) {
		
		MallOrderVO mallOrderVO = new MallOrderVO();
		mallOrderVO.setMall_buyer_no(mall_buyer_no);
		mallOrderVO.setMall_order_time(mall_order_time);
		mallOrderVO.setMall_order_prc(mall_order_prc);
		mallOrderVO.setMall_order_trans(mall_order_trans);
		mallOrderVO.setMall_order_status(mall_order_status);
		mallOrderVO.setMall_pay_status(mall_pay_status);
		mallOrderVO.setMall_order_remark(mall_order_remark);
		mallOrderVO.setMall_transport(mall_transport);
		
		dao.insertMallOrder(mallOrderVO, list);
		return mallOrderVO;
	}
	//找一個會員的所有商城訂單(買家)
	public List<MallOrderVO> getBuyerMallOrder(String mall_buyer_no){
		return dao.getMyOrderForBuyer(mall_buyer_no);
	}
	//找一張訂單的賣家編號
	public String getOrderSeller(String mall_order_no) {
		return dao.getOrderSeller(mall_order_no);
	}
	
	//找出商品大項的各分類銷售額
		public List<List<Integer>> revenuesByCat(){
			return dao.revenuesByCat();
		}
	
}

