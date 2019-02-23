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
		// �]�w��500KB���F�t�XAndroid bundle�ǰe�j�p
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
//		//�����������ϥΪ�
//		for (Session session : sessions) {
//			session.getAsyncRemote().sendText(stateMessageJson);
//		}
		
		
		String text = String.format(
				"Session ID = %s, connected; userName = %s%nusers: %s%nmaxTextMessageBufferSize = %s",
				userSession.getId(), userName, sessionsMap.keySet(), userSession.getMaxTextMessageBufferSize());
		System.out.println(text);
		
	}
	
	
	// ����k����String�������
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
		
		//�B�z�p���ȪA
		String checkReceiver = receiver.substring(0, 1);
		String checkSender = sender.substring(0, 1);
		if(checkReceiver.equals("E") || checkSender.equals("E")) {		
			
			//���������A�P�_���k�ܦ�E00000�Ӥ��O�{�b�ϥΪ̽s��
			if ("history".equals(messageType)) {
				
				//�p�G�ϥε����̬O���u(�ȪA)
				if(checkSender.equals("E")){
					System.out.println("���u��������v�T��");
					List<String> historyData = JedisHandleMessage.getHistoryMsg("E00000", receiver);
					String historyMsg = gson.toJson(historyData);
					TalkMessage cmHistory = new TalkMessage(sender, receiver, historyMsg,"history");
					if (userSession != null && userSession.isOpen()) {
						userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
						return;
					}
				//�p�G�ϥε����̬O�|��
				}else {
					System.out.println("�|����������v�T��");
					List<String> historyData = JedisHandleMessage.getHistoryMsg(sender,"E00000");
					String historyMsg = gson.toJson(historyData);
					TalkMessage cmHistory = new TalkMessage(sender, receiver, historyMsg,"history");
					if (userSession != null && userSession.isOpen()) {
						userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
						return;
					}
				}				
			}
			
			//�p�G�o�T�̬O���u�A�h�|�����쳣�O�P�ӭ��u�s��E00000�A�p�G���T�̬O���u�h���ܡA��Redis�T�����O�sE00000
			//�o�T�̬O���u
			Collection<Session> sessions = servicesMap.values();			
			if(checkSender.equals("E")){
				
				TalkMessage talkMessageEMP = gson.fromJson(message, TalkMessage.class);
				talkMessageEMP.setSender("E00000");
				String EMPMessage = gson.toJson(talkMessageEMP);
			
				// �p�G�T�����e�]�t�Ϥ��A�����N��Ƨ令byte�����ǰe�A�_�hAndroid�ݷ|�]����ƶq�L�j�ӵL�k����
				if (messageType.equals("image")) {						
					int imageLength = talkMessageEMP.getContent().getBytes().length;
					System.out.println("image length = " + imageLength);
					receiverSession.getAsyncRemote().sendBinary(ByteBuffer.wrap(EMPMessage.getBytes()));
					JedisHandleMessage.saveChatMessage("E00000", receiver, EMPMessage);
					System.out.println("���e�X�Ϥ�");
				} else if(messageType.equals("video")) {
					int videoLength = talkMessage.getContent().getBytes().length;
					System.out.println("video length = " + videoLength);
					receiverSession.getAsyncRemote().sendBinary(ByteBuffer.wrap(message.getBytes()));
					JedisHandleMessage.saveChatMessage("E00000", receiver, EMPMessage);
					System.out.println("���e�X�v��");
				} else {
					receiverSession.getAsyncRemote().sendText(EMPMessage);
					JedisHandleMessage.saveChatMessage("E00000", receiver, EMPMessage);
					System.out.println("���e�X�T��	");
				}
				return;
				
			//���T�̬O���u
			}else {
				for (Session session : sessions) {
					// �p�G�T�����e�]�t�Ϥ��A�����N��Ƨ令byte�����ǰe�A�_�hAndroid�ݷ|�]����ƶq�L�j�ӵL�k����
					if (messageType.equals("image")) {
						int imageLength = talkMessage.getContent().getBytes().length;
						System.out.println("image length = " + imageLength);
					    session.getAsyncRemote().sendBinary(ByteBuffer.wrap(message.getBytes()));
						System.out.println("���e�X�Ϥ�");
					} else if(messageType.equals("video")) {
						int videoLength = talkMessage.getContent().getBytes().length;
						System.out.println("video length = " + videoLength);
					    session.getAsyncRemote().sendBinary(ByteBuffer.wrap(message.getBytes()));
						System.out.println("���e�X�v��");
					} else {
						session.getAsyncRemote().sendText(message);
						System.out.println("���e�X�T��	");
					}
				}
				JedisHandleMessage.saveChatMessage(sender, "E00000", message);
				return;
			}
		}
		
		if ("history".equals(messageType)) {
			System.out.println("��������v�T��");
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
			// �p�G�T�����e�]�t�Ϥ��A�����N��Ƨ令byte�����ǰe�A�_�hAndroid�ݷ|�]����ƶq�L�j�ӵL�k����
			if (messageType.equals("image")) {
				int imageLength = talkMessage.getContent().getBytes().length;
				System.out.println("image length = " + imageLength);
				receiverSession.getAsyncRemote().sendBinary(ByteBuffer.wrap(message.getBytes()));
				JedisHandleMessage.saveChatMessage(sender, receiver, message);
				System.out.println("���e�X�Ϥ�");
			} else if(messageType.equals("video")){
				int videoLength = talkMessage.getContent().getBytes().length;
				System.out.println("video length = " + videoLength);
				receiverSession.getAsyncRemote().sendBinary(ByteBuffer.wrap(message.getBytes()));
				JedisHandleMessage.saveChatMessage(sender, receiver, message);
				System.out.println("���e�X�v��");
			}else {
				receiverSession.getAsyncRemote().sendText(message);
				JedisHandleMessage.saveChatMessage(sender, receiver, message);
				System.out.println("���e�X�T��");
			}
		}		
	}
	
	
	// ����k����byte�������
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
