package com.example.demo.line.vo.out.quickreply;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QReply {
	
	private String text;
	private String type;
	private QuickReply quickReply;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public QuickReply getQuickReply() {
		return quickReply;
	}
	public void setQuickReply(QuickReply quickReply) {
		this.quickReply = quickReply;
	}
	
	
	
	
}
