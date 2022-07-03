package com.example.demo.line.vo.out.flexmessage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PushFlex {
	
	private String to;
	private List<FlexMessages> messages;
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public List<FlexMessages> getMessages() {
		return messages;
	}
	public void setMessages(List<FlexMessages> messages) {
		this.messages = messages;
	}
	
	
}
