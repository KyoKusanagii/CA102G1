package com.mallorder.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mallorder.model.MallOrderDAO;
import com.mallorder.model.MallOrderService;
import com.mallorder.model.MallOrderVO;
import com.member.model.MemDAO;

@WebServlet("/FrontEnd/mallorder/mallOrder.do")


public class mallOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("cancel_order".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String mall_order_no = new String(req.getParameter("mall_order_no"));
				String mall_seller = new String(req.getParameter("mall_seller"));
				/*************************** 2.開始查詢資料 ****************************************/
				MallOrderService mallOrderSvc = new MallOrderService();
				MallOrderVO mallOrderVO = mallOrderSvc.getOneOrder(mall_order_no);
				mallOrderVO.setMall_order_status("取消");
				

				MallOrderDAO dao = new MallOrderDAO();
				dao.update(mallOrderVO);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("mallOrderVO", mallOrderVO); // 資料庫取出的mallOrderVO物件,存入req
				MemDAO memdao = new MemDAO();
				String buyer_ch_name = memdao.findByPK(mallOrderVO.getMall_buyer_no()).getMem_name();
				String to_buyer = memdao.findByPK(mallOrderVO.getMall_buyer_no()).getMem_email();
				String seller_ch_name = memdao.findByPK(mall_seller).getMem_name();
				String to_seller = memdao.findByPK(mall_seller).getMem_email();
				
				String subject = "InstaBuy的訂單相關重要通知";
				String content = "您的InstaBuy商城購物訂單:" + mallOrderVO.getMall_order_no() + "已被取消,請登入InstaBuy查看確認，謝謝!!";
				String website = "<a href='https://www.ca102g1.tk/CA102G1/FrontEnd/mallorder/mallOrder.jsp'>"
								+ "按我確認</a>";
				String buyer_messageText = "Hello! " + buyer_ch_name + "<br>" + content + "<br>" + website;
				String seller_messageText = "Hello! " + seller_ch_name + "<br>" + content + "<br>" + website;

				MailService mailService = new MailService();
				mailService.sendMail(to_buyer, subject, buyer_messageText);
				mailService.sendMail(to_seller, subject, seller_messageText);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/mallorder/mallOrder.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		if ("buyer_cancel_order".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
		
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String mall_order_no = req.getParameter("mall_order_no");
				String mall_buyer_no = req.getParameter("mall_buyer_no");
//				System.out.println(mall_order_no);
//				System.out.println(mall_buyer_no);
				/*************************** 2.開始查詢資料 ****************************************/
				MallOrderService mallOrderSvc = new MallOrderService();
				MallOrderVO mallOrderVO = mallOrderSvc.getOneOrder(mall_order_no);
				mallOrderVO.setMall_order_status("取消");
				

				MallOrderDAO dao = new MallOrderDAO();
				dao.update(mallOrderVO);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("mallOrderVO", mallOrderVO); // 資料庫取出的mallOrderVO物件,存入req
				MemDAO memdao = new MemDAO();
				MallOrderService mallorderSvc = new MallOrderService();
				String buyer_ch_name = memdao.findByPK(mall_buyer_no).getMem_name();
				String to_buyer = memdao.findByPK(mall_buyer_no).getMem_email();
				String seller_ch_name = memdao.findByPK(mallorderSvc.getOrderSeller(mall_order_no)).getMem_name();
				String to_seller = memdao.findByPK(mallorderSvc.getOrderSeller(mall_order_no)).getMem_email() ;
				
				String subject = "InstaBuy的訂單相關重要通知";
				String content = "您的InstaBuy商城購物訂單:" + mallOrderVO.getMall_order_no() + "已被取消,請登入InstaBuy查看確認，謝謝!!";
				String website = "<a href='https://www.ca102g1.tk/CA102G1/FrontEnd/mallorder/mallOrder.jsp'>"
								+ "按我確認</a>";
				String buyer_messageText = "Hello! " + buyer_ch_name + "<br>" + content + "<br>" + website;
				String seller_messageText = "Hello! " + seller_ch_name + "<br>" + content + "<br>" + website;

				MailService mailService = new MailService();
				mailService.sendMail(to_buyer, subject, buyer_messageText);
				mailService.sendMail(to_seller, subject, seller_messageText);
				
		
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/mallorder/mallOrder.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("confirmCheck".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
		
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String mall_order_no = req.getParameter("mall_order_no");
				String mall_buyer_no = req.getParameter("mall_buyer_no");
				System.out.println(mall_order_no);
				System.out.println(mall_buyer_no);
				/*************************** 2.開始查詢資料 ****************************************/
				MallOrderService mallOrderSvc = new MallOrderService();
				MallOrderVO mallOrderVO = mallOrderSvc.getOneOrder(mall_order_no);
				mallOrderVO.setMall_pay_status("已付款");
				

				MallOrderDAO dao = new MallOrderDAO();
				dao.update(mallOrderVO);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("mallOrderVO", mallOrderVO); // 資料庫取出的mallOrderVO物件,存入req
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
//				
		
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/mallorder/OrderInfo.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("confirmReceive".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
		
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String mall_order_no = req.getParameter("mall_order_no");
				String mall_buyer_no = req.getParameter("mall_buyer_no");
//				System.out.println(mall_order_no);
//				System.out.println(mall_buyer_no);
				/*************************** 2.開始查詢資料 ****************************************/
				MallOrderService mallOrderSvc = new MallOrderService();
				MallOrderVO mallOrderVO = mallOrderSvc.getOneOrder(mall_order_no);
				mallOrderVO.setMall_order_status("已完成");
				

				MallOrderDAO dao = new MallOrderDAO();
				dao.update(mallOrderVO);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("mallOrderVO", mallOrderVO); // 資料庫取出的mallOrderVO物件,存入req
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
	

		
		
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String mall_order_no = new String(req.getParameter("mall_order_no"));
				System.out.println(mall_order_no);
				/*************************** 2.開始刪除資料 ***************************************/
				MallOrderService mallOrderSvc = new MallOrderService();
				mallOrderSvc.deleteOrder(mall_order_no);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/FrontEnd/mallorder/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/mallorder/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("showOneMallOrder".equals(action)) {
			String mall_order_no = req.getParameter("mall_order_no");
			req.setAttribute("mall_order_no", mall_order_no);
			String url = "/FrontEnd/member/orderInfo.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		if("showSellerMallOrder".equals(action)) {
			String mall_order_no = req.getParameter("mall_order_no");
			req.setAttribute("mall_order_no", mall_order_no);
			String url = "/FrontEnd/mallorder/sellerMallOrderInfo.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

	}

}
