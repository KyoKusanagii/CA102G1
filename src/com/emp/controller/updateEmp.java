package com.emp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.emp.model.EmpDAO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;

@WebServlet("/updateEmp")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class updateEmp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		EmpService service = new EmpService();
		
		req.setCharacterEncoding("UTF-8"); // �B�z�����ɦW
		res.setContentType("text/html; charset=UTF-8");
		
		String emp_no = req.getParameter("emp_no");	//�n��諸���u�s��
		String emp_status = req.getParameter("emp_status");
		String emp_id = req.getParameter("emp_id");
		String emp_name = req.getParameter("emp_name");
		String emp_mem_auth = req.getParameter("emp_mem_auth");
		String emp_carousel_auth = req.getParameter("emp_carousel_auth");
		String emp_report_auth = req.getParameter("emp_report_auth");
		String emp_chat_auth = req.getParameter("emp_chat_auth");
		String emp_level = req.getParameter("emp_level");
		String emp_icon = req.getParameter("emp_icon");
		System.out.println("�n��s���j�Y��:"+emp_icon);
		
		//���Ǧ^�ȯ�A����L�[�u�A���s�L�᪺���u�����bsession	
		EmpVO empvo = service.updateEmp(emp_no,emp_id, emp_name, emp_status, emp_mem_auth, emp_carousel_auth, emp_report_auth, emp_chat_auth, emp_level, emp_icon);		
		HttpSession session = req.getSession(); //��session�N�u�ΡA�S���N�إߤ@�ӷs��
		EmpVO emp_present = (EmpVO)session.getAttribute("emp"); //���o��e�����u����
		if(emp_present.getEmp_no().equals(emp_no)) {
			session.setAttribute("emp", empvo); //�p�G�O��s�ۤv����ơA�N���]emp��session�A�p�G�O�]�w�O�H����ơAsession�N����s
			session.setAttribute("emp_icon",emp_icon);	//��s�ۤv���j�Y�ӡA����base64���r��
		}
		
		System.out.println("�n��s�����u�b��:"+emp_id);
		System.out.println("�n��s�����u�m�W:"+emp_name);
		System.out.println("�n��s�����u�޲z�|���v��:"+emp_mem_auth);
		System.out.println("�n��s�����u�޲z�ۿO���v��:"+emp_carousel_auth);
		System.out.println("�n��s�����u�޲z���|�v��:"+emp_report_auth);
		System.out.println("�n��s�����u����v��:"+emp_chat_auth);
		System.out.println("�n��s�����u¾��:"+emp_level);
							
	}

}
