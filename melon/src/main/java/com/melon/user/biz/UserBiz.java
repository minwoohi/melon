package com.melon.user.biz;

import com.melon.user.vo.UserVO;

public interface UserBiz {
	public boolean registNewUser(UserVO newUser);
	
	public UserVO loginUser(UserVO user);
	
	public boolean managePoint(String userId, int point);
	
	public boolean isDuplicatedUserId(String userId);
}
