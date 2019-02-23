package com.live.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.live.model.LiveService;
import com.live.model.LiveVO;

@WebServlet("/showAllLivesBelongSeller")
public class showAllLivesBelongSeller extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String mem_no = req.getParameter("mem_no");
		LiveService service = new LiveService();
		List<LiveVO> sellerLiveList = service.getAllLivesByASeller(mem_no);
		JSONObject json = null;
		JSONArray jarray = new JSONArray();
		
		for(LiveVO live : sellerLiveList) {
			try {
				json = new JSONObject();
				json.put("live_href", live.getLive_address());
				System.out.println(live.getLive_address());
				json.put("live_start_time",live.getLive_start_time());
				json.put("live_end_time", live.getLive_end_time());
				json.put("live_status",live.getLive_status());
			} catch (JSONException e) {
				e.printStackTrace();
			}
				jarray.put(json);
		}
		out.println(jarray);
	}

}
