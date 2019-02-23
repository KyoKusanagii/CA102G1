package com.limitSale.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.item.model.ItemService;
import com.item.model.ItemVO;
import com.limitSale.model.LimitSaleService;
import com.limitSale.model.LimitSaleVO;

@WebServlet("/FrontEnd/LimitSale.do")
public class LimitSaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LimitSaleServlet() {
		super();

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		if ("getOne_For_Insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或
																// 【/dept/listEmps_ByDeptno.jsp】 或 【
																// /dept/listAllDept.jsp】
			req.setAttribute("requestURL", requestURL); // 送出修改的來源網頁路徑, 存入req

			String whichPage = req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
			req.setAttribute("whichPage", whichPage); // 送出修改的來源網頁的第幾頁, 存入req(只用於:istAllEmp.jsp)

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String itemno = req.getParameter("item_no");

				/*************************** 2.開始查詢資料 *****************************************/
				ItemService itemSvc = new ItemService();
				ItemVO itemVO = itemSvc.getOneItem(itemno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("itemVO", itemVO);
				String url = "/FrontEnd/item/limitSale.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/ItemAll.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或
																// 【/dept/listEmps_ByDeptno.jsp】 或 【
																// /dept/listAllDept.jsp】
			req.setAttribute("requestURL", requestURL); // 送出修改的來源網頁路徑, 存入req

			String whichPage = req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
			req.setAttribute("whichPage", whichPage); // 送出修改的來源網頁的第幾頁, 存入req(只用於:istAllEmp.jsp)

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String itemno = req.getParameter("item_no");

				/*************************** 2.開始查詢資料 *****************************************/
				LimitSaleService itemSvc = new LimitSaleService();
				LimitSaleVO limitSaleVO = itemSvc.findOneSale(itemno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("limitSaleVO", limitSaleVO);
				String url = "/FrontEnd/item/update_sale.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/ItemAll.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自po_arti.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或
																// 【/dept/listEmps_ByDeptno.jsp】 或 【
																// /dept/listAllDept.jsp】
			req.setAttribute("requestURL", requestURL); // 送出修改的來源網頁路徑, 存入req

			String whichPage = req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
			req.setAttribute("whichPage", whichPage);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String sale_remark = req.getParameter("sale_remark");
				if (sale_remark == null || sale_remark.trim().length() == 0) {
					errorMsgs.add("特賣內容: 請勿空白");
				}

				Integer sale_price;
				try {
					sale_price = new Integer(req.getParameter("sale_price").trim());
				} catch (NumberFormatException e) {
					sale_price = 0;
					errorMsgs.add("價格請填數字");
				}

				String item_no = req.getParameter("item_no");
				Integer sale_status;
				try {
					sale_status = new Integer(req.getParameter("sale_status"));
				} catch (NumberFormatException e) {
					sale_status = 0;
				}

				String limitSale_start_date = req.getParameter("limitSale_start_date");
				if (limitSale_start_date == null || limitSale_start_date.trim().length() == 0) {
					errorMsgs.add("開始日期 : 請勿空白");
				}
				String limitSale_start_time = req.getParameter("limitSale_start_time");
				if (limitSale_start_time == null || limitSale_start_time.trim().length() == 0) {
					errorMsgs.add("開始時間: 請勿空白");
				}

				String limitSale_end_date = req.getParameter("limitSale_end_date");
				if (limitSale_start_date == null || limitSale_start_date.trim().length() == 0) {
					errorMsgs.add("結束日期 : 請勿空白");
				}
				String limitSale_end_time = req.getParameter("limitSale_end_time");
				if (limitSale_start_time == null || limitSale_start_time.trim().length() == 0) {
					errorMsgs.add("結束時間: 請勿空白");
				}

				String hr_s = limitSale_start_time.substring(0, 2);
				String min_s = limitSale_start_time.substring(5, 7);
				String sec_s = limitSale_start_time.substring(10, 12);
				limitSale_start_time = hr_s + ":" + min_s + ":" + sec_s;

				Timestamp sale_start = java.sql.Timestamp.valueOf(limitSale_start_date + " " + limitSale_start_time);

				String hr_e = limitSale_end_time.substring(0, 2);
				String min_e = limitSale_end_time.substring(5, 7);
				String sec_e = limitSale_end_time.substring(10, 12);
				limitSale_end_time = hr_e + ":" + min_e + ":" + sec_e;

				Timestamp sale_end = java.sql.Timestamp.valueOf(limitSale_end_date + " " + limitSale_end_time);

				if (sale_end.getTime() < sale_start.getTime())
					errorMsgs.add("結束時間請大於開始時間");

				LimitSaleVO limitSaleVO = new LimitSaleVO();
				limitSaleVO.setItem_no(item_no);
				limitSaleVO.setSale_start(sale_start);
				limitSaleVO.setSale_end(sale_end);
				limitSaleVO.setSale_price(sale_price);
				limitSaleVO.setSale_status(sale_status);
				limitSaleVO.setSale_remark(sale_remark);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("limitSaleVO", limitSaleVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/limitSale.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				LimitSaleService limitSaleSvc = new LimitSaleService();
				limitSaleSvc.insert(item_no, sale_start, sale_end, sale_price, sale_status, sale_remark);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = requestURL + "?whichPage=" + whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/limitSale.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或
																// 【/dept/listEmps_ByDeptno.jsp】 或 【
																// /dept/listAllDept.jsp】
			req.setAttribute("requestURL", requestURL); // 送出修改的來源網頁路徑, 存入req

			String whichPage = req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
			req.setAttribute("whichPage", whichPage);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				int sale_no = new Integer(req.getParameter("sale_no"));
				String sale_remark = req.getParameter("sale_remark");
				if (sale_remark == null || sale_remark.trim().length() == 0) {
					errorMsgs.add("特賣內容: 請勿空白");
				}

				Integer sale_price;
				try {
					sale_price = new Integer(req.getParameter("sale_price").trim());
				} catch (NumberFormatException e) {
					sale_price = 0;
					errorMsgs.add("價格請填數字");
				}

				String item_no = req.getParameter("item_no");
				Integer sale_status;
				try {
					sale_status = new Integer(req.getParameter("sale_status"));
				} catch (NumberFormatException e) {
					sale_status = 0;
				}

				String limitSale_start_date = req.getParameter("limitSale_start_date");
				if (limitSale_start_date == null || limitSale_start_date.trim().length() == 0) {
					errorMsgs.add("開始日期 : 請勿空白");
				}
				String limitSale_start_time = req.getParameter("limitSale_start_time");
				if (limitSale_start_time == null || limitSale_start_time.trim().length() == 0) {
					errorMsgs.add("開始時間: 請勿空白");
				}

				String limitSale_end_date = req.getParameter("limitSale_end_date");
				if (limitSale_start_date == null || limitSale_start_date.trim().length() == 0) {
					errorMsgs.add("結束日期 : 請勿空白");
				}
				String limitSale_end_time = req.getParameter("limitSale_end_time");
				if (limitSale_start_time == null || limitSale_start_time.trim().length() == 0) {
					errorMsgs.add("結束時間: 請勿空白");
				}

				LimitSaleVO limitSaleVO = new LimitSaleVO();
				limitSaleVO.setSale_no(sale_no);
				limitSaleVO.setItem_no(item_no);
				// limitSaleVO.setSale_start(sale_start);
				// limitSaleVO.setSale_end(sale_end);
				limitSaleVO.setSale_price(sale_price);
				limitSaleVO.setSale_status(sale_status);
				limitSaleVO.setSale_remark(sale_remark);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("limitSaleVO", limitSaleVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/update_sale.jsp");
					failureView.forward(req, res);
					return;
				}

				String hr_s = limitSale_start_time.substring(0, 2);
				String min_s = limitSale_start_time.substring(5, 7);
				String sec_s = limitSale_start_time.substring(10, 12);
				limitSale_start_time = hr_s + ":" + min_s + ":" + sec_s;

				Timestamp sale_start = java.sql.Timestamp.valueOf(limitSale_start_date + " " + limitSale_start_time);

				String hr_e = limitSale_end_time.substring(0, 2);
				String min_e = limitSale_end_time.substring(5, 7);
				String sec_e = limitSale_end_time.substring(10, 12);
				limitSale_end_time = hr_e + ":" + min_e + ":" + sec_e;

				Timestamp sale_end = java.sql.Timestamp.valueOf(limitSale_end_date + " " + limitSale_end_time);

				if (sale_end.getTime() < sale_start.getTime()) {
					errorMsgs.add("結束時間請大於開始時間");
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/update_sale.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				LimitSaleService limitSaleSvc = new LimitSaleService();
				limitSaleSvc.update(sale_no, item_no, sale_start, sale_end, sale_price, sale_status, sale_remark);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = requestURL + "?whichPage=" + whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/limitSale.jsp");
				failureView.forward(req, res);
			}
		}

	}
}