package com.example.demo.line.vo.out.flexmessage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlexHero {
	private String type;
	private String url;
	private String size;
	private String aspectMode;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getAspectMode() {
		return aspectMode;
	}
	public void setAspectMode(String aspectMode) {
		this.aspectMode = aspectMode;
	}
	
	
}
