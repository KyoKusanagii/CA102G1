package com.android.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.member.model.MemService;
import com.member.model.MemVO;


@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MemberServlet() {
        super();
    }


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("OK");
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine())!=null) {
			jsonIn.append(line);
		}
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String ID = jsonObject.get("ID").getAsString();
		String memPassWord = jsonObject.get("memPassWord").getAsString();
		String outStr = "";
		List<String> errorMsgs  = new ArrayList<>();
		MemVO memVO = null;
		
		MemService dao = new MemService();
		
		JsonObject jsonOut = new JsonObject();
			
//		if(mobile.length() == 0 ) {
//			errorMsgs.add("請輸入電話");				
//			String errorMsgsout = gson.toJson(errorMsgs);
//			jsonOut.addProperty("errorMsgs", errorMsgsout);
//			res.setContentType("text/heml; charset=UTF-8");
//			PrintWriter out = res.getWriter();
//			out.print(jsonOut.toString());
//			return;
//		}
//		
//		if(memPassWord.length() == 0) {
//			errorMsgs.add("請輸入密碼");				
//			String errorMsgsout = gson.toJson(errorMsgs);
//			jsonOut.addProperty("errorMsgs", errorMsgsout);
//			res.setContentType("text/heml; charset=UTF-8");
//			PrintWriter out = res.getWriter();
//			out.print(jsonOut.toString());
//			return;
//		}
			
		

		try {
			memVO = dao.findByID(ID);
			memVO.toString();   //為了跳出錯誤訊息
		}catch(Exception e){
			errorMsgs.add("無此資料");				
			String errorMsgsout = gson.toJson(errorMsgs);
			jsonOut.addProperty("errorMsgs", errorMsgsout);
			res.setContentType("text/heml; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.print(jsonOut.toString());
			return;
		}
		if(!memVO.getMem_psw().equals(memPassWord)) {
			errorMsgs.add("密碼錯誤");
		}
			
		
		String memBirth = gson.toJson(memVO.getMem_birth()); 
		String memProfilePic = gson.toJson(memVO.getMem_profilepic());
		String memMartCover = gson.toJson(memVO.getMem_martcover());
		
		String errorMsgsout = gson.toJson(errorMsgs);
		
		
		jsonOut.addProperty("errorMsgs", errorMsgsout);
		jsonOut.addProperty("mem_no",memVO.getMem_no()==null?"":memVO.getMem_no());
		jsonOut.addProperty("mem_id", memVO.getMem_id()==null?"":memVO.getMem_id());
		jsonOut.addProperty("mem_psw",memVO.getMem_psw()==null?"":memVO.getMem_psw());
		jsonOut.addProperty("mem_email", memVO.getMem_email()==null?"":memVO.getMem_email());
		jsonOut.addProperty("mem_fbid", memVO.getMem_fbid()==null?"":memVO.getMem_fbid());
		jsonOut.addProperty("mem_name", memVO.getMem_name()==null?"":memVO.getMem_name());
		jsonOut.addProperty("mem_sex", memVO.getMem_sex()==null?"":memVO.getMem_sex());
		jsonOut.addProperty("mem_birth", memVO.getMem_birth()==null?"":memBirth);
		jsonOut.addProperty("mem_mobile", memVO.getMem_mobile()==null?"":memVO.getMem_mobile());
		jsonOut.addProperty("mem_post", memVO.getMem_post()==null?"":memVO.getMem_post());
		jsonOut.addProperty("mem_address", memVO.getMem_address()==null?"":memVO.getMem_address());
		jsonOut.addProperty("mem_receiveadd", memVO.getMem_receiveadd()==null?"":memVO.getMem_receiveadd());
		jsonOut.addProperty("mem_condition", memVO.getMem_condition()==null?0:memVO.getMem_condition());
		jsonOut.addProperty("mem_artauth", memVO.getMem_artauth()==null?0:memVO.getMem_artauth());
		jsonOut.addProperty("mem_martinfo", memVO.getMem_martinfo()==null?"":memVO.getMem_martinfo());
		jsonOut.addProperty("mem_recommend", memVO.getMem_recommend()==null?"":memVO.getMem_recommend());
		jsonOut.addProperty("mem_profilepic", memVO.getMem_profilepic()==null?"":memProfilePic);
		jsonOut.addProperty("mem_martcover", memVO.getMem_martcover()==null?"":memMartCover);
		jsonOut.addProperty("profilepicEncoded", memVO.getProfilepicEncoded()==null?"":memVO.getProfilepicEncoded());
		jsonOut.addProperty("martcoverEncoded", memVO.getMartcoverEncoded()==null?"":memVO.getMartcoverEncoded());
		jsonOut.addProperty("mem_martname", memVO.getMem_martname()==null?"":memVO.getMem_martname());
		
		
		
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(jsonOut.toString());
		
		
		
		
	}

}
