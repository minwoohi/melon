package com.melon.user.biz;

import com.melon.user.dao.UserDao;
import com.melon.user.dao.UserDaoImpl;
import com.melon.user.vo.UserVO;

public class UserBizImpl implements UserBiz{

	private UserDao userDao;
	
	public UserBizImpl() {
		userDao = new UserDaoImpl();
	}
	
	@Override
	public boolean registNewUser(UserVO newUser) {
		boolean isSuccess = userDao.insertNewUser(newUser) > 0;
		
		if(isSuccess){
			isSuccess = managePoint(newUser.getUserId(), 300);
		}
		
		return isSuccess;
	}

	@Override
	public UserVO loginUser(UserVO user) {
		
		UserVO loginUser = userDao.selectOneUser(user);
		
		if(loginUser != null){
			
			int point = loginUser.getUserPoint();
			
			managePoint(loginUser.getUserId(), point + 10); // db
			loginUser.setUserPoint(point + 10);
		}
		
		return loginUser;
	}

	@Override
	public boolean managePoint(String userId, int point) {
		
		return userDao.updatePoint(userId, point) > 0;
	}
	
	@Override
	public boolean isDuplicatedUserId(String userId) {
		return userDao.selectCountByUserId(userId) > 0;
	}

}
