package com.example.demo.line.vo.out.flexmessage;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlexContents {
	private String type;
	private FlexHero hero;
	private FlexBody body;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public FlexHero getHero() {
		return hero;
	}
	public void setHero(FlexHero hero) {
		this.hero = hero;
	}
	public FlexBody getBody() {
		return body;
	}
	public void setBody(FlexBody body) {
		this.body = body;
	}
	
	
	
}
