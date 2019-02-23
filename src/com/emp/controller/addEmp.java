package com.emp.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONException;
import org.json.JSONObject;

import com.emp.model.EmpDAO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;


@WebServlet("/addEmp")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class addEmp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}

		baos.close();
		fis.close();

		return baos.toByteArray();
	}

	 //���X�W�Ǫ��ɮצW�� (�]��API������method,�ҥH�����ۦ漶�g)
	public String getFileNameFromPart(Part part) {
		
		String header = part.getHeader("content-disposition");
		//System.out.println("header=" + header); // ���ե�
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		//System.out.println("filename=" + filename); // ���ե�
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		EmpService service = new EmpService();
		
		req.setCharacterEncoding("UTF-8"); // �B�z�����ɦW
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		int emp_status = 1; //�s�W���u���ɭԹw�]�O�D���v���A
		String emp_id = req.getParameter("emp_id");
		String emp_pwd = req.getParameter("emp_pwd");
		String emp_name = req.getParameter("emp_name");
		String emp_mem_auth = req.getParameter("emp_mem_auth");
		String emp_carousel_auth = req.getParameter("emp_carousel_auth");
		String emp_report_auth = req.getParameter("emp_report_auth");
		String emp_chat_auth = req.getParameter("emp_chat_auth");
		String emp_level = req.getParameter("emp_level");
		String emp_icon = req.getParameter("emp_icon");
		
	
		service.addEmp(emp_id, emp_pwd, emp_name,emp_status,emp_mem_auth, emp_carousel_auth, 
				emp_report_auth, emp_chat_auth, emp_level, emp_icon);				
		
		JSONObject json = new JSONObject();	
		
		try {
			json.put("emp_id",emp_id);
			json.put("emp_pwd",emp_pwd);
			json.put("emp_name",emp_name);
			json.put("emp_mem_auth",emp_mem_auth);
			json.put("emp_carousel_auth",emp_carousel_auth);
			json.put("emp_report_auth",emp_report_auth);
			json.put("emp_chat_auth",emp_chat_auth);
			json.put("emp_level",emp_level);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
			
		if(emp_level.equals("1"))
			emp_level = "��O�޲z��";
		else if(emp_level.equals("2"))
			emp_level = "�@����u";
		System.out.println("�s���u�b��:"+emp_id);
		System.out.println("�s���u�K�X:"+emp_pwd);
		System.out.println("�s���u�m�W:"+emp_name);
		System.out.println("�s���u�޲z�|���v��:"+emp_mem_auth);
		System.out.println("�s���u�޲z�ۿO���v��:"+emp_carousel_auth);
		System.out.println("�s���u�޲z���|�v��:"+emp_report_auth);
		System.out.println("�s���u����v��:"+emp_chat_auth);
		System.out.println("�s���u¾��:"+emp_level);
		System.out.println("�s���u�j�Y�K:"+emp_icon);
		out.println(json);
	}

}
