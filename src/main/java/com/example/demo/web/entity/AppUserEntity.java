package com.example.demo.web.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "FAMILY_USER")
public class AppUserEntity {
	
	@Id
	private String id;
	
	@Field("account")
	private String account;
	
	@Field("password")
	private String password;
	
	@Field("roleid")
	private String roleId;
	
	@Field("linecode")
	private String lineCode;
	
	@Field("lineid")
	private String lineId;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	
	
}
