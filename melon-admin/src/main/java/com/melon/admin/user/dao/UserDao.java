package com.melon.admin.user.dao;

import java.util.List;

import com.melon.admin.user.vo.UserSearchVO;
import com.melon.admin.user.vo.UserVO;

public interface UserDao {
	public int insertNewUser(UserVO newUserVO);
	
	public int selectAllUserCount(UserSearchVO userSearch);
	
	public List<UserVO> selectAllUser(UserSearchVO userSearch);
	
	/**
	 * 관리자 페이지에서 한명의 회원정보를 보기 위한 메소드
	 * @param userId
	 * @return
	 */
	public UserVO selectOneUser(String userId);
	
	/**
	 * 로그인을 위한 메소드
	 * @param userVO
	 * @return
	 */
	public UserVO selectOneUser(UserVO user);
	
	public int updateUserInfo(UserVO user);
	
	public int deleteOneUser(String userId);
	
	public int updateAllAuth(String beforeAuthId, String afterAuthId);
	
	
}
