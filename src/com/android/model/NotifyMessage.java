package com.android.model;

import java.sql.Timestamp;
import java.util.List;

import com.member.model.MemVO;

public class NotifyMessage {
	private List<MemVO> notifyList;
	private String sender;
	private String notifyTitle;
	private String notifyMessage;
	private Timestamp notifyTime;
	private String liveAddress;
	//history,notify
	private String type;
	
	public NotifyMessage() {
		super();
	}
	
	
	public NotifyMessage(List<MemVO> notifyList, String sender, String notifyTitle, String notifyMessage,
			Timestamp notifyTime, String liveAddress, String type) {
		super();
		this.notifyList = notifyList;
		this.sender = sender;
		this.notifyTitle = notifyTitle;
		this.notifyMessage = notifyMessage;
		this.notifyTime = notifyTime;
		this.liveAddress = liveAddress;
		this.type = type;
	}


	public List<MemVO> getNotifyList() {
		return notifyList;
	}


	public void setNotifyList(List<MemVO> notifyList) {
		this.notifyList = notifyList;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public String getNotifyTitle() {
		return notifyTitle;
	}


	public void setNotifyTitle(String notifyTitle) {
		this.notifyTitle = notifyTitle;
	}


	public String getNotifyMessage() {
		return notifyMessage;
	}


	public void setNotifyMessage(String notifyMessage) {
		this.notifyMessage = notifyMessage;
	}


	public Timestamp getNotifyTime() {
		return notifyTime;
	}


	public void setNotifyTime(Timestamp notifyTime) {
		this.notifyTime = notifyTime;
	}


	public String getLiveAddress() {
		return liveAddress;
	}


	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}	
	
	
	
}
