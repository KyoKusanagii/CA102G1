package com.shoppingCart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.item.model.*;
import com.mallitem.model.MallItemVO;
import com.mallorder.model.MallOrderDAO;
import com.mallorder.model.MallOrderService;
import com.mallorder.model.MallOrderVO;
import com.member.model.MemService;
import com.member.model.MemVO;




@WebServlet("/ShoppingCartServlet")
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();

		@SuppressWarnings("unchecked")
		List<ItemVO> buylist = (Vector<ItemVO>) session.getAttribute("shoppingCart");
		String action = req.getParameter("action");

		if (!action.equals("CHECKOUT")) {

			// �R���ʪ��������ӫ~
			if (action.equals("DELETE")) {
				String del = req.getParameter("del");
				ItemService itemSvc = new ItemService();
				ItemVO delVO = itemSvc.getOneItem(del);
				buylist.remove(delVO);
				System.out.println("�R�����\");
				
				//���ӫ~�Q�R�����`���B��ۧ���
				int total = 0;
				for (int i = 0; i < buylist.size(); i++) {
					ItemVO order = buylist.get(i);
					Integer price = order.getPrice();
					Integer quantity = order.getQuantity();
					total += (price * quantity);
				}
				
				//System.out.println("total=" + total);
				
				//�^���`���B��ajax
				PrintWriter out =res.getWriter();
				out.print(total);
			
			}
			// �s�W�ӫ~���ʪ�����
			else if (action.equals("ADD")) {
				// ���o��ӷs�W���ӫ~
				ItemVO aItem = getItem(req);

				if (buylist == null) {
					buylist = new Vector<ItemVO>();
					buylist.add(aItem);
					
				} else {
					if (buylist.contains(aItem)) {
						ItemVO innerItem = buylist.get(buylist.indexOf(aItem));
						innerItem.setQuantity(innerItem.getQuantity() + aItem.getQuantity());
					} else {
						buylist.add(aItem);
					}
				}
			}

			session.setAttribute("shoppingCart", buylist);
			
			
		}

		// ���b
		else if (action.equals("CHECKOUT")) {
			
			if(buylist.size() == 0) {
				String url = "/FrontEnd/member/shoppingCart.jsp";
				res.sendRedirect(req.getContextPath()+url);
				return;
			}
			
			int total = 0;
			
			
			for (int i = 0; i < buylist.size(); i++) {
				ItemVO order = buylist.get(i);
				Integer price = order.getPrice();
				Integer quantity = order.getQuantity();
				total += (price * quantity);
			}
			
			
			
			String amount = String.valueOf(total);
			req.getSession().setAttribute("amount", amount);
			String url = "FrontEnd/member/checkout.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
			
		}
		
		if (action.equals("updateQuantity")) {

			
			ItemVO aItem = getItem(req);
			ItemVO innerItem = buylist.get(buylist.indexOf(aItem));
			innerItem.setQuantity(aItem.getQuantity());
			
				
			//�N�ϥΪ̦b�ʪ��������ܪ��ӫ~�ƶq�s�_��
			session.setAttribute("shoppingCart", buylist);
			
			//���ʪ������ӫ~�ƶq���ܰʮ��`�p����ܰ�
			int total = 0;
			for (int i = 0; i < buylist.size(); i++) {
				ItemVO order = buylist.get(i);
				Integer price = order.getPrice();
				Integer quantity = order.getQuantity();
				total += (price * quantity);
			}
			
			//System.out.println("total=" + total);
			
			//�^���`���B��ajax
			PrintWriter out =res.getWriter();
			out.print(total);
		}
		
		
		if(action.equals("cleanCart")) {
			
			session.removeAttribute("shoppingCart");
			System.out.println("�M���o");
			
			String url = "/FrontEnd/member/shoppingCart.jsp";
			res.sendRedirect(req.getContextPath()+url);
		}
		
		//�s�W�q��
		if(action.equals("insertOrder")) {
			
		    Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
		    req.setAttribute("errorMsgs", errorMsgs);

		   try {
			    /***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				MemVO memVO = (MemVO)session.getAttribute("memVO");
				String mall_buyer_no = memVO.getMem_no();
				Timestamp mall_order_time = new Timestamp(System.currentTimeMillis());
				
				String mall_order_trans = req.getParameter("mall_order_trans");
//				System.out.println("mall_order_trans=" + mall_order_trans);
				if(mall_order_trans == null || mall_order_trans.trim().length() == 0) {
					errorMsgs.put("mall_order_trans","�п�ܥI�ڤ覡");
				}
				String mall_order_status = "�i�椤";
				String mall_pay_status = "";
				if("�H�Υd".equals(mall_order_trans)) {
					mall_pay_status = "�w�I��";
				}else {
					mall_pay_status = "���I��";
				}
				 
				String mall_order_remark = req.getParameter("mall_order_remark");
				String mall_transport = "�l�H";
				
				
				String county = req.getParameter("county");
//				System.out.println("county = " + county);
				String district = req.getParameter("district");
//				System.out.println("district = " + district);
				String zipcode = req.getParameter("zipcode");
//				System.out.println("zip = " + zipcode);
				if(county == null || district == null || zipcode == null) {
					errorMsgs.put("add","�ж�J����a�}");
				}
				
				String add = req.getParameter("add");
				if(add == null || add.trim().length() == 0 ) {
					errorMsgs.put("add","�п�J�a�}");
				}
				
				String receiveadd = zipcode +" "+ county + district + add;
				
				/******************************************************************/
				String receiver = req.getParameter("receiver");
				if(receiver == null || receiver.trim().length() == 0) {
					errorMsgs.put("receiver","�п�J����H");
				}
				
				String phone = req.getParameter("phone");
				String mem_PhoneReg = "^(0)(9)([0-9]{8})$";
				if(phone == null || phone.trim().length() == 0) {
					errorMsgs.put("phone","�п�J�q��");
				}else if(!(phone.trim()).matches(mem_PhoneReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.put("phone","����榡�����T");
	            }
				req.setAttribute("phone", phone);
				
				
				if("�H�Υd".equals(mall_order_trans)) {
					String cardowner = req.getParameter("cardowner");
					if(cardowner == null || cardowner.trim().length() == 0) {
						errorMsgs.put("cardowner","�п�J���d�H�m�W");
					}
					
					String cdno1 = req.getParameter("cdno1");
					String cdno2 = req.getParameter("cdno2");
					String cdno3 = req.getParameter("cdno3");
					String cdno4 = req.getParameter("cdno4");
					String check = "^([0-9]{4})$";
					if(cdno1.trim().length() == 0 || cdno2.trim().length() == 0 || cdno3.trim().length() == 0 || cdno4.trim().length() == 0){
						errorMsgs.put("cdno","�ж�g�H�Υd��");
					}else if(!(cdno1.trim().matches(check)) || !(cdno2.trim().matches(check)) || !(cdno3.trim().matches(check)) || !(cdno4.trim().matches(check))) {
						errorMsgs.put("cdno","�H�Υd�������T");
					}
					String expire_month = req.getParameter("expire_month");
					String expire_year = req.getParameter("expire_year");
					if(expire_month.trim().length() == 0  || expire_year.trim().length() == 0) {
						errorMsgs.put("expire","�ж��H�Υd�I���");
					}
					String expire_safe = req.getParameter("expire_safe");
					String check_safe = "^([0-9]{3})$";
					if(expire_safe == null || expire_safe.trim().length() != 3 || !(expire_safe.trim().matches(check_safe))) {
						errorMsgs.put("safe","�w���X�����T�X�Ʀr");
					}
					req.setAttribute("cardowner", cardowner);
					req.setAttribute("cdno1", cdno1);
					req.setAttribute("cdno2", cdno2);
					req.setAttribute("cdno3", cdno3);
					req.setAttribute("cdno4", cdno4);
					req.setAttribute("expire_month", expire_month);
					req.setAttribute("expire_year", expire_year);
					req.setAttribute("expire_safe", expire_safe);

				}
				/******************************************************************/
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mall_order_remark", mall_order_remark);
					req.setAttribute("zipcode", zipcode);
					req.setAttribute("phone", phone);
					req.setAttribute("zipcode", zipcode);
					req.setAttribute("add", add);
					
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/member/checkout.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				
				/***************************2.�}�l�s�W���***************************************/
				
				ItemService itemSvc = new ItemService();
				//���X���R�ӫ~���U�ӽ�a�s��
				Set<String> seller_id = new HashSet<String>();
				for(int i = 0; i < buylist.size(); i++ ) {
					ItemVO order = buylist.get(i);
					seller_id.add(itemSvc.getOneItem(order.getItem_no()).getItem_owner());	
				}
//				System.out.println("seller_id="+seller_id.size());

				//�j�i�q��̾ڤ��P��a�����h�i�p�q��
				for(int i = 0; i< seller_id.size(); i++) {
					Integer order_prc = 0;
					List<ItemVO> list = null;
					
					//���F�s�q��Ӷ�
					List<MallItemVO> orderList = new ArrayList<MallItemVO>();
					
					for(int j = 0; j < buylist.size(); j++ ) {
						ItemVO order = buylist.get(j);
						
						if(seller_id.toArray()[i].equals(itemSvc.getOneItem(order.getItem_no()).getItem_owner())) {
							System.out.println(seller_id.toArray()[i]);
							list = new ArrayList<ItemVO>();
							list.add(order);
							order_prc += order.getPrice()*order.getQuantity();
							
							MallItemVO mallitemVO = new MallItemVO();
							mallitemVO.setItem_no(order.getItem_no());
							mallitemVO.setMall_item_prc(order.getPrice());
							mallitemVO.setMall_item_cnt(order.getQuantity());
							mallitemVO.setIs_item_sale(0);
							orderList.add(mallitemVO);
							
						}
						
					}
					
					//�s�W�q��M�q��Ӷ�
					MallOrderService mallOrderSvc = new MallOrderService();	
				
					mallOrderSvc.insertMallOrder(mall_buyer_no, mall_order_time, order_prc, mall_order_trans, mall_order_status, mall_pay_status, mall_order_remark, mall_transport, orderList);	
					System.out.println("�s�W���\");
					
					//�ǰe²�T����a
					MemService memSvc = new MemService();
					String mem_no = (String) seller_id.toArray()[i];
					MemVO seller = memSvc.findByPK(mem_no);
					if(seller.getMem_mobile().trim() != null) {
						String seller_mobile = seller.getMem_mobile().trim();
						String seller_name = seller.getMem_name();
						
						String[] tel = {seller_mobile};
						Send se = new Send();
						String message = "You got new order�I";
						se.sendMessage(tel , message);
					}
					
				}
				
			//��²�T���R�a
		
			Send se = new Send();
			String[] tel = {phone};
			if("�Ȧ���b".equals(mall_order_trans)) {
				String message = "Thank you for your order!!";			
				se.sendMessage(tel , message);
			}else {
				String message = "Thank you for your order!!" ;
				se.sendMessage(tel , message);
			}
			
			MemService memSvc = new MemService();
			memSvc.updateReceiveAdd(receiveadd, mall_buyer_no);
			session.setAttribute("memVO", memVO); // ��Ʈwupdate���\��,���T����memVO����,�s�Jreq
//			System.out.println("�ק�a�}���\");
			session.removeAttribute("shoppingCart");
				
			Thread.sleep(2000);
		    /***************************3.�s�W����,�ǳ����(Send the Success view)***********/  
			//���F����ܭq��I�ڪ��A
			req.getSession().setAttribute("mall_pay_status", mall_pay_status);
			String url = req.getContextPath() + "/FrontEnd/member/orderSuccess.jsp";
		    res.sendRedirect(url);
		    
	
		    /***************************��L�i�઺���~�B�z**********************************/
		   }  catch (Exception e) {
		    errorMsgs.put("Exception",e.getMessage());
		    RequestDispatcher failureView = req
		      .getRequestDispatcher("/FrontEnd/index.jsp");
		    failureView.forward(req, res);
		   }
		
	
		}
		
	
	}
		
		
	
	private ItemVO getItem(HttpServletRequest req) {

		String item_no = req.getParameter("item_no");
//		System.out.println("item_no="+item_no);
		String quantity = req.getParameter("quantity");
//		System.out.println("quantity="+quantity);
		String price = req.getParameter("item_price").trim();
//		System.out.println("price="+item_price);
		
		

		ItemVO item = new ItemVO();

		item.setItem_no(item_no);
		item.setQuantity((new Integer(quantity)).intValue());
		item.setPrice((new Integer(price)).intValue());
		
		return item;
	}
		
}
