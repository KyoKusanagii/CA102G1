package com.chat.model;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.member.model.MemVO;



@ServerEndpoint("/TalkWS/{Mem_no}")
public class TalkWS{
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	private static Map<String, Session> servicesMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();
	
	@OnOpen
	public void onOpen(@PathParam("Mem_no")String userName, Session userSession) throws IOException {
		// 設定成500KB為了配合Android bundle傳送大小
		int maxBuferSize = 4 * 1000 * 1024;
		userSession.setMaxTextMessageBufferSize(maxBuferSize);
		userSession.setMaxBinaryMessageBufferSize(maxBuferSize);		
		
		String check = userName.substring(0,1); 
		if(check.equals("E")) {
			servicesMap.put(userName, userSession);
		}else {
			sessionsMap.put(userName, userSession);
		}
		
		
		
		/* save the new user in the map */
		
		
		
		
		
		/* Sends all the connected users to the new user */
		
		
//		Set<String> userNames = sessionsMap.keySet();
//		
//		MemJDBCDAO dao = new MemJDBCDAO();
//		MemVO memVO = dao.findByID(userName);
//		Set<MemVO> userMemVOs = new HashSet();
//		
//		for(String userID: userNames) {
//			userMemVOs.add(dao.findByID(userID));
//		}
//		
//		TalkState stateMessage = new TalkState("open", memVO, userMemVOs);
//		String stateMessageJson = gson.toJson(stateMessage);
//		Collection<Session> sessions = sessionsMap.values();
//		//推播給全部使用者
//		for (Session session : sessions) {
//			session.getAsyncRemote().sendText(stateMessageJson);
//		}
		
		
		String text = String.format(
				"Session ID = %s, connected; userName = %s%nusers: %s%nmaxTextMessageBufferSize = %s",
				userSession.getId(), userName, sessionsMap.keySet(), userSession.getMaxTextMessageBufferSize());
		System.out.println(text);
		
	}
	
	
	// 此方法接收String型式資料
	@OnMessage
	public void onMessage(Session userSession, String message) {
		System.out.println("message received: " + message);
		TalkMessage talkMessage = gson.fromJson(message, TalkMessage.class);
		String messageType = talkMessage.getMessageType();
		String sender = talkMessage.getSender();
		String receiver = talkMessage.getReceiver();
		
		
		System.out.println("messageType = " + messageType);
		System.out.println("receiver:"+ receiver );
		System.out.println("sender: "  + talkMessage.getSender());
		
		Session receiverSession = sessionsMap.get(receiver);
		
		//處理聯絡客服
		String checkReceiver = receiver.substring(0, 1);
		String checkSender = sender.substring(0, 1);
		if(checkReceiver.equals("E") || checkSender.equals("E")) {		
			
			//提取紀錄，判斷左右變成E00000而不是現在使用者編號
			if ("history".equals(messageType)) {
				
				//如果使用視窗者是員工(客服)
				if(checkSender.equals("E")){
					System.out.println("員工有拿到歷史訊息");
					List<String> historyData = JedisHandleMessage.getHistoryMsg("E00000", receiver);
					String historyMsg = gson.toJson(historyData);
					TalkMessage cmHistory = new TalkMessage(sender, receiver, historyMsg,"history");
					if (userSession != null && userSession.isOpen()) {
						userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
						return;
					}
				//如果使用視窗者是會員
				}else {
					System.out.println("會員有拿到歷史訊息");
					List<String> historyData = JedisHandleMessage.getHistoryMsg(sender,"E00000");
					String historyMsg = gson.toJson(historyData);
					TalkMessage cmHistory = new TalkMessage(sender, receiver, historyMsg,"history");
					if (userSession != null && userSession.isOpen()) {
						userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
						return;
					}
				}				
			}
			
			//如果發訊者是員工，則會員收到都是同個員工編號E00000，如果收訊者是員工則不變，但Redis訊息都是存E00000
			//發訊者是員工
			Collection<Session> sessions = servicesMap.values();			
			if(checkSender.equals("E")){
				
				TalkMessage talkMessageEMP = gson.fromJson(message, TalkMessage.class);
				talkMessageEMP.setSender("E00000");
				String EMPMessage = gson.toJson(talkMessageEMP);
			
				// 如果訊息內容包含圖片，必須將資料改成byte型式傳送，否則Android端會因為資料量過大而無法接收
				if (messageType.equals("image")) {						
					int imageLength = talkMessageEMP.getContent().getBytes().length;
					System.out.println("image length = " + imageLength);
					receiverSession.getAsyncRemote().sendBinary(ByteBuffer.wrap(EMPMessage.getBytes()));
					JedisHandleMessage.saveChatMessage("E00000", receiver, EMPMessage);
					System.out.println("有送出圖片");
				} else if(messageType.equals("video")) {
					int videoLength = talkMessage.getContent().getBytes().length;
					System.out.println("video length = " + videoLength);
					receiverSession.getAsyncRemote().sendBinary(ByteBuffer.wrap(message.getBytes()));
					JedisHandleMessage.saveChatMessage("E00000", receiver, EMPMessage);
					System.out.println("有送出影片");
				} else {
					receiverSession.getAsyncRemote().sendText(EMPMessage);
					JedisHandleMessage.saveChatMessage("E00000", receiver, EMPMessage);
					System.out.println("有送出訊息	");
				}
				return;
				
			//收訊者是員工
			}else {
				for (Session session : sessions) {
					// 如果訊息內容包含圖片，必須將資料改成byte型式傳送，否則Android端會因為資料量過大而無法接收
					if (messageType.equals("image")) {
						int imageLength = talkMessage.getContent().getBytes().length;
						System.out.println("image length = " + imageLength);
					    session.getAsyncRemote().sendBinary(ByteBuffer.wrap(message.getBytes()));
						System.out.println("有送出圖片");
					} else if(messageType.equals("video")) {
						int videoLength = talkMessage.getContent().getBytes().length;
						System.out.println("video length = " + videoLength);
					    session.getAsyncRemote().sendBinary(ByteBuffer.wrap(message.getBytes()));
						System.out.println("有送出影片");
					} else {
						session.getAsyncRemote().sendText(message);
						System.out.println("有送出訊息	");
					}
				}
				JedisHandleMessage.saveChatMessage(sender, "E00000", message);
				return;
			}
		}
		
		if ("history".equals(messageType)) {
			System.out.println("有拿到歷史訊息");
			List<String> historyData = JedisHandleMessage.getHistoryMsg(sender, receiver);
			String historyMsg = gson.toJson(historyData);
			TalkMessage cmHistory = new TalkMessage(sender, receiver, historyMsg,"history");
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
				return;
			}
		}
		
