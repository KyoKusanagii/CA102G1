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
		req.setCharacterEncoding("UTF-8"); // �B�z�����ɦW
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		List<EmpVO> empList = dao.getAll();
		String emp_no = req.getParameter("emp_no");
		String emp_account = req.getParameter("emp_account");
		String emp_password = req.getParameter("emp_password");
		String verification = req.getParameter("verification");	//�P�_�O�n���ұb���٬O�K�X�٬O�n�n�J
		System.out.println("�n��������:"+verification);
		System.out.println("�n�n�J�����u�s����:"+emp_no);
		System.out.println("�n�n�J�����u�b����:"+emp_account);
		System.out.println("�n�n�J�����u�K�X��:"+emp_password);
		JSONObject json = new JSONObject();
		
		if(verification.equals("Account")) {  //�Q�n���ұb��
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
					if(!emp.getEmp_id().equals(emp_account)) {  //�T�{��s���O�_�����b��
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
			
		}else if(verification.equals("Password")) {  //�Q�n���ұb��
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
					if(!emp.getEmp_pwd().equals(emp_password)) {  //�T�{��s���O�_�����K�X
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
			
		}else if(verification.equals("Login")) {   //�b���K�X�����T�Q�n�n�J
			System.out.println("�b���K�X�����T");
			HttpSession session = req.getSession();
			Base64.Encoder encoder = Base64.getEncoder();
			EmpVO emp = dao.findByPK(emp_no);	//��즨�\�n�J�����u����
			System.out.println("�n�J�����u�m�W��:"+emp.getEmp_name());
			session.setAttribute("emp", emp);	//�æs��session��
			if(emp.getEmp_icon() != null) { //�p�G���u���]�w�j�Y�K
				session.setAttribute("emp_icon",encoder.encodeToString(emp.getEmp_icon()));	//�]�w�j�Y�K��base64
			}
				
			res.sendRedirect(req.getContextPath()+"/BackEnd/BackEnd.jsp");
		}
		
	}

}
