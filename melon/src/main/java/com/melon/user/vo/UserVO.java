package com.melon.user.vo;

import com.melon.authorization.vo.AuthorizationVO;

public class UserVO {
	
	private String userId;
	private String userName;
	private String userPassword;
	private int userPoint;
	private String authorizationId;
	
	private AuthorizationVO authorization;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public int getUserPoint() {
		return userPoint;
	}
	public void setUserPoint(int userPoint) {
		this.userPoint = userPoint;
	}
	public String getAuthorizationId() {
		return authorizationId;
	}
	public void setAuthorizationId(String authorizationId) {
		this.authorizationId = authorizationId;
	}
	public AuthorizationVO getAuthoriationVO() {
		if(authorization == null){
			authorization = new AuthorizationVO();
		}
		return authorization;
	}
	public void setAuthoriationVO(AuthorizationVO authorization) {
		this.authorization = authorization;
	}
	
}
