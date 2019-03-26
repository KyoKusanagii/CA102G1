package com.limitSaleSub.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.limitSaleSub.model.LimitSaleSubService;
import com.limitSaleSub.model.LimitSaleSubVO;


public class limitSaleSubServlet extends HttpServlet {
	
	LimitSaleSubService lmSvc = new LimitSaleSubService();
	
	
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("Big5"); 
		res.setContentType("text/html; charset=Big5");
		String action = req.getParameter("action");
		
		if("deleteThis".equals(action)) {
			String thisItemNo = req.getParameter("item");
			LimitSaleSubVO thisItem = lmSvc.findByItemNo(thisItemNo);

			Set<LimitSaleSubVO> lsList = (Set<LimitSaleSubVO>)getServletContext().getAttribute("lsList");
			lsList.remove(thisItem);
			getServletContext().setAttribute("lsList", lsList);
			String url = "/BackEnd/limitSale/limitSaleOnIndex.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); 
			successView.forward(req, res);	
			
		}
		
		
		if ("placeOnIndex".equals(action)) {
		
			
				Set<LimitSaleSubVO> lsList = new LinkedHashSet<>();
				for(int i = 0; i < req.getParameterValues("saleItem").length; i++) {
					//拿出提交的item no
					
					LimitSaleSubVO lsVO = lmSvc.findByItemNo((req.getParameterValues("saleItem")[i]));
					lsList.add(lsVO);
					System.out.println("拿了" + req.getParameterValues("saleItem")[i]);
				}
				
				while (lsList.size() < 4) {
					LimitSaleSubVO randomOne = lmSvc.getRandomOne();
					if (!(lsList.contains(randomOne))) {
						lsList.add(randomOne);
						System.out.println("另外放" + randomOne.getItem_no());
					}
				}
				
				System.out.println("現在有" + lsList.size() + "個");
				getServletContext().setAttribute("lsList", lsList);
				String url = "/BackEnd/limitSale/limitSaleOnIndex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);		
				
			
		}
		
		
	}
}