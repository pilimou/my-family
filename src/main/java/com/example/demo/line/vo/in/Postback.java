package com.example.demo.line.vo.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Postback {
	
	private String data;
	private Params params;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Params getParams() {
		return params;
	}
	public void setParams(Params params) {
		this.params = params;
	}

	
	
}
