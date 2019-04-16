package com.emp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.model.EmpService;
import com.oreilly.servlet.Base64Encoder;
import org.json.JSONException;
import org.json.JSONObject;

import com.emp.model.EmpDAO;
import com.emp.model.EmpVO;

@WebServlet("/EmpLogin")
public class EmpLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Base64.Encoder encoder = Base64.getEncoder();
		EmpService service = new EmpService();
		req.setCharacterEncoding("UTF-8"); // �B�z�����ɦW
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String loginMsg = "";
		List<EmpVO> empList = service.getAllEmp();
		String emp_account = req.getParameter("emp_account");
		String emp_password = req.getParameter("emp_password");
		loginMsg = service.empLoginCheck(emp_account,emp_password);
		RequestDispatcher View = null;
		if (!"�n�J���\".equals(loginMsg)) {
			req.setAttribute("empLoginMsg",loginMsg);
			System.out.println("�n�J���G�O = " + loginMsg);
			View = req.getRequestDispatcher("/BackEnd/Login.jsp");
			View.forward(req, res);
		}else {
			HttpSession session = req.getSession();
			EmpVO emp = service.findEmp(emp_account);
			session.setAttribute("emp", emp);	//�æs��session��
			if(emp.getEmp_icon() != null) { //�p�G���u���]�w�j�Y�K
				session.setAttribute("emp_icon",encoder.encodeToString(emp.getEmp_icon()));	//�]�w�j�Y�K��base64
			}
			res.sendRedirect(req.getContextPath() + "/BackEnd/BackEnd.jsp");
		}
	}

}
