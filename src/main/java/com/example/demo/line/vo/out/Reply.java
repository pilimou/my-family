package com.example.demo.line.vo.out;

import java.util.List;

import com.example.demo.line.vo.out.quickreply.QReply;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reply {
	
	private String to;
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
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	
	
}
