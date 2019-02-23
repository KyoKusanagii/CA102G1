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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.emp.model.EmpDAO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;

@WebServlet("/showAllEmp")
public class showAllEmp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		EmpService service = new EmpService();
		List<EmpVO> empList = service.getAllEmp();
		JSONArray jarray = new JSONArray();
		JSONObject json;
		Base64.Encoder encoder;
		
		for (EmpVO empvo : empList) {
			json = new JSONObject();
			
			encoder = Base64.getEncoder();

		try {
				json.put("emp_no", empvo.getEmp_no());
				json.put("emp_name", empvo.getEmp_name());
				json.put("emp_id", empvo.getEmp_id());
				if(empvo.getEmp_icon() != null) {
					//如果員工圖片為非空值
					json.put("emp_icon", encoder.encodeToString(empvo.getEmp_icon()));
				}	
				json.put("emp_level",empvo.getEmp_level());
				json.put("emp_status",empvo.getEmp_status());
				jarray.put(json);							
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		out.println(jarray);	//回傳結果json
	}

}
