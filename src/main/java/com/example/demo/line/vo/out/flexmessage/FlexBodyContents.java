package com.example.demo.line.vo.out.flexmessage;

import com.example.demo.line.vo.out.quickreply.Action;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlexBodyContents {
	private String type;
	private Action action;
	private String offsetTop;
	private String style;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getOffsetTop() {
		return offsetTop;
	}
	public void setOffsetTop(String offsetTop) {
		this.offsetTop = offsetTop;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
	
}
