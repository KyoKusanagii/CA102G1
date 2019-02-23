package com.item_report.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.item_report.model.ItemReportDAO;
import com.item_report.model.ItemReportService;
import com.item_report.model.ItemReportVO;


@WebServlet("/addItemReport")
public class addItemReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		
		ItemReportService service = new ItemReportService();
		
		String itemReportDescription = req.getParameter("itemReportDescription");
		String itemNo = req.getParameter("itemNo");
		String itemReporter = req.getParameter("itemReporter");
		String itemReasons = req.getParameter("itemReasons");
		String itemPic = req.getParameter("itemPic"); //得到base64圖片字串
		String itemReportHandler = null; //處理檢舉的是誰
		Integer reportStatus = new Integer(0);
		
		service.addItemReport(itemNo, itemReporter, itemReportHandler, itemReportDescription, 
		itemReasons, reportStatus,Base64.getDecoder().decode(itemPic));
		System.out.println("商品編號為:"+itemNo);
		System.out.println("檢舉描述為:"+itemReportDescription);
		System.out.println("檢舉原因為:"+itemReasons);

	}

}
