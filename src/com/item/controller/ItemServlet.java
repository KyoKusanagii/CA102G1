package com.item.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.item.model.*;
import com.member.model.MemService;
import com.member.model.MemVO;

@WebServlet("/FrontEnd/query.do")
public class ItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		String action = req.getParameter("action");

		if ("findByCat".equals(action)) {
			System.out.println("我進來找大分類");

			// try {
			// /***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			String str = req.getParameter("item_primary_class");
			Integer item_primary_class = null;
			item_primary_class = new Integer(str);
			System.out.println(item_primary_class);

			/*************************** 2.開始查詢資料 *****************************************/
			ItemService itemSvc = new ItemService();
			List<ItemVO> list = itemSvc.findByCat(item_primary_class);
			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("list", list);
			String url = "/FrontEnd/item/item_query.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		if ("findByPart".equals(action)) {
			System.out.println("我進來找小分類");

			// try {
			// /***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			String str = req.getParameter("item_secondary_class");
			Integer item_secondary_class = null;
			item_secondary_class = new Integer(str);

			/*************************** 2.開始查詢資料 *****************************************/
			ItemService itemSvc = new ItemService();
			List<ItemVO> list = itemSvc.findByPart(item_secondary_class);
			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("list", list);
			String url = "/FrontEnd/item/item_query.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		if ("findByKeyWord".equals(action)) {
			System.out.println("我來用關鍵字搜尋找商品");
			// try {
			// /***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			String item_name = req.getParameter("item_name");
			//

			/*************************** 2.開始查詢資料 *****************************************/
			ItemService itemSvc = new ItemService();
			List<ItemVO> list = itemSvc.findByKeyWord(item_name);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("list", list);
			String url = "/FrontEnd/item/item_query.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		if ("findOneItem".equals(action)) {
			System.out.println("我來找單件商品");
			String itemNo = req.getParameter("itemNo");
			ItemService itemSvc = new ItemService();
			ItemVO itemInfo = itemSvc.getOneItem(itemNo);
			req.setAttribute("itemInfo", itemInfo);
			HttpSession session = req.getSession();
			System.out.println("這個session是空值嗎?" + (session == null));

			// code inserted by Amber
			if (session.getAttribute("memVO") != null) {
				Integer categoryForThisItem = itemInfo.getItem_primary_class();
				Integer counts = 0;
				switch (categoryForThisItem) {
				case 1:
					if (session.getAttribute("CLOTH_CNTS") != null) {
						counts = (Integer) session.getAttribute("CLOTH_CNTS");
						session.setAttribute("CLOTH_CNTS", ++counts);
						System.out.println("cloth +1");
						break;
					}

				case 2:
					if (session.getAttribute("FOOD_CNTS") != null) {
						counts = (Integer) session.getAttribute("FOOD_CNTS");
						session.setAttribute("FOOD_CNTS", ++counts);
						System.out.println("food +1");
						break;
					}
				case 3:
					if (session.getAttribute("GAME_CNTS") != null) {
						counts = (Integer) session.getAttribute("GAME_CNTS");
						session.setAttribute("GAME_CNTS", ++counts);
						System.out.println("game +1");
						break;
					}

				case 4:
					if (session.getAttribute("OUTDOOR_CNTS") != null) {
						counts = (Integer) session.getAttribute("OUTDOOR_CNTS");
						session.setAttribute("OUTDOOR_CNTS", ++counts);
						System.out.println("outdoor +1");
						break;
					}

				case 5:
					if (session.getAttribute("HOMEELEC_CNTS") != null) {
						counts = (Integer) session.getAttribute("HOMEELEC_CNTS");
						session.setAttribute("HOMEELEC_CNTS", ++counts);
						System.out.println("homeelec +1");
						break;
					}

				case 6:
					if (session.getAttribute("THREEC_CNTS") != null) {
						counts = (Integer) session.getAttribute("THREEC_CNTS");
						session.setAttribute("THREEC_CNTS", ++counts);
						System.out.println("threec +1");
						break;
					}

				}
			}

			List<String> itemPicsList = itemSvc.findItemPics(itemNo);
			if (itemPicsList != null)
				req.setAttribute("itemPicsList", itemPicsList);
			// request.setMaxInactiveInterval(3*60*60); //設定session存活3小時
			// res.sendRedirect(req.getContextPath()+"/FrontEnd/item/ItemPage.jsp");
			RequestDispatcher successView = req.getRequestDispatcher("/FrontEnd/item/ItemPage.jsp");
			successView.forward(req, res);
		}

		if ("searchMall".equals(action)) {
			System.out.println("我來用關鍵字搜尋找賣場");

			// try {
			// /***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			String mem_martname = req.getParameter("item_name");
			//

			/*************************** 2.開始查詢資料 *****************************************/
			MemService memSvc = new MemService();
			List<MemVO> list = memSvc.searchMall(mem_martname);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("list", list);
			String url = "/FrontEnd/seller/mall_query.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		// 進來找單一賣場
		if ("findOneMall".equals(action)) {
			System.out.println("我來找單一賣場");
			String memNo = req.getParameter("memNo");
			MemService service = new MemService();
			req.setAttribute("otherSeller", service.findByPK(memNo));
			// req.setAttribute("ToOtherSellerPage",
			// "/CA102G1/FrontEnd/seller/OtherSellersPage.jsp");
			RequestDispatcher successView = req.getRequestDispatcher("/FrontEnd/seller/OtherSellersPage.jsp");
			successView.forward(req, res);
		}

		// 20180819 FB分享
		if ("share".equals(action.substring(0, 5))) {
			String itemNo = action.substring(5, 11);
			System.out.println(itemNo);
			ItemService itemSvc = new ItemService();
			ItemVO itemInfo = itemSvc.getOneItem(itemNo);
			req.setAttribute("itemInfo", itemInfo);
						
			List<String> itemPicsList = itemSvc.findItemPics(itemNo);
			if (itemPicsList != null)
				req.setAttribute("itemPicsList", itemPicsList);
			RequestDispatcher successView = req.getRequestDispatcher("/FrontEnd/item/ItemPage.jsp");
			successView.forward(req, res);
		}
	}
}
