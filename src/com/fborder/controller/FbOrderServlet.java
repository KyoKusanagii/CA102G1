package com.fborder.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fborder.model.FbOrderDAO;
import com.fborder.model.FbOrderService;
import com.fborder.model.FbOrderVO;
import com.member.model.MemDAO;
import com.member.model.MemService;
import com.member.model.MemVO;

@WebServlet("/FrontEnd/fborder/fbOrder.do")
public class FbOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
	

		if ("cancel_order".equals(action)) { // 來自listAllEmp.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String fb_order_no = new String(req.getParameter("fb_order_no"));
				String fb_seller = new String(req.getParameter("fb_seller"));
				/*************************** 2.開始查詢資料 ****************************************/
				FbOrderService fbOrderSvc = new FbOrderService();
				FbOrderVO fbOrderVO = fbOrderSvc.getOneOrder(fb_order_no);
				fbOrderVO.setFb_order_status("取消");

				FbOrderDAO dao = new FbOrderDAO();
				dao.update(fbOrderVO);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("fbOrderVO", fbOrderVO); // 資料庫取出的fbOrderVO物件,存入req

				MemDAO memdao = new MemDAO();
				String buyer_ch_name = memdao.findByPK(fbOrderVO.getFb_buyer_no()).getMem_name();
				String to_buyer = memdao.findByPK(fbOrderVO.getFb_buyer_no()).getMem_email();
				String seller_ch_name = memdao.findByPK(fb_seller).getMem_name();
				String to_seller = memdao.findByPK(fb_seller).getMem_email();

				String subject = "InstaBuy的訂單相關重要通知";
				String content = "您的facebook直播拍賣訂單:"+ fbOrderVO.getFb_order_no() +"已被取消,請登入InstaBuy查看確認，謝謝!!";
				String buyer_website = "<a href='https://ca102g1.tk/CA102G1/FrontEnd/member/mem_fbOrder.jsp'>"
						+ "按我確認</a>";
				String seller_website = "<a href='https://ca102g1.tk/CA102G1/FrontEnd/fborder/fbOrder.jsp'>"
						+ "按我確認</a>";
				String buyer_messageText = "Hello! " + buyer_ch_name + "<br>" + content + "<br>" + buyer_website;
				String seller_messageText = "Hello! " + seller_ch_name + "<br>" + content + "<br>" + seller_website;

				MailService mailService = new MailService();
				mailService.sendMail(to_buyer, subject, buyer_messageText);
				mailService.sendMail(to_seller, subject, seller_messageText);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/fborder/fbOrder.jsp");
				failureView.forward(req, res);
			}
		}

		

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String fb_order_no = new String(req.getParameter("fb_order_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				FbOrderService fbOrderSvc = new FbOrderService();
				fbOrderSvc.deleteOrder(fb_order_no);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/FrontEnd/fborder/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/fborder/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("buyer_cancel_order".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
		
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String fb_order_no = req.getParameter("fb_order_no");
				String fb_buyer_no = req.getParameter("fb_buyer_no");
				System.out.println(fb_order_no);
				System.out.println(fb_buyer_no);
				/*************************** 2.開始查詢資料 ****************************************/
				FbOrderService fbOrderSvc = new FbOrderService();
				FbOrderVO fbOrderVO = fbOrderSvc.getOneOrder(fb_order_no);
				fbOrderVO.setFb_order_status("取消");
				

				FbOrderDAO dao = new FbOrderDAO();
				dao.update(fbOrderVO);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("fbOrderVO", fbOrderVO); // 資料庫取出的fbOrderVO物件,存入req
//				MemDAO memdao = new MemDAO();
//				MallOrderService mallorderSvc = new MallOrderService();
//				String buyer_ch_name = memdao.findByPK(mall_buyer_no).getMem_name();
//				String to_buyer = memdao.findByPK(mall_buyer_no).getMem_email();
//				String seller_ch_name = memdao.findByPK(mallorderSvc.getOrderSeller(mall_order_no)).getMem_name();
//				String to_seller = memdao.findByPK(mallorderSvc.getOrderSeller(mall_order_no)).getMem_email() ;
//				
//				String subject = "InstaBuy的訂單相關重要通知";
//				String content = "您的InstaBuy商城購物訂單:" + mallOrderVO.getMall_order_no() + "已被取消,請登入InstaBuy查看確認，謝謝!!";
//				String website = "<a href='https://www.ca102g1.tk/CA102G1/FrontEnd/mallorder/mallOrder.jsp'>"
//								+ "按我確認</a>";
//				String buyer_messageText = "Hello! " + buyer_ch_name + "<br>" + content + "<br>" + website;
//				String seller_messageText = "Hello! " + seller_ch_name + "<br>" + content + "<br>" + website;
//
//				MailService mailService = new MailService();
//				mailService.sendMail(to_buyer, subject, buyer_messageText);
//				mailService.sendMail(to_seller, subject, seller_messageText);
				
		
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/mallorder/mallOrder.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if(action.equals("checkFbOrder")) {
			
		    Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
		    req.setAttribute("errorMsgs", errorMsgs);

		   try {
			    /***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				MemVO memVO = (MemVO)session.getAttribute("memVO");
				String fb_buyer_no = memVO.getMem_no();
				String fb_order_no = req.getParameter("fb_order_no");
				String fb_order_trans = req.getParameter("fb_order_trans");

				if(fb_order_trans == null || fb_order_trans.trim().length() == 0) {
					errorMsgs.put("mall_order_trans","請選擇付款方式");
				}
				
				String fb_pay_status = "";
				if("信用卡".equals(fb_order_trans)) {
					fb_pay_status = "已付款";
				}else {
					fb_pay_status = "未付款";
				}
				 
				String fb_order_remark = req.getParameter("fb_order_remark");
				String fb_transport = req.getParameter("fb_transport");
				
				
				String county = req.getParameter("county");
//				System.out.println("county = " + county);
				String district = req.getParameter("district");
//				System.out.println("district = " + district);
				String zipcode = req.getParameter("zipcode");
//				System.out.println("zip = " + zipcode);
				if(county == null || district == null || zipcode == null) {
					errorMsgs.put("add","請填入完整地址");
				}
				
				String add = req.getParameter("add");
				if(add == null || add.trim().length() == 0 ) {
					errorMsgs.put("add","請輸入地址");
				}
				
				String receiveadd = zipcode +" "+ county + district + add;
				
				/******************************************************************/
				String receiver = req.getParameter("receiver");
				if(receiver == null || receiver.trim().length() == 0) {
					errorMsgs.put("receiver","請輸入收件人");
				}
				
				String phone = req.getParameter("phone");
				String mem_PhoneReg = "^(0)(9)([0-9]{8})$";
				if(phone == null || phone.trim().length() == 0) {
					errorMsgs.put("phone","請輸入電話");
				}else if(!(phone.trim()).matches(mem_PhoneReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("phone","手機格式不正確");
	            }
				req.setAttribute("phone", phone);
				
				
				if("信用卡".equals(fb_order_trans)) {
					String cardowner = req.getParameter("cardowner");
					if(cardowner == null || cardowner.trim().length() == 0) {
						errorMsgs.put("cardowner","請輸入持卡人姓名");
					}
					
					
					String cdno1 = req.getParameter("cdno1");
					String cdno2 = req.getParameter("cdno2");
					String cdno3 = req.getParameter("cdno3");
					String cdno4 = req.getParameter("cdno4");
					String check = "^([0-9]{4})$";
					if(cdno1.trim().length() == 0 || cdno2.trim().length() == 0 || cdno3.trim().length() == 0 || cdno4.trim().length() == 0){
						errorMsgs.put("cdno","請填寫信用卡號");
					}else if(!(cdno1.trim().matches(check)) || !(cdno2.trim().matches(check)) || !(cdno3.trim().matches(check)) || !(cdno4.trim().matches(check))) {
						errorMsgs.put("cdno","信用卡號不正確");
					}
					String expire_month = req.getParameter("expire_month");
					String expire_year = req.getParameter("expire_year");
					if(expire_month.trim().length() == 0  || expire_year.trim().length() == 0) {
						errorMsgs.put("expire","請填選信用卡截止日");
					}
					req.setAttribute("expire_month", expire_month);
					req.setAttribute("expire_year", expire_year);
					String expire_safe = req.getParameter("expire_safe");
					String check_safe = "^([0-9]{3})$";
					if(expire_safe == null || expire_safe.trim().length() != 3 || !(expire_safe.trim().matches(check_safe))) {
						errorMsgs.put("safe","安全碼應為三碼數字");
					}
				}
				/******************************************************************/
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/member/fbCheckOut.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
			
				/***************************2.開始修改資料***************************************/
			
				FbOrderService fbOrderSvc = new FbOrderService();
				FbOrderVO fbOrderVO = fbOrderSvc.getOneOrder(fb_order_no);
				fbOrderVO.setFb_order_trans(fb_order_trans);
				fbOrderVO.setFb_pay_status(fb_pay_status);
				fbOrderVO.setFb_order_remark(fb_order_remark);
				fbOrderVO.setFb_order_trans(fb_order_trans);
				fbOrderVO.setFb_transport("郵寄");
				
				FbOrderDAO dao = new FbOrderDAO();
				dao.update(fbOrderVO);
					
				//傳簡訊給買家
				
//				Send se = new Send();
//				String[] tel = {phone};
//				if("銀行轉帳".equals(mall_order_trans)) {
//					String message = memVO.getMem_name() + "您好，已收到您的訂單，請於24小時內將款項匯於Instabuy指定帳戶，謝謝";			
//					se.sendMessage(tel , message);
//				}else {
//					String message = memVO.getMem_name() + "您好，已收到您的訂單，感謝您的使用!!" ;
//					se.sendMessage(tel , message);
//				}
				
				MemService memSvc = new MemService();
				memSvc.updateReceiveAdd(receiveadd, fb_buyer_no);
				session.setAttribute("memVO", memVO); // 資料庫update成功後,正確的的memVO物件,存入req
	//			System.out.println("修改地址成功");
				session.removeAttribute("shoppingCart");
					
				Thread.sleep(1500);
			    /***************************3.新增完成,準備轉交(Send the Success view)***********/  
				//為了能顯示訂單付款狀態
				
				req.setAttribute("fb_order_no", fb_order_no);
				String url = "/FrontEnd/member/fbOrderInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
		    
	
		    /***************************其他可能的錯誤處理**********************************/
		   }  catch (Exception e) {
		    errorMsgs.put("Exception",e.getMessage());
		    RequestDispatcher failureView = req
		      .getRequestDispatcher("/FrontEnd/index.jsp");
		    failureView.forward(req, res);
		   }
		
	
		}
		
		if("check".equals(action)) {
			String fb_order_no = req.getParameter("fb_order_no");
			System.out.println(fb_order_no);
			req.setAttribute("fb_order_no", fb_order_no);
			String url = "/FrontEnd/member/fbCheckOut.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		if ("confirmCheck".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
		
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				

				String fb_order_no = req.getParameter("fb_order_no");
				String fb_buyer_no = req.getParameter("fb_buyer_no");
				
				/*************************** 2.開始查詢資料 ****************************************/
				FbOrderService fbOrderSvc = new FbOrderService();
				FbOrderVO fbOrderVO = fbOrderSvc.getOneOrder(fb_order_no);
				fbOrderVO.setFb_pay_status("已付款");
				

				FbOrderDAO dao = new FbOrderDAO();
				dao.update(fbOrderVO);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("fbOrderVO", fbOrderVO); // 資料庫取出的mallOrderVO物件,存入req
//				MemDAO memdao = new MemDAO();
//				MallOrderService mallorderSvc = new MallOrderService();
//				String buyer_ch_name = memdao.findByPK(mall_buyer_no).getMem_name();
//				String to_buyer = memdao.findByPK(mall_buyer_no).getMem_email();
//				String seller_ch_name = memdao.findByPK(mallorderSvc.getOrderSeller(mall_order_no)).getMem_name();
//				String to_seller = memdao.findByPK(mallorderSvc.getOrderSeller(mall_order_no)).getMem_email() ;
//				
//				String subject = "InstaBuy的訂單相關重要通知";
//				String content = "您的InstaBuy商城購物訂單:" + mallOrderVO.getMall_order_no() + "已被取消,請登入InstaBuy查看確認，謝謝!!";
//				String website = "<a href='https://www.ca102g1.tk/CA102G1/FrontEnd/mallorder/mallOrder.jsp'>"
//								+ "按我確認</a>";
//				String buyer_messageText = "Hello! " + buyer_ch_name + "<br>" + content + "<br>" + website;
//				String seller_messageText = "Hello! " + seller_ch_name + "<br>" + content + "<br>" + website;
//
//				MailService mailService = new MailService();
//				mailService.sendMail(to_buyer, subject, buyer_messageText);
//				mailService.sendMail(to_seller, subject, seller_messageText);
				
		
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/mmember/fbOrderInfo.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("confirmReceive".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
		
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String fb_order_no = req.getParameter("fb_order_no");
				String fb_buyer_no = req.getParameter("fb_buyer_no");
			
				/*************************** 2.開始查詢資料 ****************************************/
				FbOrderService fbOrderSvc = new FbOrderService();
				FbOrderVO fbOrderVO = fbOrderSvc.getOneOrder(fb_order_no);
				fbOrderVO.setFb_order_status("已完成");
				

				FbOrderDAO dao = new FbOrderDAO();
				dao.update(fbOrderVO);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("fbOrderVO", fbOrderVO); // 資料庫取出的mallOrderVO物件,存入req
//				MemDAO memdao = new MemDAO();
//				MallOrderService mallorderSvc = new MallOrderService();
//				String buyer_ch_name = memdao.findByPK(mall_buyer_no).getMem_name();
//				String to_buyer = memdao.findByPK(mall_buyer_no).getMem_email();
//				String seller_ch_name = memdao.findByPK(mallorderSvc.getOrderSeller(mall_order_no)).getMem_name();
//				String to_seller = memdao.findByPK(mallorderSvc.getOrderSeller(mall_order_no)).getMem_email() ;
//				
//				String subject = "InstaBuy的訂單相關重要通知";
//				String content = "您的InstaBuy商城購物訂單:" + mallOrderVO.getMall_order_no() + "已被取消,請登入InstaBuy查看確認，謝謝!!";
//				String website = "<a href='https://www.ca102g1.tk/CA102G1/FrontEnd/mallorder/mallOrder.jsp'>"
//								+ "按我確認</a>";
//				String buyer_messageText = "Hello! " + buyer_ch_name + "<br>" + content + "<br>" + website;
//				String seller_messageText = "Hello! " + seller_ch_name + "<br>" + content + "<br>" + website;
//
//				MailService mailService = new MailService();
//				mailService.sendMail(to_buyer, subject, buyer_messageText);
//				mailService.sendMail(to_seller, subject, seller_messageText);
				
		
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/mmember/fbOrderInfo.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		if("showOneFbOrder".equals(action)) {
			String fb_order_no = req.getParameter("fb_order_no");
			req.setAttribute("fb_order_no", fb_order_no);
			String url = "/FrontEnd/member/fbOrderInfo.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		if("showSellerFbOrder".equals(action)) {
			String fb_order_no = req.getParameter("fb_order_no");
			req.setAttribute("fb_order_no", fb_order_no);
			String url = "/FrontEnd/fborder/sellerFbOrderInfo.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

	}

}
