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
		
		req.setCharacterEncoding("UTF-8"); // �B�z�����ɦW
		res.setContentType("text/html; charset=UTF-8");
		
		ItemReportService service = new ItemReportService();
		
		String itemReportDescription = req.getParameter("itemReportDescription");
		String itemNo = req.getParameter("itemNo");
		String itemReporter = req.getParameter("itemReporter");
		String itemReasons = req.getParameter("itemReasons");
		String itemPic = req.getParameter("itemPic"); //�o��base64�Ϥ��r��
		String itemReportHandler = null; //�B�z���|���O��
		Integer reportStatus = new Integer(0);
		
		service.addItemReport(itemNo, itemReporter, itemReportHandler, itemReportDescription, 
		itemReasons, reportStatus,Base64.getDecoder().decode(itemPic));
		System.out.println("�ӫ~�s����:"+itemNo);
		System.out.println("���|�y�z��:"+itemReportDescription);
		System.out.println("���|��]��:"+itemReasons);

	}

}
