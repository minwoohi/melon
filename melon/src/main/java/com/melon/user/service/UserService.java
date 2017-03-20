package com.melon.user.service;

import com.melon.user.vo.UserVO;

public interface UserService {
	public boolean registNewUser(UserVO newUser);

	public UserVO loginUser(UserVO user);

	public boolean managePoint(String userId, int point);
	
	public boolean isDuplicatedUserId(String userId);
}
