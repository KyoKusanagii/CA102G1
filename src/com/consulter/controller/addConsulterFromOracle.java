package com.consulter.controller;

import java.util.Base64;
import java.util.List;

import com.member.model.MemDAO_interface;
import com.member.model.MemDAO;
import com.member.model.MemVO;
import com.util.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class addConsulterFromOracle {
	private static JedisPool pool = JedisPoolUtil.getJedisPool();
	
	public static void main(String[] args) {
		
		MemDAO_interface dao = new MemDAO();
		
		List<MemVO> memList = dao.getAll();		
		Jedis jedis = pool.getResource();
		Base64.Encoder encoder = Base64.getEncoder();
		
		for(int i=0;i<3;i++) {
			MemVO mem = memList.get(i);
			//1-1.加入諮詢者的會員編號
			jedis.hset("consulter:"+mem.getMem_no(),"consulter_no",mem.getMem_no());
			//1-2. 將諮詢者的會員編號另外加到一個陣列裡
			jedis.rpush("consulterList_MemNo", mem.getMem_no());
			
			
			//2.加入諮詢者的姓名  LastName + FirstName
			jedis.hset("consulter:"+mem.getMem_no(),"consulter_name",mem.getMem_name());
			
			//3.加入諮詢者的大頭貼，	將取得的頭貼陣列轉成base64存進redis
			jedis.hset("consulter:"+mem.getMem_no(),"consulter_icon",encoder.encodeToString(mem.getMem_profilepic()));
					
		}	
		
	}

}
