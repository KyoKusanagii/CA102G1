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
	
	//�����ojedispool������
	//private static JedisPool pool = JedisPoolUtil.getJedisPool();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // �B�z�����ɦW
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		//Jedis jedis = pool.getResource();
		JSONObject json = null;
		JSONArray jarray = new JSONArray();
		System.out.println("�ڦ��i�Ө��o�Ҧ��ȪA�C��@!!");
		
		//���o�Ҧ��Ըߪ̪����
		
		/************************�H�U��Redis�����k***************************/
//		List<String> consulterList_MemNo = jedis.lrange("consulterList_MemNo", 0, jedis.llen("consulterList_MemNo"));
//		
//		//�����o�Ҧ����Ըߪ̽s��
//		for(String consulter_memno : consulterList_MemNo) {
//			//�A�N�Ըߪ̽s����key���X�lkey�Ψ�value
//			Map<String,String> hmap = jedis.hgetAll("consulter:"+consulter_memno);
//			for(String key : hmap.keySet()) {
//				//�[�J�Ըߪ̽s���B�m�W�B�j�Y��
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
		
		
		/****************************�H�U��Oracle���@�k****************************/
		
		//���o�Ҧ��Ըߪ̪����
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


