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
	
	@Field("lineUserId")
	private String lineUserId;
	
	@Field("lineUserName")
	private String lineUserName;
	
	@Field("createDate")
	private String createDate;

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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	
	
	
}
