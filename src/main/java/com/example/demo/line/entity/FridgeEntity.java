package com.example.demo.line.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "FAMILY_FRIDGE")
public class FridgeEntity {
	
	@Id
	private String id;
	
	@Field("account")
	private String account;
	
	@Field("lineUserId")
	private String lineUserId;
	
	@Field("lineUserName")
	private String lineUserName;
	
	@Field("itemName")
	private String itemName;
	
	@Field("expirationDateStr")
	private String expirationDateStr;
	
	@Field("processDateStr")
	private String processDateStr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLineUserId() {
		return lineUserId;
	}

	public void setLineUserId(String lineUserId) {
		this.lineUserId = lineUserId;
	}

	public String getLineUserName() {
		return lineUserName;
	}

	public void setLineUserName(String lineUserName) {
		this.lineUserName = lineUserName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getExpirationDateStr() {
		return expirationDateStr;
	}

	public void setExpirationDateStr(String expirationDateStr) {
		this.expirationDateStr = expirationDateStr;
	}

	public String getProcessDateStr() {
		return processDateStr;
	}

	public void setProcessDateStr(String processDateStr) {
		this.processDateStr = processDateStr;
	}

	
	
	
}
