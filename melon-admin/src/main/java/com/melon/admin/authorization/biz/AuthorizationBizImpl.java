package com.melon.admin.authorization.biz;

import java.util.ArrayList;
import java.util.List;

import com.melon.admin.authorization.dao.AuthorizationDao;
import com.melon.admin.authorization.dao.AuthorizationDaoImpl;
import com.melon.admin.authorization.vo.AuthorizationSearchVO;
import com.melon.admin.authorization.vo.AuthorizationVO;
import com.melon.admin.common.web.pager.Pager;

public class AuthorizationBizImpl implements AuthorizationBiz {

	private AuthorizationDao authoDao;
	
	public AuthorizationBizImpl() {
		authoDao = new AuthorizationDaoImpl();
	}
	
	@Override
	public boolean addNewAuthorization(AuthorizationVO authorization) {
		String id = authoDao.generateNewAuthorizationId();
		authorization.setAuthorizationId(id);
		return authoDao.insertNewAuthorization(authorization) > 0;
	}

	@Override
	public List<AuthorizationVO> getAllAuthorization(AuthorizationSearchVO authorizationSearch) {
		int authoCount = authoDao.selectAllAuthorizationCount(authorizationSearch);
		
		Pager pager = authorizationSearch.getPager();
		pager.setTotalArticleCount(authoCount);
		
		if(authoCount == 0){
			return new ArrayList<AuthorizationVO>();
		}
		
		return authoDao.selectAllAuthorization(authorizationSearch);
	}

	@Override
	public boolean renewAuthorizationInfo(AuthorizationVO authorization) {
		return authoDao.updateAuthorizationInfo(authorization) > 0;
	}

	@Override
	public boolean removeOneAuthorization(String authorizationId) {
		return authoDao.deleteOneAuthorization(authorizationId) > 0;
	}

}
