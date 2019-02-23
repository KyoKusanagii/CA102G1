package com.emp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.emp.model.EmpDAO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;


@WebServlet("/findEmp")
public class findEmp extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
  
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		EmpService service = new EmpService();
		
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		String emp_no = req.getParameter("emp_no");
		EmpVO empvo = service.findEmp(emp_no); //得到要查詢的員工
		JSONObject json = new JSONObject();
		Base64.Encoder encoder = Base64.getEncoder();
		System.out.println("要查詢/修改的員工編號為:"+emp_no);
		try {
			json.put("emp_no", empvo.getEmp_no());
			json.put("emp_name",empvo.getEmp_name());
			json.put("emp_id",empvo.getEmp_id());		
			json.put("emp_level",empvo.getEmp_level());
			json.put("emp_mem_auth",empvo.getEmp_mem_auth());
			json.put("emp_carousel_auth", empvo.getEmp_carousel_auth());
			json.put("emp_report_auth", empvo.getEmp_report_auth());
			json.put("emp_chat_auth", empvo.getEmp_chat_auth());
			json.put("emp_status", empvo.getEmp_status());
			if(empvo.getEmp_icon() != null) {
				json.put("emp_icon",encoder.encodeToString(empvo.getEmp_icon()));
			}		
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		out.println(json);
		
	}

}
