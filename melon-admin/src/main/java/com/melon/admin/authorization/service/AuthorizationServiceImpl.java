package com.melon.admin.authorization.service;

import java.util.List;

import com.melon.admin.authorization.biz.AuthorizationBiz;
import com.melon.admin.authorization.biz.AuthorizationBizImpl;
import com.melon.admin.authorization.vo.AuthorizationSearchVO;
import com.melon.admin.authorization.vo.AuthorizationVO;

public class AuthorizationServiceImpl implements AuthorizationService {

	private AuthorizationBiz authorizationBiz;
	
	public AuthorizationServiceImpl() {
		authorizationBiz = new AuthorizationBizImpl();
	}
	
	@Override
	public boolean addNewAuthorization(AuthorizationVO authorization) {
		return authorizationBiz.addNewAuthorization(authorization);
	}

	@Override
	public List<AuthorizationVO> getAllAuthorization(AuthorizationSearchVO authorizationSearch) {
		return authorizationBiz.getAllAuthorization(authorizationSearch);
	}

	@Override
	public boolean renewAuthorizationInfo(AuthorizationVO authorization) {
		return authorizationBiz.renewAuthorizationInfo(authorization);
	}

	@Override
	public boolean removeOneAuthorization(String authorizationId) {
		return authorizationBiz.removeOneAuthorization(authorizationId);
	}
	

}
