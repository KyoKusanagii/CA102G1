package com.chat.controller;

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

import com.chat.model.ChatService;
import com.member.model.MemVO;

@WebServlet("/showAllFriends")
public class showAllFriends extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		String chat_master_no = req.getParameter("chat_master_no");
		ChatService service = new ChatService();
		List<MemVO> friendsList = service.getAllFriends(chat_master_no);
		JSONObject json = new JSONObject();;
		JSONArray jarray = new JSONArray();
		Base64.Encoder encoder = Base64.getEncoder();
		
		for(MemVO friend : friendsList) {	//從chat表格內撈出Mem的陣列，所以E00000不再Mem裡，要另外寫
			try {
				json = new JSONObject();
				json.put("friend_no", friend.getMem_no());
				json.put("friend_name", friend.getMem_name());
				if(friend.getMem_profilepic() != null)
					json.put("friend_icon", encoder.encodeToString(friend.getMem_profilepic()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jarray.put(json);
		}
		out.println(jarray);
	}

}
