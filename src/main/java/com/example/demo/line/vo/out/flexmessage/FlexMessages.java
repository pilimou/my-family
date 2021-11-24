package com.example.demo.line.vo.out.flexmessage;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlexMessages {
	
	private String type;
	private String altText;
	private FlexContents contents;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAltText() {
		return altText;
	}
	public void setAltText(String altText) {
		this.altText = altText;
	}
	public FlexContents getContents() {
		return contents;
	}
	public void setContents(FlexContents contents) {
		this.contents = contents;
	}
	
	
}
