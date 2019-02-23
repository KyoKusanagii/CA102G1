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
			//1-1.�[�J�Ըߪ̪��|���s��
			jedis.hset("consulter:"+mem.getMem_no(),"consulter_no",mem.getMem_no());
			//1-2. �N�Ըߪ̪��|���s���t�~�[��@�Ӱ}�C��
			jedis.rpush("consulterList_MemNo", mem.getMem_no());
			
			
			//2.�[�J�Ըߪ̪��m�W  LastName + FirstName
			jedis.hset("consulter:"+mem.getMem_no(),"consulter_name",mem.getMem_name());
			
			//3.�[�J�Ըߪ̪��j�Y�K�A	�N���o���Y�K�}�C�নbase64�s�iredis
			jedis.hset("consulter:"+mem.getMem_no(),"consulter_icon",encoder.encodeToString(mem.getMem_profilepic()));
					
		}	
		
	}

}
