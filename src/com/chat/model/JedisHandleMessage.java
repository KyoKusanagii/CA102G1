package com.chat.model;

import java.util.List;

import com.util.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandleMessage {
	// 此範例key的設計為(發送者名稱:接收者名稱)，實際應採用(發送者會員編號:接收者會員編號)

	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	public static List<String> getHistoryMsg(String sender, String receiver) {
		String key = new StringBuilder(sender).append(":").append(receiver).toString();
		Jedis jedis = null;
		jedis = pool.getResource();
//		jedis.auth("123456");
		List<String> historyData = jedis.lrange(key, 0, jedis.llen(key) - 1);
		jedis.close();
		return historyData;
	}

	public static void saveChatMessage(String sender, String receiver, String message) {
		// 對雙方來說，都要各存著歷史聊天記錄
		String senderKey = new StringBuilder(sender).append(":").append(receiver).toString();
		String receiverKey = new StringBuilder(receiver).append(":").append(sender).toString();
		Jedis jedis = pool.getResource();
//		jedis.auth("123456");
		jedis.rpush(senderKey, message);
		jedis.rpush(receiverKey, message);

		jedis.close();
	}
	
	public static void saveNotifyMessage(String receiver, String message) {
		// 對雙方來說，都要各存著歷史聊天記錄
		Jedis jedis = pool.getResource();
//		jedis.auth("123456");
		jedis.rpush(receiver, message);
		
		jedis.close();
	}
	
	public static List<String> getHistoryNotify(String receiver) {
		Jedis jedis = null;
		jedis = pool.getResource();
		List<String> historyData = jedis.lrange(receiver, 0, jedis.llen(receiver) - 1);
		jedis.close();
		return historyData;
	}

}
