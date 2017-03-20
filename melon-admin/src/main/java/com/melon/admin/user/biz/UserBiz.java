package com.melon.admin.user.biz;

import java.util.List;

import com.melon.admin.user.vo.UserSearchVO;
import com.melon.admin.user.vo.UserVO;

public interface UserBiz {
	public List<UserVO> getAllUsers(UserSearchVO userSearch);
	
	public UserVO getOneUser(String userId);
	
	public UserVO getOneUser(UserVO user);
	
	public boolean signUpNewUser(UserVO user);
	
	public boolean updateUser(UserVO user);
	
	public boolean removeUser(String userId);
	
	public boolean changeAllAuth(String beforeAuthId, String afterAuthId);
}
