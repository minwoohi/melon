package com.melon.admin.authorization.dao;

import java.util.List;

import com.melon.admin.authorization.vo.AuthorizationSearchVO;
import com.melon.admin.authorization.vo.AuthorizationVO;

public interface AuthorizationDao {
	
	public String generateNewAuthorizationId();
	
	public int insertNewAuthorization(AuthorizationVO authorization);
	
	public int selectAllAuthorizationCount(AuthorizationSearchVO 
			authorizationSearch);
	
	public List<AuthorizationVO> selectAllAuthorization(
			AuthorizationSearchVO authorizationSearch);
	
	public int updateAuthorizationInfo(AuthorizationVO authorization);
	
	public int deleteOneAuthorization(String authorizationId);
}
