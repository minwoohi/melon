package com.melon.admin.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.melon.admin.authorization.biz.AuthorizationBiz;
import com.melon.admin.authorization.biz.AuthorizationBizImpl;
import com.melon.admin.authorization.vo.AuthorizationSearchVO;
import com.melon.admin.user.biz.UserBiz;
import com.melon.admin.user.biz.UserBizImpl;
import com.melon.admin.user.vo.UserSearchVO;
import com.melon.admin.user.vo.UserVO;

public class UserServiceImpl implements UserService {

	private UserBiz userBiz;
	private AuthorizationBiz authorizationBiz;
	
	public UserServiceImpl() {
		userBiz = new UserBizImpl();
		authorizationBiz = new AuthorizationBizImpl();
	}
	@Override
	public List<UserVO> getAllUsers(UserSearchVO userSearch) {
		return userBiz.getAllUsers(userSearch);
	}

	@Override
	public UserVO getOneUser(String userId) {
		return userBiz.getOneUser(userId);
	}

	@Override
	public Map<String, Object> getOneUserWithAuthorizations(String userId) {
		AuthorizationSearchVO authorizationSearch = new AuthorizationSearchVO();
		authorizationSearch.getPager().setPageNumber(0);
		
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("user", userBiz.getOneUser(userId));
		user.put("authorizations", authorizationBiz.getAllAuthorization(authorizationSearch));
		
		return user;
	}
	
	@Override
	public UserVO getOneUser(UserVO user) {
		return userBiz.getOneUser(user);
	}

	@Override
	public boolean signUpNewUser(UserVO user) {
		return userBiz.signUpNewUser(user);
	}

	@Override
	public boolean updateUser(UserVO user) {
		
		UserVO userTemp = getOneUser(user.getUserId());
		
		if( user.getAuthorizationId() != null 
				&& user.getAuthorizationId().length() >= 0 ) { // 권한 정보를 수정했다면
			userTemp.setAuthorizationId(user.getAuthorizationId());
		} 
		
		if( user.getUserPoint() > 0 ) { // 권한 정보를 수정했다면
			userTemp.setUserPoint(user.getUserPoint());
		}
		
		if( user.getUserPassword() != null &&
				user.getUserPassword().length() > 0 ){
			userTemp.setUserPassword(user.getUserPassword());
		}
		
		return userBiz.updateUser(userTemp);
	}

	@Override
	public boolean removeUser(String userId) {
		return userBiz.removeUser(userId);
	}
	
	@Override
	public boolean renewAllAuth(String beforeAuthId, String afterAuthId) {
		return userBiz.changeAllAuth(beforeAuthId, afterAuthId);
	}
	
	@Override
	public boolean renewCheckedAuth(String[] userList, String afterAuthId) {
		UserVO user = null;
		
		for(String userId : userList){
			System.out.println("userId : " + userId);
			user = new UserVO();
			user.setUserId(userId);
			user.setAuthorizationId(afterAuthId);
			updateUser(user);
		}
		return true;
	}
	
}
