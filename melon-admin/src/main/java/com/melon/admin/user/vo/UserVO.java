package com.melon.admin.user.vo;

import com.melon.admin.authorization.vo.AuthorizationVO;

public class UserVO {
	
	private int index; // 번호
	private String userId;
	private String userName;
	private String userPassword;
	private int userPoint;
	private String authorizationId;
	
	private AuthorizationVO authorizationVO;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
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
	public AuthorizationVO getAuthorizationVO() {
		if(authorizationVO == null){
			authorizationVO = new AuthorizationVO();
		}
		return authorizationVO;
	}
	public void setAuthorizationVO(AuthorizationVO authorizationVO) {
		this.authorizationVO = authorizationVO;
	}
	
}
