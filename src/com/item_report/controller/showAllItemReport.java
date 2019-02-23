package com.item_report.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.article_report.model.ArticleReportDAO;
import com.article_report.model.ArticleReportVO;
import com.item_report.model.ItemReportDAO;
import com.item_report.model.ItemReportService;
import com.item_report.model.ItemReportVO;

@WebServlet("/showAllItemReport")
public class showAllItemReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		ItemReportService service = new ItemReportService();
		List<ItemReportVO> itemReportList =  service.getAllItemReport();
		List<String> reportItemNameList =  service.getAllItemNameofReport();
		req.setAttribute("itemReportList",itemReportList);
		req.setAttribute("reportItemNameList",reportItemNameList);
		RequestDispatcher rd = req.getRequestDispatcher("/BackEnd/ItemReport.jsp");
		rd.forward(req, res);
	}

}
