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
		//�n�X�}�l
		HttpSession session = req.getSession();
		    
		if("logout".equals(action)) {
			session.invalidate();
			res.sendRedirect(req.getContextPath()+"/FrontEnd/index.jsp");
		}
		//�n�X����
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		//�n�ק諸�|���s��
		if ("findByMem_no".equals(action)) {

			/***************************1.�����ШD�Ѽ�****************************************/
			String mem_no = req.getParameter("mem_no");
			System.out.println("�n�ק諸�|���s��: " + mem_no);
			
			/***************************2.�}�l�d�߸��****************************************/
			MemService memSvc = new MemService();
			MemVO memVO = memSvc.findByPK(mem_no);
							
			/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
			req.setAttribute("memVO", memVO);
			String url = "/BackEnd/member/MemberListOne.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		
		//�n�ק諸�|���s��
		if ("findByPK".equals(action)) {

			/***************************1.�����ШD�Ѽ�****************************************/
			String mem_no = req.getParameter("mem_no");
			System.out.println("�n�ק諸�|���s��: " + mem_no);
			
			/***************************2.�}�l�d�߸��****************************************/
			MemService memSvc = new MemService();
			MemVO memVO = memSvc.findByPK(mem_no);
							
			/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
			req.setAttribute("memVO", memVO);
			String url = "/BackEnd/member/MemberUpdateAuth.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}
		
		
		//�ק�|���v��
		if ("setAuth".equals(action)) { 
			

			/***************************1.�����ШD�Ѽ�*****************************************/

			Integer mem_condition = new Integer(req.getParameter("mem_condition"));
			Integer mem_artauth = new Integer(req.getParameter("mem_artauth"));

			String mem_no = req.getParameter("mem_no");
			System.out.println(mem_no);

			MemVO memVO = new MemVO();
			memVO.setMem_condition(mem_condition);
			memVO.setMem_artauth(mem_artauth);
			memVO.setMem_no(mem_no);
			
			/***************************2.�}�l�ק���*****************************************/
			MemService memSvc = new MemService();
			memVO = memSvc.setAuth(mem_no, mem_condition, mem_artauth);
			
			/***************************3.�ק粒��,�ǳ����*************************************/
			String url = "/BackEnd/member/Member.jsp";
			res.sendRedirect(req.getContextPath()+url);
			System.out.println("�ק令�\");
		}
	}
}
