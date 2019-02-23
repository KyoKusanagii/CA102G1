package com.consulter.controller;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.consulter.model.ConsulterService;
import com.consulter.model.ConsulterVO;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@WebServlet("/showAllConsulter")
public class showAllConsulter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//先取得jedispool的物件
	//private static JedisPool pool = JedisPoolUtil.getJedisPool();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		//Jedis jedis = pool.getResource();
		JSONObject json = null;
		JSONArray jarray = new JSONArray();
		System.out.println("我有進來取得所有客服列表哦!!");
		
		//取得所有諮詢者的資料
		
		/************************以下為Redis的做法***************************/
//		List<String> consulterList_MemNo = jedis.lrange("consulterList_MemNo", 0, jedis.llen("consulterList_MemNo"));
//		
//		//先取得所有的諮詢者編號
//		for(String consulter_memno : consulterList_MemNo) {
//			//再將諮詢者編號當成key取出子key及其value
//			Map<String,String> hmap = jedis.hgetAll("consulter:"+consulter_memno);
//			for(String key : hmap.keySet()) {
//				//加入諮詢者編號、姓名、大頭照
//				try {
//					json = new JSONObject();			
//					//System.out.println(key);
//					json.put("consulter_no",hmap.get("consulter_no"));
//					json.put("consulter_name",hmap.get("consulter_name"));
//					json.put("consulter_icon",hmap.get("consulter_icon"));					
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}		
//			}
//			jarray.put(json);	
//		}	
//		jedis.close();
//		out.println(jarray);
		
		
		/****************************以下為Oracle的作法****************************/
		
		//取得所有諮詢者的資料
		ConsulterService service = new ConsulterService();
		List<ConsulterVO> consulterList = service.getAllConsulter();
		Base64.Encoder encoder = Base64.getEncoder();
		
		for(ConsulterVO consulter : consulterList) {
			try {
				json = new JSONObject();			
				json.put("consulter_no",consulter.getConsulter_no());
				json.put("consulter_name",consulter.getConsulter_name());
				json.put("consulter_icon",encoder.encodeToString(consulter.getConsulter_icon()));					
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jarray.put(json);
		}
		out.println(jarray);
	}
}


