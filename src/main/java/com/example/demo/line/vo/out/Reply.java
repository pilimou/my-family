package com.example.demo.line.vo.out;

import java.util.List;

import com.example.demo.line.vo.out.quickreply.QReply;

public class Reply {
	
	private String replyToken;
	private List<?> messages;
	
	public String getReplyToken() {
		return replyToken;
	}
	public void setReplyToken(String replyToken) {
		this.replyToken = replyToken;
	}
	public List<?> getMessages() {
		return messages;
	}
	public void setMessages(List<?> messages) {
		this.messages = messages;
	}
	
	
	
}
