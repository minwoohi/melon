package com.melon.user.service;

import com.melon.user.biz.UserBiz;
import com.melon.user.biz.UserBizImpl;
import com.melon.user.vo.UserVO;

public class UserServiceImpl implements UserService {

	UserBiz userBiz;

	public UserServiceImpl() {
		userBiz = new UserBizImpl();
	}

	@Override
	public boolean registNewUser(UserVO newUser) {
		return userBiz.registNewUser(newUser);
	}

	@Override
	public UserVO loginUser(UserVO user) {
		return userBiz.loginUser(user);
	}

	@Override
	public boolean managePoint(String userId, int point) {
		return userBiz.managePoint(userId, point);
	}

	@Override
	public boolean isDuplicatedUserId(String userId) {
		return userBiz.isDuplicatedUserId(userId);
	}

}
