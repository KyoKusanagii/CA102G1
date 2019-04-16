package com.emp.model;
import java.util.Base64;
import java.util.List;

public class EmpService {
	
	private EmpDAO_interface dao;
	
	public EmpService(){
		
		dao = new EmpDAO();
	}
	
	public EmpVO addEmp(String emp_id, String emp_pwd,String emp_name,Integer emp_status,String emp_mem_auth,
			String emp_carousel_auth,String emp_report_auth,String emp_chat_auth,String emp_level,String emp_icon) {
		
		Base64.Decoder decoder = Base64.getDecoder(); //解碼base64後送進資料庫
		EmpVO emp = new EmpVO();	
		
		emp.setEmp_id(emp_id);
		emp.setEmp_pwd(emp_pwd);
		emp.setEmp_name(emp_name);
		
		if(emp_icon != null)
			emp.setEmp_icon(decoder.decode(emp_icon));
		else 
			emp.setEmp_icon(null);
		emp.setEmp_status(emp_status);  
		emp.setEmp_mem_auth(Integer.parseInt(emp_mem_auth));//轉成數字當成布林值送進資料庫
		emp.setEmp_carousel_auth(Integer.parseInt(emp_carousel_auth));
		emp.setEmp_report_auth(Integer.parseInt(emp_report_auth));
		emp.setEmp_chat_auth(Integer.parseInt(emp_chat_auth));
		emp.setEmp_level(Integer.parseInt(emp_level));		
		dao.insert(emp);					
		return emp;
	}
	
	public EmpVO updateEmp(String emp_no,String emp_id,String emp_name,String emp_status,String emp_mem_auth,
			String emp_carousel_auth,String emp_report_auth,String emp_chat_auth,String emp_level,String emp_icon ) {
		
		EmpVO emp = new EmpVO();
		Base64.Decoder decoder= Base64.getDecoder();  //解碼網路上傳來的	 
		emp.setEmp_no(emp_no);
		emp.setEmp_id(emp_id);
		emp.setEmp_name(emp_name);	
		emp.setEmp_mem_auth(Integer.parseInt(emp_mem_auth));//轉成數字當成布林值送進資料庫
		emp.setEmp_status(Integer.parseInt(emp_status)); 
		emp.setEmp_carousel_auth(Integer.parseInt(emp_carousel_auth));
		emp.setEmp_report_auth(Integer.parseInt(emp_report_auth));
		emp.setEmp_chat_auth(Integer.parseInt(emp_chat_auth));
		emp.setEmp_level(Integer.parseInt(emp_level));
		emp.setEmp_icon(decoder.decode(emp_icon)); //解碼後得到byte陣列然後設定給更改過後的員工大頭貼
	    dao.update(emp);	
		return emp;		
	}
	
	public EmpVO findEmp(String emp_id) {
		
		EmpVO emp = dao.findById(emp_id);
		
		return emp;
	}
	public List<EmpVO> getAllEmp(){
		
		List<EmpVO> empList = dao.getAll();
		
		return empList;
	}

	public String empLoginCheck(String login_id,String login_password){
		String loginMsg = "";
		EmpVO empVO = dao.empLoginCheck(login_id,login_password);
		if(empVO.getEmp_id() == null){
			loginMsg = "沒有此帳號";
		}else{
			boolean isLoginSuccess = login_password.equals(empVO.getEmp_pwd());
			if(isLoginSuccess){
				loginMsg = "登入成功";
			}else{
				loginMsg = "密碼錯誤";
			}
		}

		return loginMsg;
	}

}
