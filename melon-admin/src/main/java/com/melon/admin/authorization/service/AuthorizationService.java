package com.melon.admin.authorization.service;

import java.util.List;

import com.melon.admin.authorization.vo.AuthorizationSearchVO;
import com.melon.admin.authorization.vo.AuthorizationVO;

public interface AuthorizationService {
	
	public boolean addNewAuthorization(AuthorizationVO authorization);

	public List<AuthorizationVO> getAllAuthorization(AuthorizationSearchVO authorizationSearch);

	public boolean renewAuthorizationInfo(AuthorizationVO authorization);

	public boolean removeOneAuthorization(String authorizationId);
	
	
}
