package com.emp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		EmpDAO dao = new EmpDAO();	
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		List<EmpVO> empList = dao.getAll();
		String emp_no = req.getParameter("emp_no");
		String emp_account = req.getParameter("emp_account");
		String emp_password = req.getParameter("emp_password");
		String verification = req.getParameter("verification");	//判斷是要驗證帳號還是密碼還是要登入
		System.out.println("要做什麼行動:"+verification);
		System.out.println("要登入的員工編號為:"+emp_no);
		System.out.println("要登入的員工帳號為:"+emp_account);
		System.out.println("要登入的員工密碼為:"+emp_password);
		JSONObject json = new JSONObject();
		
		if(verification.equals("Account")) {  //想要驗證帳號
			for(EmpVO emp : empList) {
				if(emp.getEmp_no().equals(emp_no)) {
					if(emp.getEmp_status() == 0) {
						try {
							json.put("isAccountActive", "false");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						return;
					}							
					if(!emp.getEmp_id().equals(emp_account)) {  //確認其編號是否對應帳號
						try {
							json.put("isAccountCorrect","false");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						out.println(json);
						return;
					}else if(emp.getEmp_id().equals(emp_account)) {
						try {
							json.put("isAccountCorrect","true");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						out.println(json);
						return;
					}				
				}
			}
			
		}else if(verification.equals("Password")) {  //想要驗證帳號
			for(EmpVO emp : empList) {
				if(emp.getEmp_no().equals(emp_no)) {
					if(emp.getEmp_status() == 0) {
						try {
							json.put("isAccountActive", "false");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						return;
					}			
					if(!emp.getEmp_pwd().equals(emp_password)) {  //確認其編號是否對應密碼
						try {
							json.put("isPassWordCorrect","false");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						out.println(json);
						return;
					}else if(emp.getEmp_pwd().equals(emp_password)) {
						try {
							json.put("isPassWordCorrect","true");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						out.println(json);
					 }				
					
				}
			}
			
		}else if(verification.equals("Login")) {   //帳號密碼都正確想要登入
			System.out.println("帳號密碼都正確");
			HttpSession session = req.getSession();
			Base64.Encoder encoder = Base64.getEncoder();
			EmpVO emp = dao.findByPK(emp_no);	//找到成功登入的員工物件
			System.out.println("登入的員工姓名為:"+emp.getEmp_name());
			session.setAttribute("emp", emp);	//並存到session裡
			if(emp.getEmp_icon() != null) { //如果員工有設定大頭貼
				session.setAttribute("emp_icon",encoder.encodeToString(emp.getEmp_icon()));	//設定大頭貼的base64
			}
				
			res.sendRedirect(req.getContextPath()+"/BackEnd/BackEnd.jsp");
		}
		
	}

}
