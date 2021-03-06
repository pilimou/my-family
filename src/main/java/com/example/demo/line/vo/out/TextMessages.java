package com.example.demo.line.vo.out;

import com.example.demo.line.vo.out.quickreply.QuickReply;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextMessages {
	
	private String type;
	private String text;
	private QuickReply quickReply;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public QuickReply getQuickReply() {
		return quickReply;
	}
	public void setQuickReply(QuickReply quickReply) {
		this.quickReply = quickReply;
	}

	
	
	
	
}
