package com.melon.user.dao;

import com.melon.user.vo.UserVO;

public interface UserDao {

	public int insertNewUser(UserVO user);
	
	public UserVO selectOneUser(UserVO user);
	
	public int updatePoint(String userId, int point);
	
	public int selectCountByUserId(String userId); // userId에 해당하는 유저가 DB에 몇개 있는지 알려줌
}
