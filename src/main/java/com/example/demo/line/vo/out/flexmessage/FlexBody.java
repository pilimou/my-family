package com.example.demo.line.vo.out.flexmessage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlexBody {
	private String type;
	private String layout;
	private List<FlexBodyContents> contents;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public List<FlexBodyContents> getContents() {
		return contents;
	}
	public void setContents(List<FlexBodyContents> contents) {
		this.contents = contents;
	}
	
	
}
