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
		
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		
		String emp_no = req.getParameter("emp_no");	//要更改的員工編號
		String emp_status = req.getParameter("emp_status");
		String emp_id = req.getParameter("emp_id");
		String emp_name = req.getParameter("emp_name");
		String emp_mem_auth = req.getParameter("emp_mem_auth");
		String emp_carousel_auth = req.getParameter("emp_carousel_auth");
		String emp_report_auth = req.getParameter("emp_report_auth");
		String emp_chat_auth = req.getParameter("emp_chat_auth");
		String emp_level = req.getParameter("emp_level");
		String emp_icon = req.getParameter("emp_icon");
		System.out.println("要更新的大頭照:"+emp_icon);
		
		//有傳回值能再做其他加工，把更新過後的員工物件放在session	
		EmpVO empvo = service.updateEmp(emp_no,emp_id, emp_name, emp_status, emp_mem_auth, emp_carousel_auth, emp_report_auth, emp_chat_auth, emp_level, emp_icon);		
		HttpSession session = req.getSession(); //有session就沿用，沒有就建立一個新的
		EmpVO emp_present = (EmpVO)session.getAttribute("emp"); //取得當前的員工物件
		if(emp_present.getEmp_no().equals(emp_no)) {
			session.setAttribute("emp", empvo); //如果是更新自己的資料，就重設emp的session，如果是設定別人的資料，session就不更新
			session.setAttribute("emp_icon",emp_icon);	//更新自己的大頭照，此為base64的字串
		}
		
		System.out.println("要更新的員工帳號:"+emp_id);
		System.out.println("要更新的員工姓名:"+emp_name);
		System.out.println("要更新的員工管理會員權限:"+emp_mem_auth);
		System.out.println("要更新的員工管理幻燈片權限:"+emp_carousel_auth);
		System.out.println("要更新的員工管理檢舉權限:"+emp_report_auth);
		System.out.println("要更新的員工聊天權限:"+emp_chat_auth);
		System.out.println("要更新的員工職等:"+emp_level);
							
	}

}