		System.out.println(sessionsMap.keySet());
		System.out.println(sessionsMap.values());
		
		
		if(receiverSession != null && receiverSession.isOpen()) {
			// 如果訊息內容包含圖片，必須將資料改成byte型式傳送，否則Android端會因為資料量過大而無法接收
			if (messageType.equals("image")) {
				int imageLength = talkMessage.getContent().getBytes().length;
				System.out.println("image length = " + imageLength);
				receiverSession.getAsyncRemote().sendBinary(ByteBuffer.wrap(message.getBytes()));
				JedisHandleMessage.saveChatMessage(sender, receiver, message);
				System.out.println("有送出圖片");
			} else if(messageType.equals("video")){
				int videoLength = talkMessage.getContent().getBytes().length;
				System.out.println("video length = " + videoLength);
				receiverSession.getAsyncRemote().sendBinary(ByteBuffer.wrap(message.getBytes()));
				JedisHandleMessage.saveChatMessage(sender, receiver, message);
				System.out.println("有送出影片");
			}else {
				receiverSession.getAsyncRemote().sendText(message);
				JedisHandleMessage.saveChatMessage(sender, receiver, message);
				System.out.println("有送出訊息");
			}
		}		
	}
	
	
	// 此方法接收byte型式資料
	@OnMessage
	public void OnMessage(Session userSession, ByteBuffer bytes) {
		String message = new String(bytes.array());
		System.out.println("ByteBuffer Message received: " + message);
	}
	
	
	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
		e.printStackTrace();
	}
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String userNameClose = null;
		Set<String> userNames = sessionsMap.keySet();
		
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) {
				userNameClose = userName;
				sessionsMap.remove(userName);
				break;
			}
		}
		
		
//		if (userNameClose != null) {
//			
//			MemJDBCDAO dao = new MemJDBCDAO();
//			MemVO memVO = dao.findByID(userNameClose);
//			Set<MemVO> userMemVOs = new HashSet();
//			
//			for(String userID: userNames) {
//				userMemVOs.add(dao.findByID(userID));
//			}
//			
//			
//			
//			TalkState stateMessage = new TalkState("close", memVO, userMemVOs);
//			String stateMessageJson = gson.toJson(stateMessage);
//			Collection<Session> sessions = sessionsMap.values();
//			for (Session session : sessions) {
//				session.getAsyncRemote().sendText(stateMessageJson);
//			}
//		}

		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), sessionsMap.keySet().toString());
		System.out.println(text);
	}
	
}
