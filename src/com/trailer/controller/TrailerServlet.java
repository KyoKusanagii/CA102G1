package com.trailer.controller;

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
import com.trailer.model.TrailerService;
import com.trailer.model.TrailerVO;
import com.trailerNotify.model.TrailerNotifyDAO;

@WebServlet("/FrontEnd/Trailer.do")
public class TrailerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TrailerServlet() {
		super();

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		
		
		if("getOneUpdate".equals(action)){
			RequestDispatcher successView = req.getRequestDispatcher("/FrontEnd/live/trailer.jsp");
			successView.forward(req, res);
			
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

				String trailer_topic = req.getParameter("trailer_topic");
				if (trailer_topic == null || trailer_topic.trim().length() == 0) {
					errorMsgs.add("直播預告內容: 請勿空白");
				}

				String trailer_seller_no = req.getParameter("trailer_seller_no");

				String trailer_start_date = req.getParameter("trailer_start_date");
				String trailer_start_time = req.getParameter("trailer_start_time");
				String hr_s = trailer_start_time.substring(0, 2);
				String min_s = trailer_start_time.substring(5, 7);
				String sec_s = trailer_start_time.substring(10, 12);
				trailer_start_time = hr_s + ":" + min_s + ":" + sec_s;

				Timestamp trailer_time = java.sql.Timestamp.valueOf(trailer_start_date + " " + trailer_start_time);

				TrailerVO trailerVO = new TrailerVO();
				trailerVO.setTrailer_seller_no(trailer_seller_no);
				trailerVO.setTrailer_time(trailer_time);
				trailerVO.setTrailer_topic(trailer_topic);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("trailerVO", trailerVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/live/trailer_show.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				TrailerService trailerSvc = new TrailerService();
				trailerSvc.insert(trailer_seller_no, trailer_time, trailer_topic);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = requestURL + "?whichPage=" + whichPage;
				req.setAttribute("trailerVO", trailerVO);
				RequestDispatcher successView = req.getRequestDispatcher("/FrontEnd/live/trailer_show.jsp");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/live/trailer_show.jsp");
				failureView.forward(req, res);
			}
		}
		

		if ("update".equals(action) && req.getParameter("trailer_status") == null) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				TrailerService trailerSvc = new TrailerService();
				TrailerNotifyDAO trailerNotifyDAO = new TrailerNotifyDAO();
				String trailer_no = req.getParameter("trailer_no");
				trailerNotifyDAO.delete(trailer_no);
				trailerSvc.delete(trailer_no);
	System.out.println("delete ok");
				RequestDispatcher successView = req.getRequestDispatcher("/FrontEnd/live/trailer_show.jsp");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/live/trailer_show.jsp");
				failureView.forward(req, res);
			}

		}

		else if ("update".equals(action)) {
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

				String trailer_no = req.getParameter("trailer_no");
				String trailer_seller_no = req.getParameter("trailer_seller_no");
				String trailer_topic = req.getParameter("trailer_topic");
				if (trailer_topic == null || trailer_topic.trim().length() == 0) {
					errorMsgs.add("直播預告內容: 請勿空白");
				}

				String trailer_start_date = req.getParameter("trailer_start_date");
				String trailer_start_time = req.getParameter("trailer_start_time");
				String hr_s = trailer_start_time.substring(0, 2);
				String min_s = trailer_start_time.substring(5, 7);
				String sec_s = trailer_start_time.substring(10, 12);
				trailer_start_time = hr_s + ":" + min_s + ":" + sec_s;

				Timestamp trailer_time = java.sql.Timestamp.valueOf(trailer_start_date + " " + trailer_start_time);

				TrailerVO trailerVO = new TrailerVO();
				trailerVO.setTrailer_no(trailer_no);
				trailerVO.setTrailer_seller_no(trailer_seller_no);
				trailerVO.setTrailer_time(trailer_time);
				trailerVO.setTrailer_topic(trailer_topic);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("trailerVO", trailerVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/live/trailer_show.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				TrailerService trailerSvc = new TrailerService();
				trailerSvc.update(trailer_no, trailer_seller_no, trailer_time, trailer_topic);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = requestURL + "?whichPage=" + whichPage;
				req.setAttribute("trailerVO", trailerVO);
				RequestDispatcher successView = req.getRequestDispatcher("/FrontEnd/live/trailer_show.jsp");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/live/trailer_show.jsp");
				failureView.forward(req, res);
			}
		}

	}
}