package com.chat.model;

public class TalkMessage {
    //type: open, close, chat
    private String sender; //�|���s��
    private String receiver; //�|���s��
    private String content;
    //messageType: text, image
    private String messageType;	//�P�_�T���O�Ϥ��٬O��r

    public TalkMessage(String sender, String receiver, String content, String messageType) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.messageType = messageType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
