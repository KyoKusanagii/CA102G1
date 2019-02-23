package com.consulter.controller;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.consulter.model.ConsulterService;
import com.consulter.model.ConsulterVO;

@WebServlet("/addConsulter")
public class addConsulter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		
		String cosulterNO = req.getParameter("cosulterNO");
		String consulterName = req.getParameter("consulterName");
		String consulterIcon = req.getParameter("consulterIcon");
		ConsulterService service = new ConsulterService();
		List<ConsulterVO> consulterList = service.getAllConsulter();
		
		//判斷這個會員之前是否有加過客服列表
		if(consulterList.contains(new ConsulterVO(cosulterNO,consulterName, Base64.getDecoder().decode(consulterIcon)))) {
			System.out.println("此會員已經新增至客服列表，不能再加進去了");
			return;
		}else {												//再把大頭貼解成byte陣列存到資料庫
			service.addConsulter(cosulterNO, consulterName, Base64.getDecoder().decode(consulterIcon));
		}
		
	}

}
