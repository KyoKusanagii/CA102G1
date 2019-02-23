package com.member.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.member.model.*;
@WebServlet("/BackEnd/member.do")

public class MemServletBackEnd extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
		
		
		req.setCharacterEncoding("UTF-8");
	    res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		//登出開始
		HttpSession session = req.getSession();
		    
		if("logout".equals(action)) {
			session.invalidate();
			res.sendRedirect(req.getContextPath()+"/FrontEnd/index.jsp");
		}
		//登出結束
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		//要修改的會員編號
		if ("findByMem_no".equals(action)) {

			/***************************1.接收請求參數****************************************/
			String mem_no = req.getParameter("mem_no");
			System.out.println("要修改的會員編號: " + mem_no);
			
			/***************************2.開始查詢資料****************************************/
			MemService memSvc = new MemService();
			MemVO memVO = memSvc.findByPK(mem_no);
							
			/***************************3.查詢完成,準備轉交(Send the Success view)*************/
			req.setAttribute("memVO", memVO);
			String url = "/BackEnd/member/MemberListOne.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		
		//要修改的會員編號
		if ("findByPK".equals(action)) {

			/***************************1.接收請求參數****************************************/
			String mem_no = req.getParameter("mem_no");
			System.out.println("要修改的會員編號: " + mem_no);
			
			/***************************2.開始查詢資料****************************************/
			MemService memSvc = new MemService();
			MemVO memVO = memSvc.findByPK(mem_no);
							
			/***************************3.查詢完成,準備轉交(Send the Success view)*************/
			req.setAttribute("memVO", memVO);
			String url = "/BackEnd/member/MemberUpdateAuth.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}
		
		
		//修改會員權限
		if ("setAuth".equals(action)) { 
			

			/***************************1.接收請求參數*****************************************/

			Integer mem_condition = new Integer(req.getParameter("mem_condition"));
			Integer mem_artauth = new Integer(req.getParameter("mem_artauth"));

			String mem_no = req.getParameter("mem_no");
			System.out.println(mem_no);

			MemVO memVO = new MemVO();
			memVO.setMem_condition(mem_condition);
			memVO.setMem_artauth(mem_artauth);
			memVO.setMem_no(mem_no);
			
			/***************************2.開始修改資料*****************************************/
			MemService memSvc = new MemService();
			memVO = memSvc.setAuth(mem_no, mem_condition, mem_artauth);
			
			/***************************3.修改完成,準備轉交*************************************/
			String url = "/BackEnd/member/Member.jsp";
			res.sendRedirect(req.getContextPath()+url);
			System.out.println("修改成功");
		}
	}
}
