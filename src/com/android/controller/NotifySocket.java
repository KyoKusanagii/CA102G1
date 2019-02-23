package com.android.controller;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.android.model.NotifyMessage;
import com.chat.model.JedisHandleMessage;
import com.follow_mem.model.Follow_memVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.member.model.MemVO;

@ServerEndpoint("/NotifySocket/{Mem_no}")
public class NotifySocket {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();
	
	@OnOpen
	public void onOpen(@PathParam("Mem_no")String userName, Session userSession) {
		int maxBuferSize = 500*1024; 
		userSession.setMaxTextMessageBufferSize(maxBuferSize);
		userSession.setMaxBinaryMessageBufferSize(maxBuferSize);
		
		
		
		//登入就儲存session
		sessionsMap.put(userName, userSession);
		
		Set<String> userNames = sessionsMap.keySet();
		
		
		String text = String.format("Session ID = %s, connected; userName = %s%nusers: %s", userSession.getId(),
				userName, userNames);
		System.out.println(text);
	}
	
	@OnMessage
	public void onMessage(Session userSession, String message) {
		System.out.println(message);
		

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Type type = new TypeToken<List<MemVO>>() {}.getType();

		JsonObject messagejson = gson.fromJson(message, JsonObject.class);
		
		NotifyMessage notifyMessage = new NotifyMessage();
		
		
		
		//取得歷史資料
		if(messagejson.get("type").getAsString().equals("history")) {			
			String sender = messagejson.get("sender").getAsString();
			
			List<String> historyData = JedisHandleMessage.getHistoryNotify(sender);
			
			Session session = sessionsMap.get(sender);
			
			notifyMessage.setType("history");
			notifyMessage.setNotifyMessage(gson.toJson(historyData));
			
			
			if(session!=null && session.isOpen()) {
				session.getAsyncRemote().sendText(gson.toJson(notifyMessage));
			}
			return;
		}
		
		
		//預告發送
		if(messagejson.get("type").getAsString().equals("trail")) {		
			

			notifyMessage.setNotifyList(gson.fromJson(messagejson.get("notifyList"),type));

			notifyMessage.setSender(messagejson.get("sender").getAsString());

			notifyMessage.setNotifyTitle(messagejson.get("notifyTitle").getAsString());
	
			notifyMessage.setNotifyMessage(messagejson.get("notifyMessage").getAsString());
			
			
			
			notifyMessage.setType((messagejson.get("type").getAsString()));

			
			
		
			
			String totalTime = messagejson.get("notifyTime").getAsString();
			
			
//			String news_date_day = req.getParameter("news_date_day");
//		    String news_date_time = req.getParameter("news_date_time");
//		    System.out.println(news_date_time);
			
			String trailDate = totalTime.substring(0, totalTime.indexOf("$"));
			String trailTime = totalTime.substring(totalTime.indexOf("$") + 1, totalTime.length());
			
			System.out.println(trailDate);
			System.out.println(trailTime);
			
			
		    String hr = trailTime.substring(0,2);
		    String min = trailTime.substring(5,7);
		    String sec = trailTime.substring(10,12);
		    trailTime = hr + ":" + min + ":" + sec;
//		    System.out.println(news_date_time);
//		    System.out.println(news_date_time.length());
		    Timestamp trail_date = java.sql.Timestamp.valueOf( trailDate + " " + trailTime);
		    System.out.println(trail_date);
			
									
		    
		    notifyMessage.setNotifyTime(new Timestamp(trail_date.getTime()-(1*60*1000)));

			List<MemVO> notifyList = new ArrayList<>();
			
			notifyList = notifyMessage.getNotifyList();

			String jsonOut = gson.toJson(notifyMessage);
		
			
			//取得發送通知名單並發送通知
			for(MemVO receiver: notifyList) {
				System.out.println("收訊者 :" + receiver.getMem_no());
				Session sendSession = sessionsMap.get(receiver.getMem_no());
				
				if(sendSession!=null && sendSession.isOpen()) {
					
					
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							sendSession.getAsyncRemote().sendText(jsonOut);
							JedisHandleMessage.saveNotifyMessage(receiver.getMem_no(), jsonOut);
							System.out.println("有送出訊息");
							timer.cancel();
						}
						
					}, new Date(trail_date.getTime()-(1*60*1000)));					
				}
			}
			
			System.out.println("Message received: " + message);
			
			return;
		}
		
		
		
		//直播通知
		notifyMessage.setNotifyList(gson.fromJson(messagejson.get("notifyList"),type));
		notifyMessage.setSender(messagejson.get("sender").getAsString());
		notifyMessage.setNotifyTitle(messagejson.get("notifyTitle").getAsString());
		notifyMessage.setNotifyMessage(messagejson.get("notifyMessage").getAsString());
		notifyMessage.setNotifyTime(Timestamp.valueOf(messagejson.get("notifyTime").getAsString()));
		notifyMessage.setType((messagejson.get("type").getAsString()));
		notifyMessage.setLiveAddress((messagejson.get("liveAddress").getAsString()));
		
		

		List<MemVO> notifyList = new ArrayList<>();
		
		notifyList = notifyMessage.getNotifyList();

		String jsonOut = gson.toJson(notifyMessage);
	
		
		//取得發送通知名單並發送通知
		for(MemVO receiver: notifyList) {
			System.out.println("收訊者 :" + receiver.getMem_no());
			Session sendSession = sessionsMap.get(receiver.getMem_no());
			
			if(sendSession!=null && sendSession.isOpen()) {
				sendSession.getAsyncRemote().sendText(jsonOut);
				JedisHandleMessage.saveNotifyMessage(receiver.getMem_no(), jsonOut);
				System.out.println("有送出訊息");
			}
		}
		
		System.out.println("Message received: " + message);
		
	}
	
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		Set<String> userNames = sessionsMap.keySet();
		
		for(String userName : userNames) {
			if(sessionsMap.get(userName).equals(userSession)) {
				sessionsMap.remove(userName);
				break;
			}
		}
		
		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userNames);
		System.out.println(text);
		
		
	}
	
	@OnError
	public void onError(Session userSession, Throwable e) {
		
	}

}
