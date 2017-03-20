package com.melon.admin.user.biz;

import java.util.ArrayList;
import java.util.List;

import com.melon.admin.common.web.pager.Pager;
import com.melon.admin.user.dao.UserDao;
import com.melon.admin.user.dao.UserDaoImpl;
import com.melon.admin.user.vo.UserSearchVO;
import com.melon.admin.user.vo.UserVO;

public class UserBizImpl implements UserBiz {

	private UserDao userDao;
	
	public UserBizImpl() {
		userDao = new UserDaoImpl();
	}
	
	@Override
	public List<UserVO> getAllUsers(UserSearchVO userSearch) {
		
		int userCount = userDao.selectAllUserCount(userSearch);
		
		Pager pager = userSearch.getPager();
		pager.setTotalArticleCount(userCount);
		
		return userDao.selectAllUser(userSearch);
	}

	@Override
	public UserVO getOneUser(String userId) {
		return userDao.selectOneUser(userId);
	}

	@Override
	public UserVO getOneUser(UserVO user) {
		return userDao.selectOneUser(user);
	}

	@Override
	public boolean signUpNewUser(UserVO user) {
		return userDao.insertNewUser(user) > 0;
	}

	@Override
	public boolean updateUser(UserVO user) {
		return userDao.updateUserInfo(user) > 0;
	}

	@Override
	public boolean removeUser(String userId) {
		return userDao.deleteOneUser(userId) > 0;
	}
	
	@Override
	public boolean changeAllAuth(String beforeAuthId, String afterAuthId) {
		return userDao.updateAllAuth(beforeAuthId, afterAuthId) >= 0;
	}

}
