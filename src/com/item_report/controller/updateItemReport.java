package com.item_report.controller;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.article_report.model.ArticleReportDAO;
import com.article_report.model.ArticleReportVO;
import com.artireply_report.model.ArtiReplyReportDAO;
import com.artireply_report.model.ArtiReplyReportVO;
import com.emp.model.EmpDAO;
import com.emp.model.EmpVO;
import com.item.model.ItemService;
import com.item_report.model.ItemReportDAO;
import com.item_report.model.ItemReportService;

@WebServlet("/ItemReport.jsp")	//故意叫一樣名稱，不要被認出有forward
public class updateItemReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		ItemReportService service = new ItemReportService();
		ItemService itemService = new ItemService();
		String itemReportStatus = req.getParameter("itemReportStatus");
		String itemReportNo = req.getParameter("itemReportNo");
		String itemNo = service.findOneReport(itemReportNo).getItem_NO();	//得到檢舉商品的該商品編號
		String itemReportHandler = req.getParameter("itemReportHandler");
		System.out.println("要檢舉的商品編號為:"+itemReportNo);
		System.out.println("選擇通過/駁回:"+itemReportStatus);
		System.out.println("處理該檢舉的員工編號為:"+itemReportHandler);
		service.updateItemReport(itemReportNo, itemReportHandler, Integer.parseInt(itemReportStatus));
		if(Integer.parseInt(itemReportStatus) == 1) {
			itemService.updateLaunch(itemNo, 0, 0);	//檢舉後東西下架
		}
		RequestDispatcher rd = req.getRequestDispatcher("/BackEnd/ItemReport.jsp");
		rd.forward(req, res);
		
	}

}
