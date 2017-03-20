package com.melon.admin.authorization.vo;

public class AuthorizationVO {
	private String authorizationId;
	private String authorizationName;
	private String parentAuthorizationId;
	
	public String getAuthorizationId() {
		return authorizationId;
	}
	public void setAuthorizationId(String authorizationId) {
		this.authorizationId = authorizationId;
	}
	public String getAuthorizationName() {
		return authorizationName;
	}
	public void setAuthorizationName(String authorizationName) {
		this.authorizationName = authorizationName;
	}
	public String getParentAuthorizationId() {
		return parentAuthorizationId;
	}
	public void setParentAuthorizationId(String parentAuthorizationId) {
		this.parentAuthorizationId = parentAuthorizationId;
	}
}
