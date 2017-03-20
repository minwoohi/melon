package com.melon.admin.user.service;

import java.util.List;
import java.util.Map;

import com.melon.admin.user.vo.UserSearchVO;
import com.melon.admin.user.vo.UserVO;

public interface UserService {
	public List<UserVO> getAllUsers(UserSearchVO userSearch);

	public UserVO getOneUser(String userId);

	public UserVO getOneUser(UserVO user);
	public Map<String, Object> getOneUserWithAuthorizations(String userId);

	public boolean signUpNewUser(UserVO user);

	public boolean updateUser(UserVO user);

	public boolean removeUser(String userId);
	
	public boolean renewAllAuth(String beforeAuthId, String afterAuthId);
	
	public boolean renewCheckedAuth(String[] userList, String afterAuthId);
}
